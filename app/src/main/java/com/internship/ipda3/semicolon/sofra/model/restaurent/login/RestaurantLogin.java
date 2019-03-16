
package com.internship.ipda3.semicolon.sofra.model.restaurent.login;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RestaurantLogin {

    @SerializedName("data")
    private RestaurantData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public RestaurantData getData() {
        return mData;
    }

    public void setData(RestaurantData data) {
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
        return "RestaurantLogin{" +
                "mData=" + mData +
                ", mMsg='" + mMsg + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
