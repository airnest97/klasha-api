package com.klasha.demo.services.interfaces;

import com.klasha.demo.dtos.requests.ExchangeRequest;
import com.klasha.demo.dtos.responses.CityPopulationResponse;
import com.klasha.demo.dtos.responses.CountryDetailResponse;
import com.klasha.demo.dtos.responses.CurrencyExchangeResponse;
import com.klasha.demo.dtos.responses.StateResponse;
import com.klasha.demo.exception.KlashaException;

import java.util.List;

public interface KlashaService {

    List<CityPopulationResponse> getCities(int limit);

    CountryDetailResponse getCountryDetail(String country);

    List<StateResponse> getStateDetails(String country);

    CurrencyExchangeResponse getExchange(ExchangeRequest exchangeRequest) throws KlashaException;

}