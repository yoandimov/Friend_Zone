package com.example.friendzone.Models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

public class User {

    public int userId;
    private String firstName;
    private String lastName;
    private String city;
    private String gender;
    private String country;
    private String description;
    private String profileImage;
    private String username;
    private String password;
    private String email;
    private String Permision;
    private List<Post> UserPost;

    public User(int userId, String firstName, String lastName, String city, String gender, String country, String description, String profileImage, String username, String password, String email, String permision, List<Post> userPost) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.gender = gender;
        this.country = country;
        this.description = description;
        this.profileImage = profileImage;
        this.username = username;
        this.password = password;
        this.email = email;
        Permision = permision;
        UserPost = userPost;
    }

    public User(String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public static User getInstance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("currentUser", "");
        User user = gson.fromJson(json, User.class);

        return user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermision() {
        return Permision;
    }

    public void setPermision(String permision) {
        Permision = permision;
    }

    public List<Post> getUserPost() {
        return UserPost;
    }

    public void setUserPost(List<Post> userPost) {
        UserPost = userPost;
    }
}