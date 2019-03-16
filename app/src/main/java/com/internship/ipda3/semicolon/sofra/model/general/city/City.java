
package com.internship.ipda3.semicolon.sofra.model.general.city;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class City {

    @SerializedName("data")
    private CityData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public CityData getData() {
        return mData;
    }

    public void setData(CityData data) {
        mData = data;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    @Override
    public String toString() {
        return "City{" +
                "mData=" + mData +
                ", mMsg='" + mMsg + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
