package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service

public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RestaurantService restaurantService;

    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        return categoryDao.getAllCategoriesOrderedByName();
    }

    public CategoryEntity getCategoryById(String categoryId) throws CategoryNotFoundException
    {
        return categoryDao.getCategoryById(categoryId);
    }

    public List<CategoryEntity> getCategoriesByRestaurant(String restaurantId) throws RestaurantNotFoundException {
        return restaurantService.getCategoriesByRestaurant(restaurantId);
    }

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String categoryId) {
         CategoryEntity categoryEntity = categoryDao.getItemsByCategoryAndRestaurant(categoryId);
         List<ItemEntity> itemEntities = new ArrayList<>();
         for(ItemEntity i : categoryEntity.getItems()) {
             itemEntities.add(i);
         }
         return itemEntities;
    }
}
