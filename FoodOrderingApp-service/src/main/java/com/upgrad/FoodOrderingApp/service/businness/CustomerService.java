package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public CustomerEntity getCustomer(String accessToken) throws AuthorizationFailedException {
        if(customerDao.getCustomerAuthToken(accessToken) == null) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint");
        }

        CustomerEntity customerEntity = customerDao.getCustomerById(customerDao.getCustomerAuthToken(accessToken).getCustomer().getId());
        if(customerEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in");
        }
        return customerEntity;
    }
}
