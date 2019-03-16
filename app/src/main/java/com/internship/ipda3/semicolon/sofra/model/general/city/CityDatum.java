
package com.internship.ipda3.semicolon.sofra.model.general.city;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CityDatum {

    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("updated_at")
    private Object mUpdatedAt;

    public Object getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Object createdAt) {
        mCreatedAt = createdAt;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CityDatum{" +
                "mCreatedAt=" + mCreatedAt +
                ", mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mUpdatedAt=" + mUpdatedAt +
                '}';
    }
}
