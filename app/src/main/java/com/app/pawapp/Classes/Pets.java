package com.app.pawapp.Classes;

import java.io.Serializable;

public class Pets implements Serializable {

    private int Img;
    private String Name;
    private String Age;
    private String Description;
    private String Type;
    private String Race;

    public Pets(int img, String name, String age, String description, String type, String race) {
        this.Img = img;
        this.Name = name;
        this.Age = age;
        this.Description = description;
        this.Type = type;
        this.Race = race;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getRace() {
        return Race;
    }

    public void setRace(String race) {
        Race = race;
    }

}