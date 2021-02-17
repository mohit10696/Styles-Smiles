package com.example.stylessmiles.model;

public class SaloonModel {
    private String name;
    private String address;
    private String city;
    private String imgurl;

    public SaloonModel() {

    }


    public SaloonModel(String name, String address, String city, String imgurl) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getImgurl() {
        return imgurl;
    }
}
