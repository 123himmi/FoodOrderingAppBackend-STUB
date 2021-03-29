package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "item")
@NamedQueries({
        @NamedQuery(name = "getItem", query = "select i from ItemEntity i where i.uuid = :itemdId")
})

public class ItemEntity implements Serializable {

    @Id
    @NotNull
    @Column(name = "ID")
    Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    String uuid;

    @Column(name = "item_name")
    @Size(max = 30)
    String itemName;

    @Column(name = "price")
    Integer price;

    @Column(name = "type")
    String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
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
}
