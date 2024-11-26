package com.example.devops2.model;

import java.util.ArrayList;

public class Item {

    private String dateOrder, orderCode;
    private ArrayList<String> imageUrls;


    public Item() {


    }

    public Item(String dateOrder, String orderCode, ArrayList<String> imageUrls) {
        this.dateOrder = dateOrder;
        this.orderCode = orderCode;
        this.imageUrls = imageUrls;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
