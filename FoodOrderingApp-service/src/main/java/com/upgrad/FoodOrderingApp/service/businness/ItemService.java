package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.common.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service

public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;

    public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurantEntity) {
        List<String> itemIds = itemDao.getItemsIdsByPopularity(restaurantEntity.getUuid());
        List<ItemEntity> itemEntities = new ArrayList<>();
        for(String itemId : itemIds) {
            ItemEntity itemEntity = getItem(itemId);
            itemEntities.add(itemEntity);
        }
        return itemEntities;
    }

    public ItemEntity getItem(String itemId) {
        return itemDao.getItem(itemId);
    }

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId, String categoryId) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryId);
        List<ItemEntity> itemsInRestaurant = getItemsByRestaurant(restaurantId);
        List<ItemEntity> itemsInRestaurantCategory = new ArrayList<>();
        for(ItemEntity i : itemsInRestaurant) {
            for(ItemEntity j : categoryEntity.getItems()) {
                if(i.getUuid().equals(j.getUuid())) {
                    itemsInRestaurantCategory.add(i);
                }
            }
        }
        return itemsInRestaurantCategory;
    }

    public List<ItemEntity> getItemsByRestaurant(String restaurantId) {
        return itemDao.getItemsByRestaurant(restaurantId);
    }
}
