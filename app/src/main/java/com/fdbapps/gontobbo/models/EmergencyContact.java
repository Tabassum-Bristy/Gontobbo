package com.fdbapps.gontobbo.models;


public class EmergencyContact {

    private int id;
    private String name;
    private String phoneNumber;
    private String district;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    // empty constructor
    public EmergencyContact() { }

    // constructor with params
    public EmergencyContact(int id, String name, String phoneNumber, String district) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.district = district;
    }
}
