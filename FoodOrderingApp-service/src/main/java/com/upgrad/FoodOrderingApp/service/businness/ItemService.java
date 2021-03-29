package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ItemService {

    @Autowired
    ItemDao itemDao;

    public List<String> getTopNItemsForRestaurant(String restaurantId) {
        return itemDao.getTopNItemsForRestaurant(restaurantId);
    }

    public ItemEntity getItem(String itemId) {
        return itemDao.getItem(itemId);
    }
}
