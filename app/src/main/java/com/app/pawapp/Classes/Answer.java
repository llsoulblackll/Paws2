package com.app.pawapp.Classes;

import java.io.Serializable;

public class Answer implements Serializable {

    private int ImgOwner;
    private String imgUrl;
    private String NameOwner;
    private String LastNameOwner;
    private String Answer;
    private String Time;

    public Answer() {
    }

    public Answer(int imgOwner, String nameOwner, String lastNameOwner, String answer, String time) {
        ImgOwner = imgOwner;
        NameOwner = nameOwner;
        LastNameOwner = lastNameOwner;
        Answer = answer;
        Time = time;
    }

    public int getImgOwner() {
        return ImgOwner;
    }

    public void setImgOwner(int imgOwner) {
        ImgOwner = imgOwner;
    }

    public String getNameOwner() {
        return NameOwner;
    }

    public void setNameOwner(String nameOwner) {
        NameOwner = nameOwner;
    }

    public String getLastNameOwner() {
        return LastNameOwner;
    }

    public void setLastNameOwner(String lastNameOwner) {
        LastNameOwner = lastNameOwner;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
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
