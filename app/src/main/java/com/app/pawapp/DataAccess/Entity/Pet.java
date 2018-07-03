package com.app.pawapp.DataAccess.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Pet implements Parcelable {

    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;
    @SerializedName("Age") private String age;
    @SerializedName("Description") private String description;
    @SerializedName("Picture") private String picture;
    @SerializedName("PublishDate") private Date publishDate;
    @SerializedName("State") private boolean state;
    @SerializedName("OtherRace") private String otherRace;
    @SerializedName("SpecieId") private int specieId;
    @SerializedName("RaceId") private int raceId;
    @SerializedName("OwnerId") private int ownerId;

    @SerializedName("ImageBase64") private String imageBase64;
    @SerializedName("ImageExtension") private String imageExtension;

    public Pet() {
    }

    public Pet(int id, String name, String age, String description, String picture, Date publishDate, boolean state, String otherRace, int specieId, int raceId, int ownerId, String imageBase64, String imageExtension) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.description = description;
        this.picture = picture;
        this.publishDate = publishDate;
        this.state = state;
        this.otherRace = otherRace;
        this.specieId = specieId;
        this.raceId = raceId;
        this.ownerId = ownerId;
        this.imageBase64 = imageBase64;
        this.imageExtension = imageExtension;
    }

    private Pet(Parcel in) {
        id = in.readInt();
        name = in.readString();
        age = in.readString();
        description = in.readString();
        picture = in.readString();

        long dateHolder = in.readLong();
        publishDate =  dateHolder != 0L ? new Date(dateHolder) : null;

        state = in.readByte() == 1;
        otherRace = in.readString();
        specieId = in.readInt();
        raceId = in.readInt();
        ownerId = in.readInt();
        imageBase64 = in.readString();
        imageExtension = in.readString();
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getOtherRace() {
        return otherRace;
    }

    public void setOtherRace(String otherRace) {
        this.otherRace = otherRace;
    }

    public int getSpecieId() {
        return specieId;
    }

    public void setSpecieId(int specieId) {
        this.specieId = specieId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeString(description);
        parcel.writeString(picture);
        parcel.writeLong(publishDate != null ? publishDate.getTime() : 0L);
        parcel.writeByte((byte)(state ? 1 : 0));
        parcel.writeString(otherRace);
        parcel.writeInt(specieId);
        parcel.writeInt(raceId);
        parcel.writeInt(ownerId);
        parcel.writeString(imageBase64);
        parcel.writeString(imageExtension);
    }
}
