package com.example.service;

import com.example.model.ExchangeRate;
import com.example.repository.ExchangeRateRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ExchangeRateServiceBean {

    @Inject
    private ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceBean() {
        this.exchangeRateRepository = new ExchangeRateRepository();
    }

    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateRepository.getAllExchangeRates();
    }

    public void addExchangeRate(ExchangeRate exchangeRate) {
        exchangeRateRepository.addExchangeRate(exchangeRate);
    }

    public void updateExchangeRate(ExchangeRate exchangeRate) {
        exchangeRateRepository.updateExchangeRate(exchangeRate);
    }

    public void deleteExchangeRate(Long id) {
        exchangeRateRepository.deleteExchangeRate(id);
    }

    public ExchangeRate findById(Long id) {
        return exchangeRateRepository.findById(id);
    }

    public List<ExchangeRate> findByCurrency(String currency) {
        return exchangeRateRepository.findByCurrency(currency);
    }
}
