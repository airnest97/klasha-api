package com.klasha.demo.dtos.responses;

import com.klasha.demo.dtos.CityPopulationCount;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityPopulationResponse {
    private String city;

    private String country;

    private List<CityPopulationCount> populationCounts;
}