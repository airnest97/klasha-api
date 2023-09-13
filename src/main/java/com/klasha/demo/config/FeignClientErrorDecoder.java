package com.klasha.demo.config;

import com.klasha.demo.exception.KlashaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static feign.FeignException.errorStatus;

@Slf4j
@RequiredArgsConstructor
public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("An exception occurred when calling the Service [{}]", response);

        try {
            InputStream inputStream = response.body().asInputStream();
            String errorJson = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            String errorMessage = extractErrorMessage(errorJson);
            HttpStatus httpStatus = HttpStatus.valueOf(response.status());
            return new KlashaException(errorMessage, httpStatus);
        } catch (IOException e) {
            log.error("Error reading error input stream of feign: " + e.getMessage(), e);
            return errorStatus(methodKey, response);
        }
    }

    private String extractErrorMessage(String error) throws IOException {
        Map<String, Object> errorBody = objectMapper.readValue(error, Map.class);
        return errorBody.get("msg").toString();
    }
}