package com.internship.ipda3.semicolon.sofra.model.restaurent.register;

import com.google.gson.annotations.SerializedName;
import com.internship.ipda3.semicolon.sofra.model.general.region.RegionDatum;

public class RestaurantRegisterDatum {

    @SerializedName("availability")
    private String mAvailability;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("delivery_cost")
    private String mDeliveryCost;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;
    @SerializedName("minimum_charger")
    private String mMinimumCharger;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("photo_url")
    private String mPhotoUrl;
    @SerializedName("rate")
    private Long mRate;
    @SerializedName("region")
    private RegionDatum mRegion;
    @SerializedName("region_id")
    private String mRegionId;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("whatsapp")
    private String mWhatsapp;

    public String getAvailability() {
        return mAvailability;
    }

    public void setAvailability(String mAvailability) {
        this.mAvailability = mAvailability;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }


    public String getDeliveryCost() {
        return mDeliveryCost;
    }

    public void setDeliveryCost(String mDeliveryCost) {
        this.mDeliveryCost = mDeliveryCost;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public String getMinimumCharger() {
        return mMinimumCharger;
    }

    public void setMinimumCharger(String mMinimumCharger) {
        this.mMinimumCharger = mMinimumCharger;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public Long getRate() {
        return mRate;
    }

    public void setRate(Long mRate) {
        this.mRate = mRate;
    }

    public RegionDatum getRegion() {
        return mRegion;
    }

    public void setRegion(RegionDatum mRegion) {
        this.mRegion = mRegion;
    }

    public String getRegionId() {
        return mRegionId;
    }

    public void setRegionId(String mRegionId) {
        this.mRegionId = mRegionId;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String mUpdatedAt) {
        this.mUpdatedAt = mUpdatedAt;
    }

    public String getWhatsapp() {
        return mWhatsapp;
    }

    public void setWhatsapp(String mWhatsapp) {
        this.mWhatsapp = mWhatsapp;
    }

    @Override
    public String toString() {
        return "RestaurantRegisterDatum{" +
                "mAvailability='" + mAvailability + '\'' +
                ", mCreatedAt='" + mCreatedAt + '\'' +
                ", mDeliveryCost='" + mDeliveryCost + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mId=" + mId +
                ", mMinimumCharger='" + mMinimumCharger + '\'' +
                ", mName='" + mName + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mPhoto='" + mPhoto + '\'' +
                ", mPhotoUrl='" + mPhotoUrl + '\'' +
                ", mRate=" + mRate +
                ", mRegion=" + mRegion +
                ", mRegionId='" + mRegionId + '\'' +
                ", mUpdatedAt='" + mUpdatedAt + '\'' +
                ", mWhatsapp='" + mWhatsapp + '\'' +
                '}';
    }
}
