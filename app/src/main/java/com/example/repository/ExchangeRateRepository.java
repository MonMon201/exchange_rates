package com.example.repository;

import com.example.model.ExchangeRate;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ExchangeRateRepository {

    private EntityManager entityManager;

    public ExchangeRateRepository() {
        entityManager = Persistence.createEntityManagerFactory("my-persistence-unit").createEntityManager();
    }

    public List<ExchangeRate> getAllExchangeRates() {
        TypedQuery<ExchangeRate> query = entityManager.createQuery("SELECT e FROM ExchangeRate e", ExchangeRate.class);
        return query.getResultList();
    }

    public void addExchangeRate(ExchangeRate exchangeRate) {
        entityManager.getTransaction().begin();
        entityManager.persist(exchangeRate);
        entityManager.getTransaction().commit();
    }

    public void updateExchangeRate(ExchangeRate exchangeRate) {
        entityManager.getTransaction().begin();
        entityManager.merge(exchangeRate);
        entityManager.getTransaction().commit();
    }

    public void deleteExchangeRate(Long id) {
        entityManager.getTransaction().begin();
        ExchangeRate exchangeRate = entityManager.find(ExchangeRate.class, id);
        if (exchangeRate != null) {
            entityManager.remove(exchangeRate);
        }
        entityManager.getTransaction().commit();
    }

    public ExchangeRate findById(Long id) {
        return entityManager.find(ExchangeRate.class, id);
    }

    public List<ExchangeRate> findByCurrency(String currency) {
        TypedQuery<ExchangeRate> query = entityManager.createQuery("SELECT e FROM ExchangeRate e WHERE e.currency = :currency", ExchangeRate.class);
        query.setParameter("currency", currency);
        return query.getResultList();
    }
}
