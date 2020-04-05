package com.example.friendzone.Models;

import java.util.List;

public class Post {
    private int postId;
    private int userId;
    private String PostUrl;
    private String Caption;
    private List<Commentaire> PostCommentaires;

    public Post(int postId, int userId, String postUrl, String caption, List<Commentaire> postCommentaires) {
        this.postId = postId;
        this.userId = userId;
        PostUrl = postUrl;
        Caption = caption;
        PostCommentaires = postCommentaires;
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

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public List<Commentaire> getPostCommentaires() {
        return PostCommentaires;
    }

    public void setPostCommentaires(List<Commentaire> postCommentaires) {
        PostCommentaires = postCommentaires;
    }
}
