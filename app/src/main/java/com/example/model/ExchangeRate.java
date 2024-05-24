package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRate {
    private String targetCurrency; // The currency to which UAH is being compared
    private String date;
    private double buyingRate; // The buying rate from UAH to the target currency
    private double sellingRate; // The selling rate from UAH to the target currency

    public ExchangeRate(String targetCurrency, String date, double buyingRate, double sellingRate) {
        this.targetCurrency = targetCurrency;
        this.date = date;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }
}
