package com.example.friendzone.Models;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class Post {
    private Integer postId;
    private int userId;
    private String PostUrl;
    private String Title;
    private String Content;
    private String CreationDate;

    private String Image64;

    private List<Commentaire> PostCommentaires;

    public Post(int userId, String postUrl, String title, String content, String image64,String postDate) {
        this.userId = userId;
        PostUrl = postUrl;
        Title = title;
        Content = content;
        Image64 = image64;
        CreationDate = postDate;

    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPostUrl() {
        return PostUrl;
    }

    public void setPostUrl(String postUrl) {
        PostUrl = postUrl;
    }

    public String getImage64() {
        return Image64;
    }

    public void setImage64(String image64) {
        Image64 = image64;
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

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
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
        return "Title: " + Title + " ;Content: " + Content + " ;Image: " + Image64 + " ;Data created: " + CreationDate;
    }
}
