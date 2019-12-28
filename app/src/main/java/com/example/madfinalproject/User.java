package com.example.madfinalproject;

public class User {
    String Name,ImageUri,Email;
    public User(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public User(String name, String email, String img){
        Name = name;
        ImageUri = img;
        Email = email;
    }



}
