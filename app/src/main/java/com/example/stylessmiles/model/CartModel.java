package com.example.stylessmiles.model;

import com.example.stylessmiles.centralStore;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    int totalPrice = 0;
    String saloonname = "";



    List<ProductModel> products = new ArrayList<ProductModel>();
    List<ServicesModel> services = new ArrayList<ServicesModel>();
    List<Integer> productQuantity = new ArrayList<Integer>();

    public String addProduct(ProductModel product) {
//        return String.valueOf(this.products.indexOf(product));
        for (ProductModel p : this.products) {
            if (p.name == product.name) {
                return "Product is already in cart";
            }
        }

        this.products.add(product);
        this.productQuantity.add(1);
        Calcost();
        return "Product added";

    }

    public String addService(ServicesModel servicesModel) {
        for (ServicesModel p : this.services) {
            if (p.Name == servicesModel.Name) {
                return "Service is already in cart";
            }
        }
        this.services.add(servicesModel);
        Calcost();
        return "Service added";
    }

    public void removeAllService() {
        this.services.clear();
        Calcost();
    }

    public void removeService(int index) {
        this.services.remove(index);
        if(this.services.size() == 0){
            this.saloonname = "";
        }
        Calcost();
    }

    public void removeProduct(int index) {
        this.products.remove(index);
        this.productQuantity.remove(index);
        Calcost();
    }

    public int updateQunatity(int index, int qunatity) {
        int newQunatity = this.productQuantity.get(index)+ qunatity;
        this.productQuantity.set(index, newQunatity);
        Calcost();
        return newQunatity;
    }

    public void Calcost() {
        totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            totalPrice = totalPrice + Integer.parseInt(products.get(i).price) * productQuantity.get(i);
        }
        for (ServicesModel servicesModel : services) {
            totalPrice = totalPrice + Integer.parseInt(servicesModel.getPrice());
        }
        centralStore.getInstance().synccart();
    }

    public boolean setSaloon(String saloonname) {
        if (this.saloonname.isEmpty()) {
            this.saloonname = saloonname;
            return true;
        } else if (this.saloonname.equals(saloonname)) {
            return true;
        } else {
            return false;
        }
    }

    public int getProductQuantity(int id) {
        return this.productQuantity.get(id);
    }

    public void addProductQuantity(int q){
        this.productQuantity.add(q);
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
//        this.products = products;
        for(ProductModel productModel : products)
        {
            this.products.add(productModel);
        }
    }

    public List<ServicesModel> getServices() {
        return services;
    }

    public void setServices(List<ServicesModel> services) {
        for(ServicesModel servicesModel : services)
        {
            this.services.add(servicesModel);
        }
//        this.services = services;
    }

    public List<Integer> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(List<Integer> productQuantity) {
        for (Integer i : productQuantity){
            this.productQuantity.add(i);
        }
//        this.productQuantity = productQuantity;
    }


}
