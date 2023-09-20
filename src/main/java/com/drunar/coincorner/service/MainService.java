package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.ExchangeRatesDTO;

import java.util.List;

public interface MainService {

    List<ExchangeRatesDTO> getExchangeRatesList();

}
