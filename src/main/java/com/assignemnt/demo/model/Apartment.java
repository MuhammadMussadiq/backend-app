package com.assignemnt.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TBL_APARTMENT")
public class Apartment extends BaseEntity implements Serializable {

    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "DESCRIPTION", nullable = false)
    @Lob
    private String description;
    @Column(name = "NO_OF_ROOMS", nullable = false)
    private Integer noOfRooms;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Photo> photos;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Integer noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
