package com.example.groceryapp;

public class ProductModel {
    private String title;
    private String imgid;
    private String des;
    private double price;
    private int quantity;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public ProductModel(){}
    public ProductModel(String title, String imgid, String des, double price,int quantity) {
        this.title = title;
        this.imgid = imgid;
        this.des   =  des;
        this.price =  price;
        this.quantity=quantity;
    }
}
