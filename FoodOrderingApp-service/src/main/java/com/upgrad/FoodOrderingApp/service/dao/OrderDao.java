package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository

public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CouponEntity getCouponByName(String couponName) {
        try {
            return entityManager.createNamedQuery("couponByName", CouponEntity.class).setParameter("couponName", couponName)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public List<OrderEntity> getOrdersByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("ordersByUuid", OrderEntity.class).setParameter("uuid", uuid)
                    .getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public OrderEntity saveOrder(OrderEntity orderEntity) {
        this.entityManager.persist(orderEntity);
        return orderEntity;
    }

    public OrderItemEntity saveOrder(OrderItemEntity orderItemEntity) {
        this.entityManager.persist(orderItemEntity);
        return orderItemEntity;
    }

}
