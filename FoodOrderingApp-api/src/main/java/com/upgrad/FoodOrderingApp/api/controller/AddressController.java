package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/")
public class AddressController {

    @Autowired
    private AddressDao addressDao;


    @RequestMapping(method = RequestMethod.POST,path ="/address" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE ,produces =MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity<String> saveaddress(final AddressList addressList,
                                                           @RequestHeader("authorization") String authorizaton)
            throws AuthorizationFailedException, SaveAddressException {

        // Gets the contactNumber:password after base64 decoding
        byte[] decode = Base64.getDecoder().decode(authorizaton.split("Basic ")[1]);
        String decodedText = new String(decode);

        CustomerDao customerDao = new CustomerDao();
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthToken(decodedText);
        if(customerAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001","Customer is not Logged in.");
        }

        LoginResponse loginResponse = new LoginResponse().id(customerAuthEntity.getCustomer().getUuid());
        if(loginResponse == null){
            throw new AuthorizationFailedException("ATHR-002","Customer is logged out. Log in again to access this endpoint.");
        }
        if(addressList.getCity() == "" || addressList.getFlatBuildingName() == "" || addressList.getId().toString() == "" || addressList.getLocality() == "" ||
            addressList.getPincode() == "" || addressList.getState().getStateName() == ""){
            throw new AuthorizationFailedException("SAR-001","No field can be empty");
        }
        if(addressList.getPincode().length() != 6 && addressList.getPincode().matches("([0-9]+):(.+?)")){
            throw new SaveAddressException("SAR-002","Invalid pincode");

        }
        final CustomerEntity customerEntity = new CustomerEntity();
        final AddressEntity addressEntity = new AddressEntity();
        // Adds all the attributes provided to the customer entity
        addressEntity.setUuid(UUID.randomUUID().toString());
        addressEntity.setFlatBuildingNumber(addressList.getFlatBuildingName());
        addressEntity.setLocality(addressList.getLocality());
        addressEntity.setCity(addressList.getCity());
        addressEntity.setPincode(addressList.getPincode());
        addressEntity.setState(addressEntity.getState());

        // Calls the signup method of customer service with the provided attributes
        final AddressEntity newAddressEntity = addressDao.saveAddress(addressEntity);

        // Returns the SignupCustomerResponse with resource created http status
        return new ResponseEntity<String>("ADDRESS SUCCESSFULLY REGISTERED", HttpStatus.CREATED);

    }
}
