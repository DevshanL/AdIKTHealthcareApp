package com.tech.adikthealthcare.Models;

public class doctordetails {

    String id;
    String name;
    String special;
    String time;
    String image;

    public doctordetails() {
    }

    public doctordetails(String id, String name, String special, String time, String image) {
        this.id = id;
        this.name = name;
        this.special = special;
        this.time = time;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
