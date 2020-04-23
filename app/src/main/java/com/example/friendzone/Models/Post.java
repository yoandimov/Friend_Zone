package com.example.friendzone.Models;

import androidx.annotation.NonNull;

import java.util.List;

public class Post {
    private Integer PostId;
    private int UserId;
    private String PostUrl;
    private String Title;
    private String Content;
    private String DateCreated;

    private String Image;

    private List<Commentaire> PostCommentaires;

    public Post(int userId, String postUrl, String title, String content, String image, String postDate) {
        this.UserId = userId;
        PostUrl = postUrl;
        Title = title;
        Content = content;
        Image = image;
        DateCreated = postDate;
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

    public List<Commentaire> getPostCommentaires() {
        return PostCommentaires;
    }

    public void setPostCommentaires(List<Commentaire> postCommentaires) {
        PostCommentaires = postCommentaires;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + Title + " ;Content: " + Content + " ;Image: " + Image + " ;Data created: " + DateCreated;
    }
}