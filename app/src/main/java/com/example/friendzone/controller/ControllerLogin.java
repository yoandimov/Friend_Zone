package com.example.friendzone.controller;

import com.example.friendzone.Login;
import com.example.friendzone.models.User;
import com.example.friendzone.WebAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerLogin {
    public static final String GRANT_TYPE = "password";
    public static final String BASE_URL = "http://10.0.2.2:50764/";

    public void getLoginInfo(Callback<Login> loginCallback,String username, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<Login> call = webAPIService.getAccesToken(GRANT_TYPE,username, password);
        call.enqueue(loginCallback);
    }

    public void SignUpUser(Callback<Boolean> booleanCallback, User user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<Boolean> call = webAPIService.SignUp(user);
        call.enqueue(booleanCallback);
    }


}
