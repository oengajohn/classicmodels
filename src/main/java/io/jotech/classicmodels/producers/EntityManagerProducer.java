package io.jotech.classicmodels.producers;


import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


public class EntityManagerProducer {

    @PersistenceContext(unitName = "TEST_PU")
    private EntityManager em;

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }

}