
package com.internship.ipda3.semicolon.sofra.model.restaurent.items.newItem;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NewItem {

    @SerializedName("data")
    private NewItemData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public NewItemData getData() {
        return mData;
    }

    public void setData(NewItemData data) {
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
        return "NewItem{" +
                "mData=" + mData +
                ", mMsg='" + mMsg + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
