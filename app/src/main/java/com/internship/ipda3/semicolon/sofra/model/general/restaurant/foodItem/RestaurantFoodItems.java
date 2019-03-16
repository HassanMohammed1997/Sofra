
package com.internship.ipda3.semicolon.sofra.model.general.restaurant.foodItem;

import com.google.gson.annotations.SerializedName;


public class RestaurantFoodItems {

    @SerializedName("data")
    private FoodItemData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public FoodItemData getData() {
        return mData;
    }

    public void setData(FoodItemData data) {
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
