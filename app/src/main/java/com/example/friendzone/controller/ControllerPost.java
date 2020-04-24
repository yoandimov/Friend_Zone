package com.example.friendzone.controller;

import com.example.friendzone.models.Post;
import com.example.friendzone.WebAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerPost {
    public static final String BASE_URL = "http://10.0.2.2:50764/";
    private String auth;

    public ControllerPost(String auth) {
        this.auth = auth;
    }

    public void CreatePost(Callback<Post> postCallback, Post post){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<Post> call = webAPIService.createPost(this.auth,post);
        call.enqueue(postCallback);
    }

    public void GetPost(Callback<Post> postCallback, int id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<Post> call = webAPIService.getPost(id);
        call.enqueue(postCallback);
    }

    public void GetAllPosts(Callback<List<Post>> postCallback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<List<Post>> call = webAPIService.getAllPosts();
        call.enqueue(postCallback);
    }
}
