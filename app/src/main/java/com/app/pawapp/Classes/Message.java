package com.app.pawapp.Classes;

import java.io.Serializable;

public class Message implements Serializable {

    private int Img;
    private String imgUrl;
    private String Name;
    private String LastName;
    private String Message;
    private String Time;

    public Message() {
    }

    public Message(int img, String name, String lastName, String message, String time) {
        Img = img;
        Name = name;
        LastName = lastName;
        Message = message;
        Time = time;
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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}