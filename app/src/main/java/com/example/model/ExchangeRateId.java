package com.example.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class ExchangeRateId implements Serializable {
    private String currency;
    private String date;

    // Default constructor
    public ExchangeRateId() {}

    public ExchangeRateId(String currency, String date) {
        this.currency = currency;
        this.date = date;
    }

    // hashCode and equals
    @Override
    public int hashCode() {
        return Objects.hash(currency, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateId that = (ExchangeRateId) o;
        return Objects.equals(currency, that.currency) &&
                Objects.equals(date, that.date);
    }
}
