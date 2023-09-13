package com.klasha.demo.controller;

import com.klasha.demo.dtos.requests.ExchangeRequest;
import com.klasha.demo.dtos.ResponseDto;
import com.klasha.demo.exception.KlashaException;
import com.klasha.demo.services.interfaces.KlashaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/klasha")
@RestController
@RequiredArgsConstructor
public class KlashaController {

    private final KlashaService klashaService;
    private final String MESSAGE = "REQUEST SUCCESSFUL";

    @GetMapping("/cities")
    public ResponseDto<?> getCities(@RequestParam(defaultValue = "10") int numberOfCities) {
        return ResponseDto.wrapSuccessResult(klashaService.getCities(numberOfCities), MESSAGE);
    }

    @GetMapping("/country")
    public ResponseDto<?> getCountryDetails(@RequestParam(defaultValue = "Nigeria") String country) {
        return ResponseDto.wrapSuccessResult(klashaService.getCountryDetail(country), MESSAGE);
    }

    @GetMapping("/states")
    public ResponseDto<?> getStateDetails(@RequestParam(defaultValue = "Nigeria") String country) {
        return ResponseDto.wrapSuccessResult(klashaService.getStateDetails(country), MESSAGE);
    }

    @GetMapping("/exchange")
    public ResponseDto<?> getExchangeDetails(@RequestBody @Valid ExchangeRequest exchangeRequest) throws KlashaException {
        return ResponseDto.wrapSuccessResult(klashaService.getExchange(exchangeRequest), MESSAGE);
    }
}
