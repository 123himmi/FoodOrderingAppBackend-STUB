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
import java.util.UUID;

@RestController
@RequestMapping

public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, value = "/item/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ItemListResponse> getTopNItemsPerRestaurant(@PathVariable(name = "restaurant_id") String restaurantId) throws RestaurantNotFoundException {

        List<String> listOfItems = itemService.getTopNItemsForRestaurant(restaurantId);
        System.out.println(listOfItems.size());

        if(listOfItems == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        ItemListResponse itemListResponse = new ItemListResponse();
        for(String item : listOfItems) {
            System.out.println(item);
            ItemEntity itemEntity = itemService.getItem(item);
            ItemList listItem = new ItemList();
            listItem.setId(UUID.fromString(itemEntity.getUuid()));
            listItem.setItemName(itemEntity.getItemName());
            listItem.setPrice(itemEntity.getPrice());
            listItem.setItemType(ItemList.ItemTypeEnum.fromValue(itemEntity.getType()));
            itemListResponse.add(listItem);
        }
        return new ResponseEntity<>(itemListResponse, HttpStatus.OK);
    }
}
