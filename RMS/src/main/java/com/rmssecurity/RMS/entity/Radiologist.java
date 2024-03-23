package com.rmssecurity.RMS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Radiologist {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String username;
    private String type;
    private String contactNumber;
    private boolean dicomAttached=false;

    public Radiologist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isDicomAttached() {
        return dicomAttached;
    }
    public void setDicomAttached(boolean dicomAttached) {
        this.dicomAttached = dicomAttached;
    }
}
