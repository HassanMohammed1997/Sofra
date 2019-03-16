
package com.internship.ipda3.semicolon.sofra.model.general.region;

import com.google.gson.annotations.SerializedName;
import com.internship.ipda3.semicolon.sofra.model.general.city.CityDatum;

@SuppressWarnings("unused")
public class RegionDatum {

    @SerializedName("city")
    private CityDatum mCity;
    @SerializedName("city_id")
    private String mCityId;
    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("updated_at")
    private Object mUpdatedAt;

    public CityDatum getCity() {
        return mCity;
    }

    public void setCity(CityDatum city) {
        mCity = city;
    }

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

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

}
