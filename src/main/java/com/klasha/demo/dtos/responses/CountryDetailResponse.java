package com.klasha.demo.dtos.responses;


import lombok.*;

@Setter
@Getter
public class CountryDetailResponse {

    private String capital;

    private String iso2;

    private String iso3;

    private CountryLocationResponse location;

    private String currency;

    private int population;
}