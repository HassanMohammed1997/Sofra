
package com.internship.ipda3.semicolon.sofra.model.general.restaurant;

import com.google.gson.annotations.SerializedName;

public class Restaurant {

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

}
