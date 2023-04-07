package com.example.groceryapp;

public class ProductModel {
    private String title;
    private int imgid;
    private String des;
    private double price;


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

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public ProductModel(){}
    public ProductModel(String title, int imgid, String des, double price) {
        this.title = title;
        this.imgid = imgid;
        this.des   =  des;
        this.price =  price;
    }
}
