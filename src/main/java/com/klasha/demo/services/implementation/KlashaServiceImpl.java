package com.klasha.demo.services.implementation;


import com.klasha.demo.dtos.PopulationCount;
import com.klasha.demo.dtos.requests.ExchangeRequest;
import com.klasha.demo.dtos.responses.*;
import com.klasha.demo.exception.KlashaException;
import com.klasha.demo.client.CountryClient;
import com.klasha.demo.services.interfaces.KlashaService;
import com.klasha.demo.utils.CSVFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class KlashaServiceImpl implements KlashaService {

    private final CountryClient countryClient;
    private final CSVFileReader CSVFileReader;

    @Override
    public List<CityPopulationResponse> getCities(int limit) {
        List<String> countries = Arrays.asList("Italy", "New Zealand", "Ghana");
        return countries.stream()
                .flatMap(name -> countryClient.getCitiesPopulations(name).getData().parallelStream())
                .sorted(Comparator.comparingDouble(object -> -object.getPopulationCounts().get(0).getValue()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDetailResponse getCountryDetail(String country) {
        CountryLocationResponse countryLocationResponse = countryClient.getLocationData(country).getData();
        CurrencyResponse currencyResponse = countryClient.getCurrencyData(country).getData();
        CountryCapitalResponse countryCapitalResponse = countryClient.getCapitalData(country).getData();
        PopulationResponse populationResponse = countryClient.getPopulationData(country).getData();

        PopulationCount populationCount = populationResponse.getPopulationCounts().get(populationResponse.getPopulationCounts().size() - 1);

        CountryDetailResponse countryDetailResponse = new CountryDetailResponse();
        countryDetailResponse.setCapital(countryCapitalResponse.getCapital());
        countryDetailResponse.setCurrency(currencyResponse.getCurrency());
        countryDetailResponse.setIso2(currencyResponse.getIso2());
        countryDetailResponse.setIso3(currencyResponse.getIso3());
        countryDetailResponse.setPopulation(populationCount.getValue());
        countryDetailResponse.setLocation(countryLocationResponse);
        return countryDetailResponse;
    }

    @Override
    public List<StateResponse> getStateDetails(String country) {
        CountryStateResponse countryStateResponse = countryClient.getStateData(country).getData();
        return countryStateResponse.getStates().stream()
                .peek(stateResponse -> {
                    String stateName = stateResponse.getName().equalsIgnoreCase("Lagos State") ? "Lagos" : stateResponse.getName();
                    List<String> cities = countryClient.getCitiesData(country, stateName).getData();
                    stateResponse.setCites(cities);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyExchangeResponse getExchange(ExchangeRequest exchangeRequest) throws KlashaException {
        CurrencyResponse currencyResponse = countryClient.getCurrencyData(exchangeRequest.getSourceCountry()).getData();

        String key = currencyResponse.getCurrency() + "-" + exchangeRequest.getTargetCurrency();
        Double rate = CSVFileReader.getExchangeRate(key.toUpperCase());
        if (rate == null) throw new KlashaException("Exchange rate not found for " + key, HttpStatus.NOT_FOUND);

        BigDecimal amount = exchangeRequest.getAmount().multiply(BigDecimal.valueOf(rate));
        BigDecimal roundedAmount = amount.setScale(2, RoundingMode.UP);

        CurrencyExchangeResponse currencyExchangeResponse = new CurrencyExchangeResponse();
        currencyExchangeResponse.setSourceCurrency(currencyResponse.getCurrency());
        currencyExchangeResponse.setTargetCurency(exchangeRequest.getTargetCurrency());
        currencyExchangeResponse.setAmount(roundedAmount);
        return currencyExchangeResponse;
    }
}
