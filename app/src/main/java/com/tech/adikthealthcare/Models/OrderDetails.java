package com.tech.adikthealthcare.Models;

public class OrderDetails {

    String Id;
    String Name;
    String Price;
    String image;

    public OrderDetails() {
    }

    public OrderDetails(String id, String name, String price, String image) {
        Id = id;
        Name = name;
        Price = price;
        this.image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
