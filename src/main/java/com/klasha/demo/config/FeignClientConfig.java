package com.klasha.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FeignClientConfig {

    private final ObjectMapper objectMapper;

    @Bean
    Logger.Level feignLoggerLevel() {
        if (log.isDebugEnabled()) return Logger.Level.FULL;
        return Logger.Level.BASIC;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientErrorDecoder(objectMapper);
    }
}