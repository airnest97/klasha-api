package com.klasha.demo.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ExchangeRequest {
    @NotBlank(message = "{sourceCountry.not_blank}")
    private String sourceCountry;
    @NotBlank(message = "{targetCurrency.not_blank}")
    private String targetCurrency;
    @Positive(message = "{amount.invalid}")
    private BigDecimal amount;
}
