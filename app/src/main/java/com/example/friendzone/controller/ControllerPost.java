package com.example.friendzone.controller;

import com.example.friendzone.Models.Post;
import com.example.friendzone.Models.User;
import com.example.friendzone.WebAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerPost {
    public static final String BASE_URL = "http://10.0.2.2:50764/";

    public  void CreatePost(Callback<Post> postCallback, Post post){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<Post> call = webAPIService.createPost(post);
        call.enqueue(postCallback);
    }

}
