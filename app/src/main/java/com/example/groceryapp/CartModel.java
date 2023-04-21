package com.example.groceryapp;

import java.io.Serializable;

public class CartModel implements Serializable {
    private String title;
    private String imgid;
    private int quantity;
    private double price;
    private double quan_per_price;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public void setPrice(double price) {
        this.price = price;
    }
    public void setQuan_per_price(double quan_per_price) {
        this.quan_per_price = quan_per_price;
    }

    public double getquanPrice() {
        return quan_per_price;
    }


    public CartModel(){}
    public CartModel(String title, String imgid, int quantity, double price, double quan_per_price) {
        this.title = title;
        this.imgid = imgid;
        this.quantity   =  quantity;
        this.price =  price;
        this.quan_per_price= quan_per_price;
    }
    public CartModel(String title,String imgid, int quantity, double price) {
        this.title = title;
        this.imgid = imgid;
        this.quantity   =  quantity;
        this.price =  price;

    }
    public CartModel(String title, int quantity, double price, double quan_per_price) {
        this.title = title;
        this.quantity   =  quantity;
        this.price =  price;
        this.quan_per_price= quan_per_price;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CartModel other = (CartModel) obj;
        return title.equals(other.title);
    }

}
