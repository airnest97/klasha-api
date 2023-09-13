package com.klasha.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class CSVFileReader {

    @Value("${csv.file-url:${CSV_FILE}}")
    private String url;

    public HashMap<String, Double> parseCSV() {
        HashMap<String, Double> exchangeRates = new HashMap<>();

        try {
            URL csvUrl = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(csvUrl.openStream()));

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String sourceCurrency = columns[0];
                String targetCurrency = columns[1];
                double rate = Double.parseDouble(columns[2]);

                // Create the currency pair key (e.g., "JPY-UGX") and store the exchange rate in the HashMap
                String currencyPair = sourceCurrency + "-" + targetCurrency;
                exchangeRates.put(currencyPair, rate);

                // Create the reverse currency pair key (e.g., "UGX-JPY" for "JPY-UGX")
                String reverseCurrencyPair = targetCurrency + "-" + sourceCurrency;
                // Calculate the reverse conversion rate by taking the reciprocal of the rate
                double reverseRate = 1 / rate;

                exchangeRates.put(reverseCurrencyPair, reverseRate);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(exchangeRates);

        return exchangeRates;
    }

    @Cacheable(key = "#exchangeKey", value = "exchangeRate")
    public Double getExchangeRate(String exchangeKey) {
        Map<String, Double> exchangeRates = parseCSV();
        return exchangeRates.getOrDefault(exchangeKey, null);
    }
}
