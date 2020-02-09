package com.assignemnt.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class ApartmentDto {

    private Long id;
    @NotBlank(message = "Address field is required")
    private String address;
    @NotBlank(message = "Description field is required")
    private String description;
    @Min(value = 1, message = "Number of rooms field must be greater than 0")
    private int noOfRooms;

    private List<PhotoDto> photos;
    @JsonIgnore
    private List<Long> previousIds;

    @JsonIgnore
    public List<Long> getPreviousIds() {
        return previousIds;
    }

    @JsonProperty
    public void setPreviousIds(List<Long> removedIds) {
        this.previousIds = removedIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public List<PhotoDto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDto> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "ApartmentDto{" +
                "address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", noOfRooms='" + noOfRooms + '\'' +
                ", photos='" + photos + '\'' +
                '}';
    }
}
