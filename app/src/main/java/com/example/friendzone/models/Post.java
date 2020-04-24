package com.example.friendzone.models;

import androidx.annotation.NonNull;

public class Post {
    private Integer PostId;
    private int UserId;
    private String username;
    private String profileImage;
    private String PostUrl;
    private String Title;
    private String Content;
    private String DateCreated;
    private String Image;

    public Post(int userId, String uName, String profileImage, String postUrl, String title, String content, String dateCreated, String image) {
        UserId = userId;
        this.username = uName;
        this.profileImage = profileImage;
        PostUrl = postUrl;
        Title = title;
        Content = content;
        DateCreated = dateCreated;
        Image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        this.PostId = postId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }

    public String getPostUrl() {
        return PostUrl;
    }

    public void setPostUrl(String postUrl) {
        PostUrl = postUrl;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }


    @NonNull
    @Override
    public String toString() {
        return "Id: " + PostId +"Title: " + Title + " ;Content: " + Content + " ;Image: " + Image + " ;Data created: " ;
    }
}