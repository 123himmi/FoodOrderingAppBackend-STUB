package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository

public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RestaurantEntity> getAllRestaurants() {
        try {
            return entityManager.createNamedQuery("getAllRestaurants", RestaurantEntity.class).getResultList();
        }
        catch(NoResultException noResultException) {
            return null;
        }
    }

    public List<RestaurantEntity> getRestaurantByName(String restaurantName) {
        try {
            return entityManager.createNamedQuery("getRestaurantByName", RestaurantEntity.class).setParameter("restaurantName", restaurantName).getResultList();
        }
        catch(NoResultException noResultException) {
            return null;
        }
    }

    public RestaurantEntity restaurantByUUID(String restaurantId) {
        try {
            return entityManager.createNamedQuery("getRestaurantByRestaurantId", RestaurantEntity.class).setParameter("restaurantId", restaurantId).getSingleResult();
        }
        catch(NoResultException noResultException) {
            return null;
        }
    }

    public List<RestaurantEntity> getRestaurantByCategoryId(String categoryId) {
        try {
            return entityManager.createNamedQuery("getRestaurantByCategoryId", RestaurantEntity.class).setParameter("categoryId", categoryId).getResultList();
        }
        catch(NoResultException noResultException) {
            return null;
        }
    }

    @Transactional
    public void updateRestaurantDetails(RestaurantEntity restaurantEntity) {
            entityManager.merge(restaurantEntity);
        }
}
