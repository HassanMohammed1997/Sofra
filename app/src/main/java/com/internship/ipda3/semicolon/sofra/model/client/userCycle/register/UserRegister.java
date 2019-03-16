
package com.internship.ipda3.semicolon.sofra.model.client.userCycle.register;

import com.google.gson.annotations.SerializedName;
import com.internship.ipda3.semicolon.sofra.model.client.userCycle.login.UserData;

@SuppressWarnings("unused")
public class UserRegister {

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
        return "UserRegister{" +
                "mData=" + mData +
                ", mMsg='" + mMsg + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
