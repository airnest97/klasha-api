package com.klasha.demo.client;


import com.klasha.demo.dtos.responses.*;
import com.klasha.demo.config.FeignClientConfig;
import com.klasha.demo.dtos.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "countryClient", url = "https://countriesnow.space/api/v0.1/countries", configuration = FeignClientConfig.class)
public interface CountryClient {

    @RequestMapping(method = RequestMethod.GET, value = "/population/cities/filter/q")
    ResponseDto<List<CityPopulationResponse>> getCitiesPopulations(@RequestParam("country") String country);

    @RequestMapping(method = RequestMethod.GET, value = "/population/q")
    ResponseDto<PopulationResponse> getPopulationData(@RequestParam("country") String country);

    @RequestMapping(method = RequestMethod.GET, value = "/currency/q")
    ResponseDto<CurrencyResponse> getCurrencyData(@RequestParam("country") String country);

    @RequestMapping(method = RequestMethod.GET, value = "/positions/q")
    ResponseDto<CountryLocationResponse> getLocationData(@RequestParam("country") String country);

    @RequestMapping(method = RequestMethod.GET, value = "/states/q")
    ResponseDto<CountryStateResponse> getStateData(@RequestParam("country") String country);

    @RequestMapping(method = RequestMethod.GET, value = "/capital/q")
    ResponseDto<CountryCapitalResponse> getCapitalData(@RequestParam("country") String country);

    @RequestMapping(method = RequestMethod.GET, value = "/state/cities/q")
    ResponseDto<List<String>> getCitiesData(@RequestParam("country") String country, @RequestParam("state") String state);
}