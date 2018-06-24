package com.app.pawapp.Classes;

import java.io.Serializable;

public class Message implements Serializable {

    public int Img;
    public String Name;
    public String LastName;
    public String Email;
    public String Phone;
    public String Time;

    public Message(int img, String name, String lastName, String email, String phone, String time) {
        this.Img = img;
        this.Name = name;
        this.LastName = lastName;
        this.Email = email;
        this.Phone = phone;
        this.Time = time;
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

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

}