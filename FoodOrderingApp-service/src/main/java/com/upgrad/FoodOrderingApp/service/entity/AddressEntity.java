package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="address")

public class AddressEntity implements Serializable {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    @NotNull
    String uuid;

    @Column(name = "flat_buil_number")
    @Size(max = 255)
    String flatBuildingNumber;

    @Column(name = "locality")
    @Size(max = 255)
    String locality;

    @Column(name = "city")
    @Size(max = 30)
    String city;

    @Column(name = "pincode")
    @Size(max = 30)
    String pincode;

    @OneToOne
    @NotNull
    @JoinColumn(name = "state_id")
    private StateEntity state;

    public AddressEntity() {};

    public AddressEntity(String uuid, String flatBuildingNumber, String locality, String city, String pincode, StateEntity state) {
        this.uuid = uuid;
        this.flatBuildingNumber = flatBuildingNumber;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
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

    public String getFlatBuildingNumber() {
        return flatBuildingNumber;
    }

    public void setFlatBuildingNumber(String flatBuildingNumber) {
        this.flatBuildingNumber = flatBuildingNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }
}
