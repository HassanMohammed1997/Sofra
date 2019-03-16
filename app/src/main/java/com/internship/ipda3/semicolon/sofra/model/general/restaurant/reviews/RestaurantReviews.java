
package com.internship.ipda3.semicolon.sofra.model.general.restaurant.reviews;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class RestaurantReviews {

    @SerializedName("data")
    private ReviewsData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public ReviewsData getData() {
        return mData;
    }

    public void setData(ReviewsData data) {
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
