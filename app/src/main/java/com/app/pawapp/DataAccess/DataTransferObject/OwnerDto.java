package com.app.pawapp.DataAccess.DataTransferObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.pawapp.DataAccess.Entity.District;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class OwnerDto implements Parcelable{
    @SerializedName("Id") private int id;
    @SerializedName("Username") private String username;
    @SerializedName("Password") private String password;
    @SerializedName("Name") private String name;
    @SerializedName("LastName") private String lastName;
    @SerializedName("BirthDate") private Date birthDate;
    private String DNI;
    @SerializedName("EMail") private String eMail;
    @SerializedName("Address") private String address;
    @SerializedName("PhoneNumber") private String phoneNumber;
    @SerializedName("ProfilePicture") private String profilePicture;
    @SerializedName("District") private District district;
    @SerializedName("RegisteredAmount") private int registeredAmount;
    @SerializedName("AdoptedAmount") private int adoptedAmount;

    public OwnerDto() {
    }

    public OwnerDto(int id, String username, String password, String name, String lastName, Date birthDate, String DNI, String eMail, String address, String phoneNumber, String profilePicture, District district, int registeredAmount, int adoptedAmount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.DNI = DNI;
        this.eMail = eMail;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.district = district;
        this.registeredAmount = registeredAmount;
        this.adoptedAmount = adoptedAmount;
    }

    private OwnerDto(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();
        name = in.readString();
        lastName = in.readString();
        birthDate = new Date(in.readLong());
        DNI = in.readString();
        eMail = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        profilePicture = in.readString();
        district = in.readParcelable(District.class.getClassLoader());
        registeredAmount = in.readInt();
        adoptedAmount = in.readInt();
    }

    public static final Parcelable.Creator<OwnerDto> CREATOR = new Parcelable.Creator<OwnerDto>() {
        @Override
        public OwnerDto createFromParcel(Parcel in) {
            return new OwnerDto(in);
        }

        @Override
        public OwnerDto[] newArray(int size) {
            return new OwnerDto[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public int getRegisteredAmount() {
        return registeredAmount;
    }

    public void setRegisteredAmount(int registeredAmount) {
        this.registeredAmount = registeredAmount;
    }

    public int getAdoptedAmount() {
        return adoptedAmount;
    }

    public void setAdoptedAmount(int adoptedAmount) {
        this.adoptedAmount = adoptedAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeString(lastName);
        parcel.writeLong(birthDate.getTime());
        parcel.writeString(DNI);
        parcel.writeString(eMail);
        parcel.writeString(address);
        parcel.writeString(phoneNumber);
        parcel.writeString(profilePicture);
        parcel.writeParcelable(district, i);
        parcel.writeInt(registeredAmount);
        parcel.writeInt(adoptedAmount);
    }
}
