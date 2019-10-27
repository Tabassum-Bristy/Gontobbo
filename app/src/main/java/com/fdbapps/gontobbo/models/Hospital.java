package com.fdbapps.gontobbo.models;


public class Hospital extends Place {

    private int id;
    private String phoneNumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Hospital() { }

    public Hospital(int id, String title, String address,
                 double latitude, double longitude, String phoneNumber) {

        super(title, address, latitude, longitude);

        this.id = id;
        this.phoneNumber = phoneNumber;
    }
}
