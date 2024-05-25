package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "exchange_rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateEntity {

    @EmbeddedId
    private ExchangeRateId id;

    private double buyingRate;
    private double sellingRate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeRateId implements java.io.Serializable {
        private String currency;
        private String date;
    }
}
