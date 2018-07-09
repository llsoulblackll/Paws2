package com.app.pawapp.DataAccess.DataTransferObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Pet;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PetAdopterDto implements Parcelable {
    @SerializedName("Pet") private PetDto pet;
    @SerializedName("Adopter") private Owner adopter;
    @SerializedName("RequestDate") private Date requestDate;
    @SerializedName("ResponseDate") private Date responseDate;
    @SerializedName("State") private boolean state;

    public PetAdopterDto() {
    }

    public PetAdopterDto(PetDto petDto, Owner adopter, Date requestDate, Date responseDate, boolean state) {
        this.pet = petDto;
        this.adopter = adopter;
        this.requestDate = requestDate;
        this.responseDate = responseDate;
        this.state = state;
    }

    private PetAdopterDto(Parcel in) {
        pet = in.readParcelable(PetDto.class.getClassLoader());
        adopter = in.readParcelable(Owner.class.getClassLoader());
        state = in.readByte() != 0;
    }

    public static final Creator<PetAdopterDto> CREATOR = new Creator<PetAdopterDto>() {
        @Override
        public PetAdopterDto createFromParcel(Parcel in) {
            return new PetAdopterDto(in);
        }

        @Override
        public PetAdopterDto[] newArray(int size) {
            return new PetAdopterDto[size];
        }
    };

    public PetDto getPet() {
        return pet;
    }

    public void setPet(PetDto pet) {
        this.pet = pet;
    }

    public Owner getAdopter() {
        return adopter;
    }

    public void setAdopter(Owner adopter) {
        this.adopter = adopter;
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
        parcel.writeParcelable(pet, i);
        parcel.writeParcelable(adopter, i);
        parcel.writeByte((byte) (state ? 1 : 0));
    }
}
