package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="restaurant_item")

public class RestaurantItemEntity implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="item_id")
    private Integer itemIdValue;

    @Column(name="restaurant_id")
    private Integer restaurantIdValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemIdValue() {
        return itemIdValue;
    }

    public void setItemIdValue(Integer itemIdValue) {
        this.itemIdValue = itemIdValue;
    }

    public Integer getRestaurantIdValue() {
        return restaurantIdValue;
    }

    public void setRestaurantIdValue(Integer restaurantIdValue) {
        this.restaurantIdValue = restaurantIdValue;
    }
}
