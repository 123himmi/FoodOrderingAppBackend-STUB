package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, value = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants() throws RestaurantNotFoundException {

        List<RestaurantEntity> restaurantEntityList = restaurantService.restaurantsByRating();
        List<RestaurantList> restaurantList = new ArrayList<>();
        for (RestaurantEntity r : restaurantEntityList) {
            RestaurantList temp = new RestaurantList();
            /*
            Adding address per restaurant list
             */
            RestaurantDetailsResponseAddress tempAddress = new RestaurantDetailsResponseAddress();
            tempAddress.setCity(r.getAddress().getCity());
            tempAddress.setFlatBuildingName(r.getAddress().getFlatBuildingNumber());
            tempAddress.setId(UUID.fromString(r.getAddress().getUuid()));
            tempAddress.setLocality(r.getAddress().getLocality());
            tempAddress.setPincode(r.getAddress().getPincode());
            /*
            Adding state per address per restaurant list
            */
            RestaurantDetailsResponseAddressState tempState = new RestaurantDetailsResponseAddressState();
            tempState.setId(UUID.fromString(r.getAddress().getState().getUuid()));
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for (CategoryEntity c : categoryService.getCategoriesByRestaurant(r.getUuid())) {
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
            tempAddress.setId(UUID.fromString(r.getAddress().getUuid()));
            tempAddress.setLocality(r.getAddress().getLocality());
            tempAddress.setPincode(r.getAddress().getPincode());
            /*
            Adding state per address per restaurant list
            */
            RestaurantDetailsResponseAddressState tempState = new RestaurantDetailsResponseAddressState();
            tempState.setId(UUID.fromString(r.getAddress().getState().getUuid()));
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for (CategoryEntity c : categoryService.getCategoriesByRestaurant(r.getUuid())) {
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
    public ResponseEntity<RestaurantListResponse> restaurantByCategory(@PathVariable(name = "category_id") String categoryId) throws CategoryNotFoundException, RestaurantNotFoundException {
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
            tempAddress.setId(UUID.fromString(r.getAddress().getUuid()));
            tempAddress.setLocality(r.getAddress().getLocality());
            tempAddress.setPincode(r.getAddress().getPincode());
            /*
            Adding state per address per restaurant list
            */
            RestaurantDetailsResponseAddressState tempState = new RestaurantDetailsResponseAddressState();
            tempState.setId(UUID.fromString(r.getAddress().getState().getUuid()));
            tempState.setStateName(r.getAddress().getState().getStateName());
            tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
            StringJoiner tempCategories = new StringJoiner(", ");
            for (CategoryEntity c : categoryService.getCategoriesByRestaurant(r.getUuid())) {
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

    @RequestMapping(method = RequestMethod.GET, value = "/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurantByRestaurantId(@PathVariable(name = "restaurant_id") String restaurantId) throws RestaurantNotFoundException, CategoryNotFoundException {

        if(restaurantId.replace(" ", "").equals("")) {
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }

        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantId);

        if(restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }

        RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse();
            /*
            Adding address per restaurant list
             */
        RestaurantDetailsResponseAddress tempAddress = new RestaurantDetailsResponseAddress();
        tempAddress.setCity(restaurantEntity.getAddress().getCity());
        tempAddress.setFlatBuildingName(restaurantEntity.getAddress().getFlatBuildingNumber());
        tempAddress.setId(UUID.fromString(restaurantEntity.getAddress().getUuid()));
        tempAddress.setLocality(restaurantEntity.getAddress().getLocality());
        tempAddress.setPincode(restaurantEntity.getAddress().getPincode());
            /*
            Adding state per address per restaurant list
            */
        RestaurantDetailsResponseAddressState tempState = new RestaurantDetailsResponseAddressState();
        tempState.setId(UUID.fromString(restaurantEntity.getAddress().getState().getUuid()));
        tempState.setStateName(restaurantEntity.getAddress().getState().getStateName());
        tempAddress.setState(tempState);
            /*
            Adding categories per address to the restaurant list
             */
        List<CategoryList> categoryLists = new ArrayList<>();

        for (CategoryEntity c : categoryService.getCategoriesByRestaurant(restaurantId)) {
            CategoryList categoryList = new CategoryList();
            List<ItemList> itemLists = new ArrayList<>();
            for(ItemEntity i : itemService.getItemsByCategoryAndRestaurant(restaurantId, c.getUuid())) {
                ItemList itemList = new ItemList();
                itemList.setItemName(i.getItemName());
                itemList.setPrice(i.getPrice());
                Integer enumIndex = (i.getType().trim().equals("") || i.getType().equals('0')) ? 0 : 1;
                itemList.setItemType(ItemList.ItemTypeEnum.values()[enumIndex]);
                itemList.setId(UUID.fromString(i.getUuid()));
                itemLists.add(itemList);
            }
            categoryList.setId(UUID.fromString(c.getUuid()));
            categoryList.setCategoryName(c.getCategoryName());
            categoryList.setItemList(itemLists);
            categoryLists.add(categoryList);
        }
        restaurantDetailsResponse.setCategories(categoryLists);

            /*
            Adding rest of the attributes for restaurant list
             */
        restaurantDetailsResponse.setId(UUID.fromString(restaurantEntity.getUuid()));
        restaurantDetailsResponse.setRestaurantName(restaurantEntity.getRestaurantName());
        restaurantDetailsResponse.setAddress(tempAddress);
        restaurantDetailsResponse.setAveragePrice(restaurantEntity.getAvgPriceForTwo());
        restaurantDetailsResponse.setCustomerRating(new BigDecimal(restaurantEntity.getCustomerRating()));
        restaurantDetailsResponse.setPhotoURL(restaurantEntity.getPhotoUrl());
        restaurantDetailsResponse.setNumberCustomersRated(restaurantEntity.getNumberOfCustomersRated());

        return new ResponseEntity<>(restaurantDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurantDetails(@PathVariable(name = "restaurant_id") final String restaurantId,
                                                                             @RequestParam(name = "customer_rating") final Double customerRating,
                                                                             @RequestHeader("authorization") final String authorization
                                                                            )
            throws AuthorizationFailedException, RestaurantNotFoundException, InvalidRatingException
    {
        String[] bearerToken = authorization.split("Bearer ");

        if(customerService.getCustomer(bearerToken[1]) == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in");
        }

        if(restaurantId.replace(" ", "").equals("")) {
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }

        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantId);

        if(restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        restaurantService.updateRestaurantRating(restaurantEntity, customerRating);
        RestaurantUpdatedResponse restaurantListResponse = new RestaurantUpdatedResponse();
        restaurantListResponse.setId(UUID.fromString(restaurantId));
        restaurantListResponse.setStatus("RESTAURANT RATING UPDATED SUCCESSFULLY");
        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }
}
