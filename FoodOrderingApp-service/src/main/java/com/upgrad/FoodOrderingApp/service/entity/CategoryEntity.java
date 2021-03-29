package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "getAllCategories", query = "select c from CategoryEntity c"),
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

    @ManyToMany
    @JoinTable(
            name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<ItemEntity> items = new HashSet<>();

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
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

    public void setItems(Set<ItemEntity> items) {
        this.items = items;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public Set<RestaurantEntity> getRestaurantCategories() {
        return restaurantCategories;
    }

    public void setRestaurantCategories(Set<RestaurantEntity> restaurantCategories) {
        this.restaurantCategories = restaurantCategories;
    }
}
