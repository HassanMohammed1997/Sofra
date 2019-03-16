
package com.internship.ipda3.semicolon.sofra.model.restaurent.login;

import com.google.gson.annotations.SerializedName;


public class RestaurantData {

    @SerializedName("api_token")
    private String mApiToken;
    @SerializedName("user")
    private RestaurantUser mUser;

    public String getApiToken() {
        return mApiToken;
    }

    public void setApiToken(String apiToken) {
        mApiToken = apiToken;
    }

    public RestaurantUser getUser() {
        return mUser;
    }

    public void setUser(RestaurantUser user) {
        mUser = user;
    }

    @Override
    public String toString() {
        return "RestaurantData{" +
                "mApiToken='" + mApiToken + '\'' +
                ", mUser=" + mUser +
                '}';
    }
}
