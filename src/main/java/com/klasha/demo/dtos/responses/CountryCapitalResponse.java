package com.klasha.demo.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountryCapitalResponse {
    private String name;
    private String capital;
    private String iso2;
    private String iso3;
}
