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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.*;

@RestController
@RequestMapping

public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(method = RequestMethod.GET, value = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants(@RequestHeader(name="authorization") String authorization) {

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
            String tempCategories = "";
            for(CategoryEntity category : r.getCategories()) {
                tempCategories += category.getCategoryName() + ",";
            }
            System.out.println("Categories : " + tempCategories);
            temp.setCategories(tempCategories);
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
