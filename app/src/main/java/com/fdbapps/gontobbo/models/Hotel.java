package com.fdbapps.gontobbo.models;



public class Hotel extends Place {

    private int id;
    private String description;
    private byte[] image;
    private String roomFairs;
    private String phoneNumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public byte[] getImageName() {
        return image;
    }

    public void setImageName(byte[] image) {
        this.image = image;
    }

    public String getRoomFairs() {
        return roomFairs;
    }

    public void setRoomFairs(String roomFairs) {
        this.roomFairs = roomFairs;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Hotel() { }

    public Hotel(int id, String title, String description, String address, byte[] image,
                 double latitude, double longitude, String roomFairs, String phoneNumber) {

        super(title, address, latitude, longitude);

        this.id = id;
        this.description = description;
        this.image = image;
        this.roomFairs = roomFairs;
        this.phoneNumber = phoneNumber;
    }
}
