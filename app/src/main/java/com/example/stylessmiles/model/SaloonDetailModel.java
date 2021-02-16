package com.example.stylessmiles.model;

import java.util.ArrayList;
import java.util.List;

public class SaloonDetailModel {
    String Name;
    String Address;
    String City;
    String Image;
    List<ServicesModel> servicesModels = new ArrayList<ServicesModel>();

    public SaloonDetailModel() {
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public List<ServicesModel> getServicesModels() {
        return servicesModels;
    }

    public void setServicesModels(List<ServicesModel> servicesModels) {
        this.servicesModels = servicesModels;
    }
}
