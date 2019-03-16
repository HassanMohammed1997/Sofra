
package com.internship.ipda3.semicolon.sofra.model.client.userCycle.login;

import com.google.gson.annotations.SerializedName;
import com.internship.ipda3.semicolon.sofra.model.general.region.RegionDatum;

@SuppressWarnings("unused")
public class UserDatum {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("region")
    private RegionDatum mRegion;
    @SerializedName("region_id")
    private String mRegionId;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public RegionDatum getRegion() {
        return mRegion;
    }

    public void setRegion(RegionDatum region) {
        mRegion = region;
    }

    public String getRegionId() {
        return mRegionId;
    }

    public void setRegionId(String regionId) {
        mRegionId = regionId;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserDatum{" +
                "mAddress='" + mAddress + '\'' +
                ", mCreatedAt='" + mCreatedAt + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mRegion=" + mRegion +
                ", mRegionId='" + mRegionId + '\'' +
                ", mUpdatedAt='" + mUpdatedAt + '\'' +
                '}';
    }
}
