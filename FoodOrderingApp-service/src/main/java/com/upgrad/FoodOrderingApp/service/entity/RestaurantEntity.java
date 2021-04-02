package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "restaurant")
@NamedQueries({
        @NamedQuery(name = "getAllRestaurants", query = "select r from RestaurantEntity r"),
        @NamedQuery(name = "getRestaurantByRestaurantId", query = "select r from RestaurantEntity r where r.uuid = :restaurantId"),
        @NamedQuery(name = "getRestaurantByName", query = "select r from RestaurantEntity r where lower(r.restaurantName) like lower(concat('%',:restaurantName,'%'))"),
        @NamedQuery(name = "getRestaurantByCategoryId", query = "select r from RestaurantEntity r LEFT JOIN FETCH r.categories c where c.uuid = :categoryId")
})

public class RestaurantEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "restaurant_name")
    @Size(max = 50)
    @NotNull
    private String restaurantName;

    @Column(name = "photo_url")
    @Size(max = 255)
    @NotNull
    private String photoUrl;

    @Column(name = "customer_rating")
    private Double customerRating;

    @Column(name = "average_price_for_two")
    private Integer avgPrice;

    @Column(name = "number_of_customers_rated")
    private Integer numberCustomersRated;

    public Integer getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Integer avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Integer getNumberCustomersRated() {
        return numberCustomersRated;
    }

    public void setNumberCustomersRated(Integer numberCustomersRated) {
        this.numberCustomersRated = numberCustomersRated;
    }

    @OneToOne
    @NotNull
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToMany
    @JoinTable(
            name = "restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    Set<CategoryEntity> categories = new HashSet<>();

    @ManyToMany(mappedBy = "itemsInRestaurant")
    private Set<ItemEntity> restaurantItems = new HashSet<>();

    public Set<ItemEntity> getRestaurantItems() {
        return restaurantItems;
    }

    public void setRestaurantItems(Set<ItemEntity> restaurantItems) {
        this.restaurantItems = restaurantItems;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Double getCustomerRating() {
        return customerRating;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setCustomerRating(Double customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getAvgPriceForTwo() {
        return avgPrice;
    }

    public Integer getNumberOfCustomersRated() {
        return numberCustomersRated;
    }

    public void setNumberOfCustomersRated(Integer numberOfCustomersRated) {
        this.numberCustomersRated = numberOfCustomersRated;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }
}
