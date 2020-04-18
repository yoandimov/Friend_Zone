package com.example.friendzone;

import com.example.friendzone.Models.Post;
import com.example.friendzone.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebAPIService {
    @FormUrlEncoded
    @POST("login/getToken")
    Call<Login> getAccesToken(
            @Field("grant_type") String grantType,
            @Field("username")  String username,
            @Field("password") String password
    );

    @GET("api/user/getUser")
    Call<User> getUser(@Header("Authorization") String auth);

    @POST("api/auth/signUp")
    Call<Boolean> SignUp(@Body User user);

    @POST("api/post/create")
    Call<Post> createPost(@Body Post post);
}