package com.klasha.demo.dtos.responses;


import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class CurrencyExchangeResponse {
    private String sourceCurrency;
    private String targetCurency;
    private BigDecimal amount;
}
