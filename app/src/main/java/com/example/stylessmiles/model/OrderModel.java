package com.example.stylessmiles.model;

import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.Activity.OrderStatus;

public class OrderModel {
    CartModel order = new CartModel();
    usermodel user = new usermodel();
    String orderDate;
    String AppoimentDate;
    public String orderStatus;
    String orderNo = "";
    String orderType = "";

    public OrderModel() {
    }

    public OrderModel(CartModel order, com.example.stylessmiles.model.usermodel usermodel, String orderDate, String appoimentDate, String orderStatus,String orderType) {
        this.order = order;
        this.user = usermodel;
        this.orderDate = orderDate;
        AppoimentDate = appoimentDate;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
    }


    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
        if(this.orderStatus.equals(centralStore.orderplaced)){
            this.orderStatus = centralStore.confirmOrder;
        return this.orderStatus;
        }
        if(this.orderStatus.equals(centralStore.confirmOrder)){
            this.orderStatus = centralStore.completeOrder;
            return this.orderStatus;
        }
        return this.orderStatus;
    }

    public String cancelOrder() {
        this.orderStatus = centralStore.cancelOrder;
        return this.orderStatus;
    }
}
