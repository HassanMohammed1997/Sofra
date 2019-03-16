
package com.internship.ipda3.semicolon.sofra.model.general.region;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Region {

    @SerializedName("data")
    private RegionData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private int mStatus;

    public RegionData getData() {
        return mData;
    }

    public void setData(RegionData data) {
        mData = data;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

}
