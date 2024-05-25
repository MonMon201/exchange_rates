package com.example.repository;

import com.example.model.ExchangeRate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ExchangeRateRepository {

    private EntityManagerFactory entityManagerFactory;

    public ExchangeRateRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public List<ExchangeRate> getAllExchangeRates() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ExchangeRate> exchangeRates = entityManager.createQuery("SELECT e FROM ExchangeRate e", ExchangeRate.class).getResultList();
        entityManager.close();
        return exchangeRates;
    }

    public void addExchangeRate(ExchangeRate exchangeRate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(exchangeRate);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateExchangeRate(ExchangeRate exchangeRate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ExchangeRate existingRate = entityManager.find(ExchangeRate.class, exchangeRate.getId());
        if (existingRate != null) {
            existingRate.setCurrency(exchangeRate.getCurrency());
            existingRate.setDate(exchangeRate.getDate());
            existingRate.setBuyingRate(exchangeRate.getBuyingRate());
            existingRate.setSellingRate(exchangeRate.getSellingRate());
            entityManager.getTransaction().commit();
        }
        entityManager.close();
    }

    public void deleteExchangeRate(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ExchangeRate exchangeRate = entityManager.find(ExchangeRate.class, id);
        if (exchangeRate != null) {
            entityManager.remove(exchangeRate);
            entityManager.getTransaction().commit();
        }
        entityManager.close();
    }
}
