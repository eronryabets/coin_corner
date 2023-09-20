package com.drunar.coincorner.util;

import com.drunar.coincorner.dto.ExchangeRatesDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class GetPOJOExchangeRates implements ExchangeRatesProvider{

    private final String UrlExchangeRates;

    private final RestTemplate restTemplate;

    public GetPOJOExchangeRates(@Value("${app.exchangeRatesUrl}")
                                String urlExchangeRates, RestTemplateBuilder restTemplateBuilder) {
        UrlExchangeRates = urlExchangeRates;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ExchangeRatesDTO[] getArrayRates() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("my_other_key", "my_other_value");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ExchangeRatesDTO[]> response = restTemplate.exchange(
                UrlExchangeRates, HttpMethod.GET, entity, ExchangeRatesDTO[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }
}
