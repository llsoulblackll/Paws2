package com.app.pawapp.DataAccess.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Specie implements Parcelable {

    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;

    public Specie() {
    }

    public Specie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private Specie(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Specie> CREATOR = new Creator<Specie>() {
        @Override
        public Specie createFromParcel(Parcel in) {
            return new Specie(in);
        }

        @Override
        public Specie[] newArray(int size) {
            return new Specie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
