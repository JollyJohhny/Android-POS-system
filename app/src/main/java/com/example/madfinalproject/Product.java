package com.example.madfinalproject;

import android.widget.ImageView;

public class Product {
    String Name;
    String Quantity;
    String Price;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    String UserId;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    String Image;

    public Product(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Product(String price, String quantity, String name,String image,String userid){
        Price = price;
        Quantity = quantity;
        Name = name;
        Image = image;
        UserId = userid;
    }


}
