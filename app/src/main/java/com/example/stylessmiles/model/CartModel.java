package com.example.stylessmiles.model;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    int totalPrice = 0;
    String saloonname = "";
    List<ProductModel> products = new ArrayList<ProductModel>();
    List<ServicesModel> services = new ArrayList<ServicesModel>();
    List<Integer> productQuantity = new ArrayList<Integer>();

    public void addProduct(ProductModel product) {
        this.products.add(product);
        this.productQuantity.add(1);
        Calcost();
    }

    public void addService(ServicesModel servicesModel) {
        this.services.add(servicesModel);
        Calcost();
    }

    public void removeAllService() {
        this.services.clear();
        Calcost();
    }

    public void removeService(int index) {
        this.services.remove(index);
        Calcost();
    }

    public void removeProduct(int index) {
        this.products.remove(index);
        Calcost();
    }

    public void updateQunatity(int index, int qunatity) {
        this.productQuantity.set(index, qunatity);
        Calcost();
    }

    public void Calcost() {
        totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            totalPrice = totalPrice + Integer.parseInt(products.get(i).price) * productQuantity.get(i);
        }
        for (ServicesModel servicesModel : services) {
            totalPrice = totalPrice + Integer.parseInt(servicesModel.getPrice());
        }
    }

    public boolean setSaloon(String saloonname) {
        if(this.saloonname.isEmpty()){
            this.saloonname = saloonname;
            return true;
        }
        else if(this.saloonname.equals(saloonname)){
            return true;
        }
        else{
            return false;
        }
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSaloonname() {
        return saloonname;
    }

    public void setSaloonname(String saloonname) {
        this.saloonname = saloonname;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public List<ServicesModel> getServices() {
        return services;
    }

    public void setServices(List<ServicesModel> services) {
        this.services = services;
    }

    public List<Integer> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(List<Integer> productQuantity) {
        this.productQuantity = productQuantity;
    }
}
