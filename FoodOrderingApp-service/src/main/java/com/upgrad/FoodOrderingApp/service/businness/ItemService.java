package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.aspectj.weaver.ArrayReferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;

    public List<String> getTopNItemsForRestaurant(String restaurantId) {
        return itemDao.getTopNItemsForRestaurant(restaurantId);
    }

    public ItemEntity getItem(String itemId) {
        return itemDao.getItem(itemId);
    }

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId, String categoryId) {
        return categoryService.getItemsByCategoryAndRestaurant(categoryId);
    }
}
