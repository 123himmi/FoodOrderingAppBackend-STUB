package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "category_item")

public class CategoryItemEntity implements Serializable {

    @Id
    @NotNull
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="item_id")
    private Integer itemIdValue;

    @Column(name = "category_id")
    private Integer categoryIdValue;

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

    public Integer getCategoryIdValue() {
        return categoryIdValue;
    }

    public void setCategoryIdValue(Integer categoryIdValue) {
        this.categoryIdValue = categoryIdValue;
    }
}
