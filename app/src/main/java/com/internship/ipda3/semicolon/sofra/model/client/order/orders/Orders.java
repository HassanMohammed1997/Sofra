
package com.internship.ipda3.semicolon.sofra.model.client.order.orders;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Orders {

    @SerializedName("data")
    private OrderData mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public OrderData getData() {
        return mData;
    }

    public void setData(OrderData data) {
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
        return "Orders{" +
                "mData=" + mData +
                ", mMsg='" + mMsg + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
