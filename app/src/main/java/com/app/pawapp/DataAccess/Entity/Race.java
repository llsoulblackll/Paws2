package com.app.pawapp.DataAccess.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Race implements Parcelable {

    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;
    @SerializedName("SpecieId") private int specieId;

    public Race() {
    }

    public Race(int id, String name, int specieId) {
        this.id = id;
        this.name = name;
        this.specieId = specieId;
    }

    protected Race(Parcel in) {
        id = in.readInt();
        name = in.readString();
        specieId = in.readInt();
    }

    public static final Creator<Race> CREATOR = new Creator<Race>() {
        @Override
        public Race createFromParcel(Parcel in) {
            return new Race(in);
        }

        @Override
        public Race[] newArray(int size) {
            return new Race[size];
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

    public int getSpecieId() {
        return specieId;
    }

    public void setSpecieId(int specieId) {
        this.specieId = specieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(specieId);
    }
}
