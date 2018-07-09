package com.app.pawapp.DataAccess.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PetAdopter implements Parcelable {
    @SerializedName("PetId") private int petId;
    @SerializedName("AdopterId") private int adopterId;
    @SerializedName("RequestDate") private Date requestDate;
    @SerializedName("ResponseDate") private Date responseDate;
    @SerializedName("State") private boolean state;

    public PetAdopter() {
    }

    public PetAdopter(int petId, int adopterId, Date requestDate, Date responseDate, boolean state) {
        this.petId = petId;
        this.adopterId = adopterId;
        this.requestDate = requestDate;
        this.responseDate = responseDate;
        this.state = state;
    }

    private PetAdopter(Parcel in) {
        petId = in.readInt();
        adopterId = in.readInt();
        state = in.readByte() != 0;
    }

    public static final Creator<PetAdopter> CREATOR = new Creator<PetAdopter>() {
        @Override
        public PetAdopter createFromParcel(Parcel in) {
            return new PetAdopter(in);
        }

        @Override
        public PetAdopter[] newArray(int size) {
            return new PetAdopter[size];
        }
    };

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(int adopterId) {
        this.adopterId = adopterId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(petId);
        parcel.writeInt(adopterId);
        parcel.writeByte((byte) (state ? 1 : 0));
    }
}
