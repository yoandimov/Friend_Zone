package com.example.friendzone.controller;

import android.content.Context;

import com.example.friendzone.Models.Commentaire;
import com.example.friendzone.Models.Post;
import com.example.friendzone.R;
import com.example.friendzone.WebAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerCommentaire {

    public static final String BASE_URL = "http://10.0.2.2:50764/";
    Context context;

    public ControllerCommentaire(Context context) {
        this.context = context.getApplicationContext();
    }

    public void CreateCommentaire(Callback<Commentaire> commentaireCallback, Commentaire commentaire){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<Commentaire> call = webAPIService.CreateCommentaire(context.getResources().getString(R.string.token),commentaire);
        call.enqueue(commentaireCallback);
    }

    public void GetCommentairesByPost(Callback<List<Commentaire>> listCallback, int idPost){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebAPIService webAPIService = retrofit.create(WebAPIService.class);
        Call<List<Commentaire>> call = webAPIService.getCommentairesByPost(context.getResources().getString(R.string.token), idPost);
        call.enqueue(listCallback);
    }
}
