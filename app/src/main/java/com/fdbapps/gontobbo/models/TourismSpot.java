                    package com.fdbapps.gontobbo.models;


public class TourismSpot extends Place {

    private int id;
    private String description;
    private byte[] image;
    private String district;


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

    public void setImageName(byte[] imageName) {
        this.image = imageName;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public TourismSpot(int id, String title, String description, String address, byte[] image,
                       double latitude, double longitude, String district) {

        super(title, address, latitude, longitude);

        this.id = id;
        this.description = description;
        this.image = image;

        this.district = district;
    }

}
