package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        try {
            return entityManager.createNamedQuery("getAllCategories", CategoryEntity.class).getResultList();
        }
        catch(NoResultException noResultException) {
            return null;
        }
    }

    public CategoryEntity getCategoryById(String categoryId) {
        try {
            return entityManager.createNamedQuery("getCategoryById", CategoryEntity.class).setParameter("categoryId", categoryId).getSingleResult();
        }
        catch(NoResultException noResultException) {
            return null;
        }
    }
}
