package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.ExchangeRatesDTO;
import com.drunar.coincorner.util.GetPOJOExchangeRates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private final GetPOJOExchangeRates exchangeRates;

    public List<ExchangeRatesDTO> getExchangeRatesList() {
        ExchangeRatesDTO[] array = exchangeRates.getArrayRates();

        return Arrays.stream(array)
                .filter(this::isDesiredCurrency)
                .collect(Collectors.toList());
    }

    private boolean isDesiredCurrency(ExchangeRatesDTO e) {
        String currencyCode = e.getCurrencyCodeL();
        return "USD".equals(currencyCode) || "EUR".equals(currencyCode) || "PLN".equals(currencyCode);
    }
}
