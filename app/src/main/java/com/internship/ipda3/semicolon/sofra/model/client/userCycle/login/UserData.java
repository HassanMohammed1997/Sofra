
package com.internship.ipda3.semicolon.sofra.model.client.userCycle.login;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserData {

    @SerializedName("api_token")
    private String mApiToken;
    @SerializedName("user")
    private UserDatum mUser;

    public String getApiToken() {
        return mApiToken;
    }

    public void setApiToken(String apiToken) {
        mApiToken = apiToken;
    }

    public UserDatum getUser() {
        return mUser;
    }

    public void setUser(UserDatum user) {
        mUser = user;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "mApiToken='" + mApiToken + '\'' +
                ", mUser=" + mUser +
                '}';
    }
}
