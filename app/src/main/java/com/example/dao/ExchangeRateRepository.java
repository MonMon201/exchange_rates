package com.example.dao;

import com.example.model.ExchangeRateEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ExchangeRateRepository {

    private EntityManagerFactory entityManagerFactory;

    public ExchangeRateRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public List<ExchangeRateEntity> getAllExchangeRates() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ExchangeRateEntity> exchangeRates = entityManager.createQuery("SELECT e FROM ExchangeRateEntity e", ExchangeRateEntity.class).getResultList();
        entityManager.close();
        return exchangeRates;
    }

    public void addExchangeRate(ExchangeRateEntity exchangeRate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(exchangeRate);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateExchangeRate(ExchangeRateEntity exchangeRate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ExchangeRateEntity existingRate = entityManager.find(ExchangeRateEntity.class, exchangeRate.getId());
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
        ExchangeRateEntity exchangeRate = entityManager.find(ExchangeRateEntity.class, id);
        if (exchangeRate != null) {
            entityManager.remove(exchangeRate);
            entityManager.getTransaction().commit();
        }
        entityManager.close();
    }
}
