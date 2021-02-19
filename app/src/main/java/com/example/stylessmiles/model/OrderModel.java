package com.example.stylessmiles.model;

import java.util.Date;
import java.util.Date;

public class OrderModel {
    CartModel order = new CartModel();
    String email = "";
    String orderDate;
    String AppoimentDate;
    String orderStatus;
    String orderNo = "";

    public OrderModel(){
    }

    public OrderModel(CartModel order, String email, String orderDate, String appoimentDate, String orderStatus) {
        this.order = order;
        this.email = email;
        this.orderDate = orderDate;
        AppoimentDate = appoimentDate;
        this.orderStatus = orderStatus;
    }

    public CartModel getOrder() {
        return order;
    }

    public void setOrder(CartModel order) {
        this.order = order;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAppoimentDate() {
        return AppoimentDate;
    }

    public void setAppoimentDate(String appoimentDate) {
        AppoimentDate = appoimentDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
