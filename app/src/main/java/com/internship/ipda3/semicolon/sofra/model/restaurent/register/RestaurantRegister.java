
package com.internship.ipda3.semicolon.sofra.model.restaurent.register;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RestaurantRegister {

    @SerializedName("data")
    private RestaurantRegisterData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public RestaurantRegisterData getData() {
        return mData;
    }

    public void setData(RestaurantRegisterData data) {
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
        return "RestaurantRegister{" +
                "mData=" + mData +
                ", mMsg='" + mMsg + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
