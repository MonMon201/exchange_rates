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

    public double getExchangeRate(String fromCurrency, String toCurrency) throws Exception {
        if (fromCurrency.equals("UAH")) {
            ExchangeRate rate = exchangeRateRepository.findByCurrency(toCurrency).stream()
                    .findFirst()
                    .orElseThrow(() -> new Exception("Exchange rate not found for currency: " + toCurrency));
            return rate.getBuyingRate(); // UAH to other currency
        } else if (toCurrency.equals("UAH")) {
            ExchangeRate rate = exchangeRateRepository.findByCurrency(fromCurrency).stream()
                    .findFirst()
                    .orElseThrow(() -> new Exception("Exchange rate not found for currency: " + fromCurrency));
            return 1 / rate.getSellingRate(); // Other currency to UAH
        } else {
            throw new Exception("Conversions are only allowed between UAH and another currency");
        }
    }

    public ExchangeRate getLatestExchangeRateByCurrency(String currency) {
        return exchangeRateRepository.findLatestByCurrency(currency);
    }
}
