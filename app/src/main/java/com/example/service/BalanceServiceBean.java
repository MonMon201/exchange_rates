package com.example.service;

import com.example.model.Balance;
import com.example.model.ExchangeRate;
import com.example.repository.BalanceRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class BalanceServiceBean {

    @Inject
    private BalanceRepository balanceRepository;

    @Inject
    private ExchangeRateServiceBean exchangeRateServiceBean;

    public List<Balance> getBalance() {
        return balanceRepository.getAllBalances();
    }

    @Transactional(rollbackOn = Exception.class)
    public void addUah(double amount) {
        Balance balance = balanceRepository.findByCurrency("UAH");
        balance.setAmount(balance.getAmount() + amount);
        balanceRepository.updateBalance(balance);
    }

    @Transactional(rollbackOn = Exception.class)
    public void decreaseUah(double amount) throws Exception {
        Balance balance = balanceRepository.findByCurrency("UAH");
        balance.setAmount(balance.getAmount() - amount);
        balanceRepository.updateBalance(balance);
        if (balance.getAmount() < amount) {
            throw new Exception("Insufficient UAH balance");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void convertCurrency(String fromCurrency, String toCurrency, double amount) throws Exception {
        if (!(fromCurrency.equals("UAH") || toCurrency.equals("UAH"))) {
            throw new Exception("Conversions are only allowed between UAH and another currency");
        }

        Balance fromBalance = balanceRepository.findByCurrency(fromCurrency);
        Balance toBalance = balanceRepository.findByCurrency(toCurrency);
        ExchangeRate exchangeRate = exchangeRateServiceBean.getLatestExchangeRateByCurrency(toCurrency);

        if (fromBalance == null) {
            throw new Exception("Currency " + fromCurrency + " does not exist in your balance");
        }

        if (toBalance == null) {
            String currencyName = exchangeRate.getCurrency();
            toBalance = new Balance(null, currencyName, 0);
            balanceRepository.updateBalance(toBalance);
        }

        if (exchangeRate == null) {
            throw new Exception("Exchange rate for currency " + toCurrency + " not found");
        }

        double convertedAmount;
        if (fromCurrency.equals("UAH")) {
            convertedAmount = amount / exchangeRate.getBuyingRate(); // UAH to other currency
        } else {
            convertedAmount = amount * exchangeRate.getSellingRate(); // Other currency to UAH
        }

        if (fromBalance.getAmount() < amount) {
            throw new Exception("Insufficient balance in " + fromCurrency);
        }

        fromBalance.setAmount(fromBalance.getAmount() - amount);
        toBalance.setAmount(toBalance.getAmount() + convertedAmount);

        balanceRepository.updateBalance(fromBalance);
        balanceRepository.updateBalance(toBalance);
    }
}
