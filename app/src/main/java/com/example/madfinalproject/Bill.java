package com.example.madfinalproject;

public class Bill {

    int Total_Products,Total_Amount;

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    String TimeStamp;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    String UserId;

    public Bill(){

    }

    public int getTotal_Products() {
        return Total_Products;
    }

    public void setTotal_Products(int total_Products) {
        Total_Products = total_Products;
    }

    public int getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(int total_Amount) {
        Total_Amount = total_Amount;
    }

    public Bill(int tp, int ta,String time,String id){
        Total_Amount = ta;
        Total_Products = tp;
        TimeStamp = time;
        UserId = id;
    }
}
