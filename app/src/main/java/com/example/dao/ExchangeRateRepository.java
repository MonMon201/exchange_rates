package com.example.dao;

import com.example.model.ExchangeRateEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

public class ExchangeRateRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");

    public List<ExchangeRateEntity> getAllExchangeRates() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createQuery("SELECT e FROM ExchangeRateEntity e", ExchangeRateEntity.class).getResultList();
    }

    @Transactional
    public void addExchangeRate(ExchangeRateEntity exchangeRateEntity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(exchangeRateEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Transactional
    public void updateExchangeRate(ExchangeRateEntity exchangeRateEntity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(exchangeRateEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Transactional
    public void deleteExchangeRate(String currency, String date) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        ExchangeRateEntity exchangeRateEntity = em.find(ExchangeRateEntity.class, new ExchangeRateEntity.ExchangeRateId(currency, date));
        if (exchangeRateEntity != null) {
            em.remove(exchangeRateEntity);
        }
        em.getTransaction().commit();
        em.close();
    }
}
