package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    public List<RestaurantEntity> getAllRestaurants() {
        return restaurantDao.getAllRestaurants();
    }

    public List<RestaurantEntity> getRestaurantByName(String restaurantName) {
        return restaurantDao.getRestaurantByName(restaurantName);
    }

    public List<RestaurantEntity> getRestaurantByRestaurantId(String restaurantId) {
        return restaurantDao.getRestaurantByRestaurantId(restaurantId);
    }

    public List<RestaurantEntity> getRestaurantByCategoryId(String categoryId) {
        return restaurantDao.getRestaurantByCategoryId(categoryId);
    }


}
