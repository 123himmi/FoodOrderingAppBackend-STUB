package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.common.ItemType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
        @NamedQuery(name = "getItemsByRestaurant", query = "select i from ItemEntity i")
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

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<CategoryEntity> restaurantItems = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "restaurant_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    Set<RestaurantEntity> itemsInRestaurant = new HashSet<>();

    public Set<RestaurantEntity> getItemsInRestaurant() {
        return itemsInRestaurant;
    }

    public void setItemsInRestaurant(Set<RestaurantEntity> itemsInRestaurant) {
        this.itemsInRestaurant = itemsInRestaurant;
    }

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
