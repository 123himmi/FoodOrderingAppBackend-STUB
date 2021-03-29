
package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public CustomerDao() {
    }

    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        this.entityManager.persist(customerEntity);
        return customerEntity;
    }

    public CustomerEntity getCustomerById(final Integer customerId) {
        try {
            return (CustomerEntity)this.entityManager.createNamedQuery("customerById", CustomerEntity.class).setParameter("id", customerId).getSingleResult();
        } catch (NoResultException var3) {
            return null;
        }
    }

    public CustomerEntity getCustomerByUuid(final String uuid) {
        try {
            return (CustomerEntity)this.entityManager.createNamedQuery("customerByUuid", CustomerEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException var3) {
            return null;
        }
    }

    public CustomerEntity getCustomerByContactNumber(final String customerContactNumber) {
        try {
            return (CustomerEntity)this.entityManager.createNamedQuery("customerByContactNumber", CustomerEntity.class).setParameter("contactNumber", customerContactNumber).getSingleResult();
        } catch (NoResultException var3) {
            return null;
        }
    }

    public CustomerAuthEntity createAuthToken(final CustomerAuthEntity customerAuthTokenEntity) {
        this.entityManager.persist(customerAuthTokenEntity);
        return customerAuthTokenEntity;
    }

    public void updateCustomer(final CustomerEntity updatedCustomerEntity) {
        this.entityManager.merge(updatedCustomerEntity);
    }

    public void updateCustomerAuth(final CustomerAuthEntity customerAuthTokenEntity) {
        this.entityManager.merge(customerAuthTokenEntity);
    }

    public CustomerEntity deleteCustomer(final CustomerEntity customerEntity) {
        this.entityManager.remove(customerEntity);
        return customerEntity;
    }

    public CustomerAuthEntity getCustomerAuthToken(final String accessToken) {
        try {
            return (CustomerAuthEntity)this.entityManager.createNamedQuery("customerAuthTokenByAccessToken", CustomerAuthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException var3) {
            return null;
        }
    }

    public CustomerAuthEntity getCustomerAuthTokenById(final Long customerId) {
        try {
            return (CustomerAuthEntity)this.entityManager.createNamedQuery("customerAuthTokenById", CustomerAuthEntity.class).setParameter("customer", customerId).getSingleResult();
        } catch (NoResultException var3) {
            return null;
        }
    }
}
