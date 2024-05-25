package com.example.repository;

import com.example.model.Balance;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

public class BalanceRepository {

    private EntityManager entityManager;

    public BalanceRepository() {
        entityManager = Persistence.createEntityManagerFactory("my-persistence-unit").createEntityManager();
    }

    public List<Balance> getAllBalances() {
        TypedQuery<Balance> query = entityManager.createQuery("SELECT b FROM Balance b", Balance.class);
        return query.getResultList();
    }

    public void updateBalance(Balance balance) {
        entityManager.getTransaction().begin();
        entityManager.merge(balance);
        entityManager.getTransaction().commit();
    }

    public Balance findByCurrency(String currency) {
        TypedQuery<Balance> query = entityManager.createQuery("SELECT b FROM Balance b WHERE b.currency = :currency", Balance.class);
        query.setParameter("currency", currency);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}