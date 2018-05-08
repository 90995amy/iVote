package com.kirandeep.ivote.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abc on 11-04-2018.
 */

public class EntryAadharData implements Serializable {
    String uid;
    String name;
    String gender;
    String yearOfBirth;
    String careOf;
    String villageTehsil;
    String postOffice;
    String district;
    String state;
    String postCode;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getCareOf() {
        return careOf;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public String getVillageTehsil() {
        return villageTehsil;
    }

    public void setVillageTehsil(String villageTehsil) {
        this.villageTehsil = villageTehsil;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
