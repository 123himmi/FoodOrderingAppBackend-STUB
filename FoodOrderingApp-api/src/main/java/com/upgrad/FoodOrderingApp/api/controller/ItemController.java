package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping

public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, value = "/item/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ItemListResponse> getTopNItemsPerRestaurant(@PathVariable(name = "restaurant_id") String restaurantId) throws RestaurantNotFoundException {

        List<String> listOfItems = itemService.getTopNItemsForRestaurant(restaurantId);
        for(String item : listOfItems) {
            System.out.println("Printing values " + item);
        }
        ItemListResponse itemListResponse = new ItemListResponse();
        return new ResponseEntity<>(itemListResponse, HttpStatus.OK);
    }

}
