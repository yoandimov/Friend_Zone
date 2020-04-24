package com.example.friendzone;

import com.example.friendzone.models.Commentaire;
import com.example.friendzone.models.Post;
import com.example.friendzone.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("api/commentaire/createcommentaire")
    Call<Commentaire> CreateCommentaire(@Body Commentaire commentaire);

    @GET ("api/post/get/{id}")
    Call<Post> getPost(@Path("id")int id);

    @GET("api/post/getall")
    Call<List<Post>> getAllPosts();

    @GET("api/user/getuserpost")
    Call<List<Post>> getPostsByUser(@Header("Authorization") String auth);

    @POST("api/user/updateprofile")
    Call<Boolean> updateProfile(@Header("Authorization") String auth, @Body User user);
}
