package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    // Creates customer by persisting the record in the database
    public AddressEntity saveAddress(AddressEntity addressEntity) {
        entityManager.persist(addressEntity);
        return addressEntity;
    }

    // Gets the customer details from the database based on id
    public CustomerEntity getCustomerById(final Integer customerId) {
        try {
            return entityManager.createNamedQuery("customerById", CustomerEntity.class).setParameter("id", customerId)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    // Gets the customer details from the database based on uuid
    public CustomerEntity getCustomerByUuid(final String uuid) {
        try {
            return entityManager.createNamedQuery("customerByUuid", CustomerEntity.class).setParameter("uuid", uuid)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    // Gets the customer details from the database based on contact number
    public CustomerEntity getCustomerByContactNumber(final String customerContactNumber) {
        try {
            return entityManager.createNamedQuery("customerByContactNumber", CustomerEntity.class).setParameter("contactNumber", customerContactNumber)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    // Creates auth token by persisting the record in the database
    public CustomerAuthEntity createAuthToken(final CustomerAuthEntity customerAuthTokenEntity) {
        entityManager.persist(customerAuthTokenEntity);
        return customerAuthTokenEntity;
    }

    // Updates the customer details to the database
    public void updateCustomer(final CustomerEntity updatedCustomerEntity) {
        entityManager.merge(updatedCustomerEntity);
    }

    //
    public void updateCustomerAuth(final CustomerAuthEntity customerAuthTokenEntity) {
        entityManager.merge(customerAuthTokenEntity);
    }

    public CustomerEntity deleteCustomer(final CustomerEntity customerEntity){
        entityManager.remove(customerEntity);
        return customerEntity;
    }

    public CustomerAuthEntity getCustomerAuthToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery("customerAuthTokenByAccessToken", CustomerAuthEntity.class)
                    .setParameter("accessToken", accessToken).getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public CustomerAuthEntity getCustomerAuthTokenById(final Long customerId) {
        try {
            return entityManager.createNamedQuery("customerAuthTokenById", CustomerAuthEntity.class)
                    .setParameter("customer", customerId).getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public AddressEntity getAddressById(final Long addressId) {
        try {
            return entityManager.createNamedQuery("addressById", AddressEntity.class).setParameter("id", addressId)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }

    }

    public AddressEntity getAddressByUuid(final String addressUuid) {
        try {
            return entityManager.createNamedQuery("addressByUuid", AddressEntity.class).setParameter("uuid", addressUuid)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }

    }

    public AddressEntity createAddress(AddressEntity addressEntity) {
        entityManager.persist(addressEntity);
        return addressEntity;
    }

    public AddressEntity deleteAddressByUuid(final AddressEntity addressEntity) {
        entityManager.remove(addressEntity);
        return addressEntity;

    }

}

