package com.assignemnt.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TBL_PHOTO")
public class Photo extends BaseEntity implements Serializable {

    @Column(name = "TYPE", nullable = false)
    private String mimeType;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "PHOTO", nullable = false)
    @Lob
    private byte[] fileBytes;

    @ManyToOne()
    @JoinColumn(name = "APARTMENT_ID")
    @JsonBackReference
    private Apartment apartment;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
