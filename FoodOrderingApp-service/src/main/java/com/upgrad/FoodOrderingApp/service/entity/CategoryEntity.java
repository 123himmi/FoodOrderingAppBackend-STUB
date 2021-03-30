package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.businness.ItemService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "getAllCategories", query = "select c from CategoryEntity c order by c.categoryName"),
        @NamedQuery(name = "getCategoryById", query = "select c from CategoryEntity c where c.uuid=:categoryId")
})

public class CategoryEntity implements Serializable {

    @Id
    @NotNull
    @Column(name = "ID")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "category_name")
    @Size(max = 255)
    private String categoryName;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @ManyToMany(mappedBy = "categories")
    Set<RestaurantEntity> restaurantCategories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    Set<ItemEntity> items = new HashSet<>();

    public List<ItemEntity> getItems() {
        List<ItemEntity> itemsList = new ArrayList<>();
        for(ItemEntity i : items) {
            itemsList.add(i);
        }
        return itemsList;
    }

    public void setItems(List<ItemEntity> items) {
        Set<ItemEntity> itemEntitySet = new HashSet<>();
        for(ItemEntity i : items) {
            itemEntitySet.add(i);
        }

        this.items = itemEntitySet;
    }

    public Set<RestaurantEntity> getRestaurantCategories() {
        return restaurantCategories;
    }

    public void setRestaurantCategories(Set<RestaurantEntity> restaurantCategories) {
        this.restaurantCategories = restaurantCategories;
    }
}
