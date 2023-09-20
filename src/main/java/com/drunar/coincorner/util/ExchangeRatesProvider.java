package com.drunar.coincorner.util;

import com.drunar.coincorner.dto.ExchangeRatesDTO;

public interface ExchangeRatesProvider {

    ExchangeRatesDTO[] getArrayRates();

}
