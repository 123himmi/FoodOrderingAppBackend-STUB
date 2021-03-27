package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddressState;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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
        for(RestaurantEntity r : restaurantEntityList) {
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
            RestaurantDetailsResponseAddressState tempState= new RestaurantDetailsResponseAddressState();
            tempState.setId(r.getAddress().getState().getUuid());
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for(CategoryEntity c : r.getCategories()) {
                tempCategories.add(c.getCategoryName().toString());
            }
            temp.setCategories(tempCategories.toString());
            /*
            Adding rest of the attributes for restaurant list
             */
            temp.setId(r.getUuid());
            temp.setRestaurantName(r.getRestaurantName());
            temp.setAddress(tempAddress);
            temp.setAveragePrice(r.getAvgPriceForTwo());
            temp.setCustomerRating(r.getCustomerRating());
            temp.setPhotoURL(r.getPhotoUrl());
            temp.setNumberCustomersRated(r.getNumberOfCustomersRated());
            restaurantList.add(temp);
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        restaurantListResponse.setRestaurants(restaurantList);

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/restaurant/name/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantByName(@PathVariable(name="restaurant_name") String restaurantName) {

        List<RestaurantEntity> restaurantEntityList = restaurantService.getRestaurantByName(restaurantName);
        List<RestaurantList> restaurantList = new ArrayList<>();
        for(RestaurantEntity r : restaurantEntityList) {
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
            RestaurantDetailsResponseAddressState tempState= new RestaurantDetailsResponseAddressState();
            tempState.setId(r.getAddress().getState().getUuid());
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for(CategoryEntity c : r.getCategories()) {
                tempCategories.add(c.getCategoryName().toString());
            }
            temp.setCategories(tempCategories.toString());
            /*
            Adding rest of the attributes for restaurant list
             */
            temp.setId(r.getUuid());
            temp.setRestaurantName(r.getRestaurantName());
            temp.setAddress(tempAddress);
            temp.setAveragePrice(r.getAvgPriceForTwo());
            temp.setCustomerRating(r.getCustomerRating());
            temp.setPhotoURL(r.getPhotoUrl());
            temp.setNumberCustomersRated(r.getNumberOfCustomersRated());
            restaurantList.add(temp);
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        restaurantListResponse.setRestaurants(restaurantList);

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantByCategoryId(@PathVariable(name="category_id") String categoryId) {

        List<RestaurantEntity> restaurantEntityList = restaurantService.getRestaurantByCategoryId(categoryId);
        List<RestaurantList> restaurantList = new ArrayList<>();
        for(RestaurantEntity r : restaurantEntityList) {
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
            RestaurantDetailsResponseAddressState tempState= new RestaurantDetailsResponseAddressState();
            tempState.setId(r.getAddress().getState().getUuid());
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for(CategoryEntity c : r.getCategories()) {
                tempCategories.add(c.getCategoryName().toString());
            }
            temp.setCategories(tempCategories.toString());
            /*
            Adding rest of the attributes for restaurant list
             */
            temp.setId(r.getUuid());
            temp.setRestaurantName(r.getRestaurantName());
            temp.setAddress(tempAddress);
            temp.setAveragePrice(r.getAvgPriceForTwo());
            temp.setCustomerRating(r.getCustomerRating());
            temp.setPhotoURL(r.getPhotoUrl());
            temp.setNumberCustomersRated(r.getNumberOfCustomersRated());
            restaurantList.add(temp);
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        restaurantListResponse.setRestaurants(restaurantList);

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantByRestaurantId(@PathVariable(name="restaurant_id") String restaurantId) {

        List<RestaurantEntity> restaurantEntityList = restaurantService.getRestaurantByRestaurantId(restaurantId);
        List<RestaurantList> restaurantList = new ArrayList<>();
        for(RestaurantEntity r : restaurantEntityList) {
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
            RestaurantDetailsResponseAddressState tempState= new RestaurantDetailsResponseAddressState();
            tempState.setId(r.getAddress().getState().getUuid());
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for(CategoryEntity c : r.getCategories()) {
                tempCategories.add(c.getCategoryName().toString());
            }
            temp.setCategories(tempCategories.toString());
            /*
            Adding rest of the attributes for restaurant list
             */
            temp.setId(r.getUuid());
            temp.setRestaurantName(r.getRestaurantName());
            temp.setAddress(tempAddress);
            temp.setAveragePrice(r.getAvgPriceForTwo());
            temp.setCustomerRating(r.getCustomerRating());
            temp.setPhotoURL(r.getPhotoUrl());
            temp.setNumberCustomersRated(r.getNumberOfCustomersRated());
            restaurantList.add(temp);
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        restaurantListResponse.setRestaurants(restaurantList);

        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }


}
