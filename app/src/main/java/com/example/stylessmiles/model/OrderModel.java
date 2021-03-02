package com.example.stylessmiles.model;


import com.example.stylessmiles.Activity.OrderStatus;

public class OrderModel {
    CartModel order = new CartModel();
    usermodel user = new usermodel();
    String orderDate;
    String AppoimentDate;
    String orderStatus;
    String orderNo = "";

    public OrderModel() {
    }

    public OrderModel(CartModel order, com.example.stylessmiles.model.usermodel usermodel, String orderDate, String appoimentDate, String orderStatus) {
        this.order = order;
        this.user = usermodel;
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

    public com.example.stylessmiles.model.usermodel getUsermodel() {
        return user;
    }

    public void setUsermodel(com.example.stylessmiles.model.usermodel usermodel) {
        this.user = usermodel;
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

    public String updateStatus() {
        if(this.orderStatus.equals("Order Placed")){
            this.orderStatus = "Order Accepted";
        return this.orderStatus;
        }
        if(this.orderStatus.equals("Order Accepted")){
            this.orderStatus = "Order Completed";
            return this.orderStatus;
        }
        return this.orderStatus;
    }

    public String cancelOrder() {
        this.orderStatus = "Order Cancelled";
        return this.orderStatus;
    }
}
