package com.example.friendzone.controller;

import com.example.friendzone.models.Post;
import com.example.friendzone.models.User;
import com.example.friendzone.WebAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerUser {
    private  String auth;

    public ControllerUser(String auth) {
        this.auth = auth;
    }

    public static final String BASE_URL = "http://10.0.2.2:50764/";

    public void getUserInfo(Callback<User> userCallback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<User> call = webAPIService.getUser(this.auth);
        call.enqueue(userCallback);
    }

    public void GetPostsByUser(Callback<List<Post>> postCallback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<List<Post>> call = webAPIService.getPostsByUser(this.auth);
        call.enqueue(postCallback);
    }

    public void UpdateProfile(Callback<Boolean> booleanCallback, User user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<Boolean> call = webAPIService.updateProfile(this.auth, user);
        call.enqueue(booleanCallback);
    }
}
