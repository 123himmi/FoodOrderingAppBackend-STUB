package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.common.ItemType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "item")
@NamedQueries({
        @NamedQuery(name = "getItem", query = "select i from ItemEntity i where i.uuid = :itemdId"),
        @NamedQuery(name = "getItemsByCategory", query = "select i from CategoryEntity c " +
                "JOIN CategoryItemEntity ci ON ci.id = c.id " +
                "JOIN ItemEntity i ON i.id = ci.itemIdValue " +
                "WHERE c.uuid = :categoryId"),
        @NamedQuery(name = "getItemsByRestaurantAndCategory",
                query = "select i from RestaurantEntity r " +
                        "JOIN RestaurantCategoryEntity rc ON r.id = rc.restaurantIdValue " +
                        "JOIN CategoryEntity c ON rc.categoryIdValue = c.id " +
                        "JOIN CategoryItemEntity ci ON rc.categoryIdValue = ci.categoryIdValue " +
                        "JOIN ItemEntity i ON ci.itemIdValue = i.id " +
                        "where r.uuid = :restaurantId AND c.uuid = :categoryId")
})

public class ItemEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "item_name")
    @Size(max = 30)
    private String itemName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "type")
    private String type;

    public Set<CategoryEntity> getRestaurantItems() {
        return restaurantItems;
    }

    public void setRestaurantItems(Set<CategoryEntity> restaurantItems) {
        this.restaurantItems = restaurantItems;
    }

    @ManyToMany(mappedBy = "items")
    private Set<CategoryEntity> restaurantItems = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(ItemType enumType) {
        this.type = enumType.toString();
    }
}
