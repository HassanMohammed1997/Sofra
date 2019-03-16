
package com.internship.ipda3.semicolon.sofra.model.client.userCycle.login;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserLogin {

    @SerializedName("data")
    private UserData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public UserData getData() {
        return mData;
    }

    public void setData(UserData data) {
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
        return "UserLogin{" +
                "mData=" + mData +
                ", mMsg='" + mMsg + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
