package com.example.stylessmiles.model;

public class ServicesModel {
    String Name;
    String Price;
    String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public ServicesModel(String name, String price, String image) {
        Name = name;
        Price = price;
        Image = image;
    }

    public ServicesModel(String name, String price) {
        Name = name;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
