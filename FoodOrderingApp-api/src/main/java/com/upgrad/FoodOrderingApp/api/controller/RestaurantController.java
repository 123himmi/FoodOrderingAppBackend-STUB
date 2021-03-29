package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping

public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(method = RequestMethod.GET, value = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants() {

        List<RestaurantEntity> restaurantEntityList = restaurantService.getAllRestaurants();
        List<RestaurantList> restaurantList = new ArrayList<>();
        for (RestaurantEntity r : restaurantEntityList) {
            RestaurantList temp = new RestaurantList();
            /*
            Adding address per restaurant list
             */
            RestaurantDetailsResponseAddress tempAddress = new RestaurantDetailsResponseAddress();
            tempAddress.setCity(r.getAddress().getCity());
            tempAddress.setFlatBuildingName(r.getAddress().getFlatBuildingNumber());
            tempAddress.setId(r.getAddress().getUuid());
            tempAddress.setLocality(r.getAddress().getLocality());
            tempAddress.setPincode(r.getAddress().getPincode());
            /*
            Adding state per address per restaurant list
            */
            RestaurantDetailsResponseAddressState tempState = new RestaurantDetailsResponseAddressState();
            tempState.setId(r.getAddress().getState().getUuid());
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for (CategoryEntity c : r.getCategories()) {
                tempCategories.add(c.getCategoryName().toString());
            }
            temp.setCategories(tempCategories.toString());
            /*
            Adding rest of the attributes for restaurant list
             */
            temp.setId(UUID.fromString(r.getUuid()));
            temp.setRestaurantName(r.getRestaurantName());
            temp.setAddress(tempAddress);
            temp.setAveragePrice(r.getAvgPriceForTwo());
            temp.setCustomerRating(new BigDecimal(r.getCustomerRating()));
            temp.setPhotoURL(r.getPhotoUrl());
            temp.setNumberCustomersRated(r.getNumberOfCustomersRated());
            restaurantList.add(temp);
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        restaurantListResponse.setRestaurants(restaurantList);

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/restaurant/name/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> restaurantsByName(@PathVariable(name = "restaurant_name") String restaurantName) throws RestaurantNotFoundException
    {
        if(restaurantName.replace(" ", "").equals("")) {
            throw new RestaurantNotFoundException("RNF-003","Restaurant name field should not be empty");
        }

        List<RestaurantEntity> restaurantEntityList = restaurantService.restaurantsByName(restaurantName);
        List<RestaurantList> restaurantList = new ArrayList<>();
        for (RestaurantEntity r : restaurantEntityList) {
            RestaurantList temp = new RestaurantList();
            /*
            Adding address per restaurant list
             */
            RestaurantDetailsResponseAddress tempAddress = new RestaurantDetailsResponseAddress();
            tempAddress.setCity(r.getAddress().getCity());
            tempAddress.setFlatBuildingName(r.getAddress().getFlatBuildingNumber());
            tempAddress.setId(r.getAddress().getUuid());
            tempAddress.setLocality(r.getAddress().getLocality());
            tempAddress.setPincode(r.getAddress().getPincode());
            /*
            Adding state per address per restaurant list
            */
            RestaurantDetailsResponseAddressState tempState = new RestaurantDetailsResponseAddressState();
            tempState.setId(r.getAddress().getState().getUuid());
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for (CategoryEntity c : r.getCategories()) {
                tempCategories.add(c.getCategoryName().toString());
            }
            temp.setCategories(tempCategories.toString());
            /*
            Adding rest of the attributes for restaurant list
             */
            temp.setId(UUID.fromString(r.getUuid()));
            temp.setRestaurantName(r.getRestaurantName());
            temp.setAddress(tempAddress);
            temp.setAveragePrice(r.getAvgPriceForTwo());
            temp.setCustomerRating(new BigDecimal(r.getCustomerRating()));
            temp.setPhotoURL(r.getPhotoUrl());
            temp.setNumberCustomersRated(r.getNumberOfCustomersRated());
            restaurantList.add(temp);
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        restaurantListResponse.setRestaurants(restaurantList);

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> restaurantByCategory(@PathVariable(name = "category_id") String categoryId) throws CategoryNotFoundException
    {
        if(categoryId.replace(" ", "").equals("")) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        List<RestaurantEntity> restaurantEntityList = restaurantService.restaurantByCategory(categoryId);

        if(restaurantEntityList == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        List<RestaurantList> restaurantList = new ArrayList<>();
        for (RestaurantEntity r : restaurantEntityList) {
            RestaurantList temp = new RestaurantList();
            /*
            Adding address per restaurant list
             */
            RestaurantDetailsResponseAddress tempAddress = new RestaurantDetailsResponseAddress();
            tempAddress.setCity(r.getAddress().getCity());
            tempAddress.setFlatBuildingName(r.getAddress().getFlatBuildingNumber());
            tempAddress.setId(r.getAddress().getUuid());
            tempAddress.setLocality(r.getAddress().getLocality());
            tempAddress.setPincode(r.getAddress().getPincode());
            /*
            Adding state per address per restaurant list
            */
            RestaurantDetailsResponseAddressState tempState = new RestaurantDetailsResponseAddressState();
            tempState.setId(r.getAddress().getState().getUuid());
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for (CategoryEntity c : r.getCategories()) {
                tempCategories.add(c.getCategoryName().toString());
            }
            temp.setCategories(tempCategories.toString());
            /*
            Adding rest of the attributes for restaurant list
             */
            temp.setId(UUID.fromString(r.getUuid()));
            temp.setRestaurantName(r.getRestaurantName());
            temp.setAddress(tempAddress);
            temp.setAveragePrice(r.getAvgPriceForTwo());
            temp.setCustomerRating(new BigDecimal(r.getCustomerRating()));
            temp.setPhotoURL(r.getPhotoUrl());
            temp.setNumberCustomersRated(r.getNumberOfCustomersRated());
            restaurantList.add(temp);
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        restaurantListResponse.setRestaurants(restaurantList);

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantByRestaurantId(@PathVariable(name = "restaurant_id") String restaurantId) throws RestaurantNotFoundException
    {

        if(restaurantId.replace(" ", "").equals("")) {
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }

        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantId);

        if(restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }

        List<RestaurantList> restaurantList = new ArrayList<>();
        RestaurantList temp = new RestaurantList();
            /*
            Adding address per restaurant list
             */
        RestaurantDetailsResponseAddress tempAddress = new RestaurantDetailsResponseAddress();
        tempAddress.setCity(restaurantEntity.getAddress().getCity());
        tempAddress.setFlatBuildingName(restaurantEntity.getAddress().getFlatBuildingNumber());
        tempAddress.setId(restaurantEntity.getAddress().getUuid());
        tempAddress.setLocality(restaurantEntity.getAddress().getLocality());
        tempAddress.setPincode(restaurantEntity.getAddress().getPincode());
            /*
            Adding state per address per restaurant list
            */
        RestaurantDetailsResponseAddressState tempState = new RestaurantDetailsResponseAddressState();
        tempState.setId(restaurantEntity.getAddress().getState().getUuid());
        tempState.setStateName(restaurantEntity.getAddress().getState().getStateName());
        tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
        StringJoiner tempCategories = new StringJoiner(", ");
        for (CategoryEntity c : restaurantEntity.getCategories()) {
            tempCategories.add(c.getCategoryName().toString());
        }
        temp.setCategories(tempCategories.toString());
            /*
            Adding rest of the attributes for restaurant list
             */
        temp.setId(UUID.fromString(restaurantEntity.getUuid()));
        temp.setRestaurantName(restaurantEntity.getRestaurantName());
        temp.setAddress(tempAddress);
        temp.setAveragePrice(restaurantEntity.getAvgPriceForTwo());
        temp.setCustomerRating(new BigDecimal(restaurantEntity.getCustomerRating()));
        temp.setPhotoURL(restaurantEntity.getPhotoUrl());
        temp.setNumberCustomersRated(restaurantEntity.getNumberOfCustomersRated());
        restaurantList.add(temp);

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        restaurantListResponse.setRestaurants(restaurantList);

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurantDetails(@PathVariable(name = "restaurant_id") String restaurantId, @RequestHeader(name = "customer_rating") BigDecimal customerRating)
            throws AuthorizationFailedException, RestaurantNotFoundException, InvalidRatingException
    {
        if(restaurantId.replace(" ", "").equals("")) {
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }

        if(customerRating.doubleValue() < 1 && customerRating.doubleValue() > 5) {
            throw new InvalidRatingException("IRE-001", "Restaurant should be in the range of 1 to 5");
        }

        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantId);

        if(restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }

        /* Extract the number Of Customers and avg rating from Restaurant Entity
           Perform the calculation New Average Rating as No Of Customers multiplied by Avg Rating
           Add the new rating to this sum and divide by No Of Customers + 1
         */
        Integer tempNoOfCustomers = restaurantEntity.getNumberOfCustomersRated();
        Double tempAvgRating = restaurantEntity.getCustomerRating();
        Double newAvgRating = (tempAvgRating*tempNoOfCustomers + tempAvgRating)/(tempNoOfCustomers+1);
        restaurantEntity.setCustomerRating(newAvgRating);
        restaurantEntity.setNumberOfCustomersRated(restaurantEntity.getNumberOfCustomersRated()+1);
        RestaurantEntity updatedRestaurantEntity = restaurantService.updateRestaurantRating(restaurantEntity, newAvgRating.doubleValue());
        RestaurantUpdatedResponse restaurantListResponse = new RestaurantUpdatedResponse();
        restaurantListResponse.setId(UUID.fromString(restaurantEntity.getUuid()));

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }
}
