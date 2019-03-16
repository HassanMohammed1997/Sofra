
package com.internship.ipda3.semicolon.sofra.model.general.categories;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.internship.ipda3.semicolon.sofra.model.general.restaurant.CategoryDatum;


public class Catagory {

    @SerializedName("data")
    private List<CategoryDatum> mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public List<CategoryDatum> getData() {
        return mData;
    }

    public void setData(List<CategoryDatum> data) {
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
        return "Catagory{" +
                "mData=" + mData +
                ", mMsg='" + mMsg + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
