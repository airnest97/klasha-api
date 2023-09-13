package com.klasha.demo.controller;

import com.klasha.demo.dtos.CityPopulationCount;
import com.klasha.demo.dtos.requests.ExchangeRequest;
import com.klasha.demo.dtos.responses.*;
import com.klasha.demo.exception.KlashaException;
import com.klasha.demo.services.implementation.KlashaServiceImpl;
import com.klasha.demo.utils.CSVFileReader;
import com.klasha.demo.client.CountryClient;
import com.klasha.demo.dtos.ResponseDto;
import com.klasha.demo.services.interfaces.KlashaService;
import com.klasha.demo.factory.GeneralFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KlashaControllerTest {
    private final CountryClient citiesClient = mock(CountryClient.class);

    private final CSVFileReader csvFileReader = mock(CSVFileReader.class);
    private final KlashaService klashaService = new KlashaServiceImpl(citiesClient, csvFileReader);
    private final KlashaController klashaController = new KlashaController(klashaService);
    @Value("${request.successful}")
    private String MESSAGE;

    @Test
    void testGetCities() {

        List<CityPopulationResponse> sampleData = Arrays.asList(
                createCityPopulationResponse("City A", "Country A", 100000),
                createCityPopulationResponse("City B", "Country B", 200000),
                createCityPopulationResponse("City C", "Country C", 150000)
        );

        when(citiesClient.getCitiesPopulations(anyString()))
                .thenReturn(ResponseDto.wrapSuccessResult(sampleData, MESSAGE));

        ResponseDto<?> responseDto = klashaController.getCities(3);
        List<CityPopulationResponse> result = (List<CityPopulationResponse>) responseDto.getData();
        assertNotNull(responseDto);

        assertEquals(3, result.size());
        assertEquals("City B", result.get(0).getCity());
        assertEquals("Country B", result.get(0).getCountry());
        assertEquals(200000, result.get(0).getPopulationCounts().get(0).getValue());
    }

    private CityPopulationResponse createCityPopulationResponse(String city, String country, double value) {
        CityPopulationCount populationCount = new CityPopulationCount();
        populationCount.setValue(value);

        CityPopulationResponse cityPopulationResponse = new CityPopulationResponse();
        cityPopulationResponse.setCity(city);
        cityPopulationResponse.setCountry(country);
        cityPopulationResponse.setPopulationCounts(List.of(populationCount));

        return cityPopulationResponse;
    }


    @Test
    void testGetCountryData() {
        CountryLocationResponse locationResponse = GeneralFactory.createCountryLocationResponse();
        CurrencyResponse currencyResponse = GeneralFactory.createCurrencyResponse();
        CountryCapitalResponse capitalResponse = GeneralFactory.createCountryCapitalResponse("Abuja");
        PopulationResponse populationResponse = GeneralFactory.createPopulationResponse();

        when(citiesClient.getLocationData(anyString()))
                .thenReturn(ResponseDto.wrapSuccessResult(locationResponse, MESSAGE));
        when(citiesClient.getCurrencyData(anyString()))
                .thenReturn(ResponseDto.wrapSuccessResult(currencyResponse, MESSAGE));
        when(citiesClient.getCapitalData(anyString()))
                .thenReturn(ResponseDto.wrapSuccessResult(capitalResponse, MESSAGE));
        when(citiesClient.getPopulationData(anyString()))
                .thenReturn(ResponseDto.wrapSuccessResult(populationResponse, MESSAGE));

        ResponseDto<?> responseDto = klashaController.getCountryDetails("Nigeria");
        CountryDetailResponse result = (CountryDetailResponse) responseDto.getData();

        assertNotNull(responseDto);
        assertEquals("Abuja", result.getCapital());
        assertEquals(1000, result.getPopulation());
    }


    @Test
    void testGetExchange() throws KlashaException {
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setSourceCountry("Nigeria");
        exchangeRequest.setTargetCurrency("USD");
        exchangeRequest.setAmount(BigDecimal.TEN);

        when(citiesClient.getCurrencyData(anyString())).thenReturn(ResponseDto.wrapSuccessResult(GeneralFactory.currencyResponse(), "ds"));
        when(csvFileReader.getExchangeRate(anyString())).thenReturn(56.2);

        ResponseDto<?> responseDto = klashaController.getExchangeDetails((exchangeRequest));
        CurrencyExchangeResponse currencyExchangeResponse = (CurrencyExchangeResponse) responseDto.getData();
        assertNotNull(currencyExchangeResponse);
    }

    @Test
    void testGetExchange_ExchangeRateNotFound() {
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setSourceCountry("Nigeria");
        exchangeRequest.setTargetCurrency("EUR");
        exchangeRequest.setAmount(BigDecimal.TEN);

        when(citiesClient.getCurrencyData(anyString())).thenReturn(ResponseDto.wrapSuccessResult(GeneralFactory.currencyResponse(), "ds"));
        when(csvFileReader.getExchangeRate(anyString())).thenReturn(null);

        KlashaException exception = assertThrows(KlashaException.class, () -> klashaController.getExchangeDetails((exchangeRequest)));
        assertEquals("Exchange rate not found for NGN-EUR", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}