package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service

public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    public List<RestaurantEntity> getAllRestaurants() {
        return restaurantDao.getAllRestaurants();
    }

    public List<RestaurantEntity> restaurantsByRating() {return restaurantDao.getAllRestaurants();}

    public List<RestaurantEntity> restaurantsByName(String restaurantName) throws RestaurantNotFoundException {
        return restaurantDao.restaurantsByName(restaurantName);
    }

    public RestaurantEntity restaurantByUUID(String restaurantId) throws RestaurantNotFoundException {
        return restaurantDao.restaurantByUUID(restaurantId);
    }

    public List<RestaurantEntity> restaurantByCategory(String categoryId) throws CategoryNotFoundException {
        return restaurantDao.restaurantByCategory(categoryId);
    }

    public RestaurantEntity updateRestaurantRating(RestaurantEntity restaurantEntity, Double newRating) throws InvalidRatingException {
        restaurantDao.updateRestaurantDetails(restaurantEntity);
        return restaurantEntity;
    }

    public List<CategoryEntity> getCategoriesByRestaurant(String restaurantId) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantByUUID(restaurantId);
        List<CategoryEntity> listOfCategories = new ArrayList<>();
        for(CategoryEntity c : restaurantEntity.getCategories()) {
            CategoryEntity temp = new CategoryEntity();
            temp.setUuid(c.getUuid().toString());
            temp.setCategoryName(c.getCategoryName());
            listOfCategories.add(temp);
        }
        return listOfCategories;
    }


}
