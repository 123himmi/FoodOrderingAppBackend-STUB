package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerAdminBusinessService;
import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/")
public class AddressController {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerAdminBusinessService customerAdminBusinessService;

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
    // Get All Saved Addresses endpoint requests Bearer authorization of the customer
    // and gets the addresses successfully.
    @RequestMapping(method = RequestMethod.GET, path = "/address/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getAllPermanentAddress(@RequestHeader("authorization") String authorization)
            throws AuthorizationFailedException {

        // Splits the Bearer authorization text as Bearer and bearerToken
        String[] bearerToken = authorization.split("Bearer ");

        // Calls the getAllAddress method by passing the bearer token
        List<AddressEntity> addressEntityList = addressService.getAllAddress(bearerToken[1]);

        AddressListResponse addressListResponse = new AddressListResponse();

        // Loops thru the addressEntityList to get details
        for (AddressEntity ae : addressEntityList) {

            // Calls the getStateById method by passing stateId
            StateEntity se = addressService.getStateById(ae.getState().getId());

            AddressListState addressListState = new AddressListState();

            // Sets the state to each address element
            addressListState.setStateName(se.getStateName());

            // Adds the city, flat building name, locality, pincode and state to the addressList
            AddressList addressList = new AddressList().id(UUID.fromString(ae.getUuid())).city(ae.getCity())
                    .flatBuildingName(ae.getFlatBuildingNumber()).locality(ae.getLocality())
                    .pincode(ae.getPincode()).state(addressListState);

            // Adds the addressList to addressListResponse
            addressListResponse.addAddressesItem(addressList);
        }

        // Returns the AddressListResponse with OK http status
        return new ResponseEntity<AddressListResponse>(addressListResponse, HttpStatus.OK);
    }

    // Delete Saved Address endpoint requests Bearer authorization of the customer and Address UUID as path variable
    // and deletes the address successfully.
    @RequestMapping(method = RequestMethod.DELETE, path = "/address/{address_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeleteAddressResponse> deleteAddress(String authorization,
                                                               @PathVariable("address_id") String addressUuid)
            throws AuthorizationFailedException, AddressNotFoundException {

        // Splits the Bearer authorization text as Bearer and bearerToken
        String[] bearerToken = authorization.split("Bearer ");

        // Calls the deleteAddress with addressId and bearerToken as argument
        AddressEntity deletedAddress = addressService.deleteAddress(addressUuid, bearerToken[1]);

        // Loads the DeleteAddressResponse with uuid of the address and the respective status message
        DeleteAddressResponse deleteAddressResponse = new DeleteAddressResponse().id(UUID.fromString(deletedAddress.getUuid()))
                .status("ADDRESS DELETED SUCCESSFULLY");

        // Returns the DeleteAddressResponse with OK http status
        return new ResponseEntity<DeleteAddressResponse>(deleteAddressResponse, HttpStatus.OK);
    }

    // Get All States endpoint gets all the states successfully.
    @RequestMapping(method = RequestMethod.GET, path = "/states", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StatesListResponse> getAllStates() throws AuthorizationFailedException {

        // Calls the getAllStates from addressService
        List<StateEntity> stateEntityList = addressService.getAllStates();
        StatesListResponse stateListResponse = new StatesListResponse();

        // Loops thru the stateEntityList to load the StatesListResponse
        for (StateEntity se : stateEntityList) {
            StatesList state = new StatesList();
            state.setStateName(se.getStateName());
            state.setId(UUID.fromString(se.getUuid()));
            stateListResponse.addStatesItem(state);
        }

        return new ResponseEntity<StatesListResponse>(stateListResponse, HttpStatus.OK);
    }
}
