package com.example.friendzone;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

import static android.content.Context.MODE_PRIVATE;

public class Login {
    public static String AUTHORIZATION_PREF = "AuthorizationToken";
    public static String AUTHORIZATION_HEADER_VALUE = "authorizationHeaderValue";
    private String error;
    @SerializedName("error_desciption")
    private String errorDescription;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private int expireDate;

    public Login(String error, String errorDescription, String accessToken, String tokenType, int expireDate) {
        this.error = error;
        this.errorDescription = errorDescription;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expireDate = expireDate;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(int expireDate) {
        this.expireDate = expireDate;
    }

    public static String getAuthorization(Context context){
        SharedPreferences prefs = context.getSharedPreferences(AUTHORIZATION_PREF, MODE_PRIVATE);
        return prefs.getString(AUTHORIZATION_HEADER_VALUE, "");
    }

    public static Boolean ValidateSamePassword(String password, String ConfirmedPassword){
        return  password.equals(ConfirmedPassword);
    }
}