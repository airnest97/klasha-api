package com.klasha.demo.factory;

import com.klasha.demo.dtos.PopulationCount;
import com.klasha.demo.dtos.responses.CountryCapitalResponse;
import com.klasha.demo.dtos.responses.CountryLocationResponse;
import com.klasha.demo.dtos.responses.CurrencyResponse;
import com.klasha.demo.dtos.responses.PopulationResponse;

import java.util.List;


public class GeneralFactory {
    public static CountryLocationResponse createCountryLocationResponse() {
        CountryLocationResponse locationResponse = new CountryLocationResponse();
        locationResponse.setLatitude(20);
        locationResponse.setLongitude(10);

        return locationResponse;
    }

    public static CurrencyResponse createCurrencyResponse() {
        CurrencyResponse currencyResponse = new CurrencyResponse();
        currencyResponse.setCurrency("fdf");
        currencyResponse.setName("ds");
        currencyResponse.setIso2("dsd");
        currencyResponse.setIso3("Dsd");

        return currencyResponse;
    }

    public static CountryCapitalResponse createCountryCapitalResponse(String capital) {
        CountryCapitalResponse capitalResponse = new CountryCapitalResponse();
        capitalResponse.setCapital(capital);
        return capitalResponse;
    }

    public static PopulationResponse createPopulationResponse() {
        PopulationResponse populationResponse = new PopulationResponse();
        PopulationCount populationCount = new PopulationCount();
        populationCount.setValue(1000);
        populationResponse.setPopulationCounts(List.of(populationCount));
        return populationResponse;
    }

    public static CurrencyResponse currencyResponse() {
        CurrencyResponse currencyResponse = new CurrencyResponse();
        currencyResponse.setCurrency("NGN");
        return currencyResponse;
    }
}
