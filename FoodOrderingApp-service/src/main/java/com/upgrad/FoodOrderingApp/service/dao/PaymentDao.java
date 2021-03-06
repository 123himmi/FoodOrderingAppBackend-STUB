package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository

public class PaymentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PaymentEntity> getPaymentMethods() {
        try {
            return entityManager.createNamedQuery("allPaymentMethods", PaymentEntity.class)
                    .getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public PaymentEntity getPaymentByUUID(String paymentId) {
        try{
            PaymentEntity paymentEntity = entityManager.createNamedQuery("paymentByUuid",PaymentEntity.class).setParameter("uuid",paymentId).getSingleResult();
            return paymentEntity;
        }catch (NoResultException nre){
            return null;
        }
    }
}
