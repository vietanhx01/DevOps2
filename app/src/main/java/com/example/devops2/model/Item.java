package com.example.devops2.model;

import java.util.ArrayList;

public class Item {

    private String idItem;
    private String dateOrder, orderCode;
    private ArrayList<String> imageUrls;


    public Item() {


    }

    public Item(String idItem, String dateOrder, String orderCode, ArrayList<String> imageUrls) {
        this.idItem = idItem;
        this.dateOrder = dateOrder;
        this.orderCode = orderCode;
        this.imageUrls = imageUrls;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
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
