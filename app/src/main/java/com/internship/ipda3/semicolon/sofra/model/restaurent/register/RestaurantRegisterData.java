
package com.internship.ipda3.semicolon.sofra.model.restaurent.register;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RestaurantRegisterData {

    @SerializedName("api_token")
    private String mApiToken;
    @SerializedName("data")
    private RestaurantRegisterDatum datum;

    public String getApiToken() {
        return mApiToken;
    }

    public void setApiToken(String mApiToken) {
        this.mApiToken = mApiToken;
    }

    public RestaurantRegisterDatum getDatum() {
        return datum;
    }

    public void setDatum(RestaurantRegisterDatum datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        return "RestaurantRegisterData{" +
                "mApiToken='" + mApiToken + '\'' +
                ", datum=" + datum +
                '}';
    }
}
