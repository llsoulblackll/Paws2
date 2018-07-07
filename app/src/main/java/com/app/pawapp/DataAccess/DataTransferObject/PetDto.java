package com.app.pawapp.DataAccess.DataTransferObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Race;
import com.app.pawapp.DataAccess.Entity.Specie;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PetDto implements Parcelable {
    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;
    @SerializedName("Age") private String age;
    @SerializedName("Description") private String description;
    @SerializedName("PawPicture") private String picture;
    @SerializedName("PublishDate") private Date publishDate;
    @SerializedName("State") private boolean state;
    @SerializedName("OtherRace") private String otherRace;
    @SerializedName("Specie") private Specie specie;
    @SerializedName("Race") private Race race;
    @SerializedName("Owner") private Owner owner;

    public PetDto() {
    }

    public PetDto(int id, String name, String age, String description, String picture, Date publishDate, boolean state, String otherRace, Specie specie, Race race, Owner owner) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.description = description;
        this.picture = picture;
        this.publishDate = publishDate;
        this.state = state;
        this.otherRace = otherRace;
        this.specie = specie;
        this.race = race;
        this.owner = owner;
    }

    private PetDto(Parcel in) {
        id = in.readInt();
        name = in.readString();
        age = in.readString();
        description = in.readString();
        picture = in.readString();
        state = in.readByte() != 0;
        otherRace = in.readString();
        specie = in.readParcelable(Specie.class.getClassLoader());
        race = in.readParcelable(Race.class.getClassLoader());
        owner = in.readParcelable(Owner.class.getClassLoader());
    }

    public static final Creator<PetDto> CREATOR = new Creator<PetDto>() {
        @Override
        public PetDto createFromParcel(Parcel in) {
            return new PetDto(in);
        }

        @Override
        public PetDto[] newArray(int size) {
            return new PetDto[size];
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

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
        parcel.writeByte((byte) (state ? 1 : 0));
        parcel.writeString(otherRace);
        parcel.writeParcelable(specie, i);
        parcel.writeParcelable(race, i);
        parcel.writeParcelable(owner, i);
    }
}
