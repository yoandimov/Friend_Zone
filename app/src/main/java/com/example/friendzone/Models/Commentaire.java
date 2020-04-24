package com.example.friendzone.Models;

public class Commentaire {


    private int commentaireId;
    private int userId;
    private String username;
    private String profileImage;
    private int postId;
    private int referenceId;
    private String message;
    private String dateCreated;

    public Commentaire(int userId, String uName, String profileImage, int postId, int referenceId, String message, String DateCreated) {
        this.userId = userId;
        this.postId = postId;
        this.referenceId = referenceId;
        this.message = message;
        this.dateCreated = DateCreated;
        this.username = uName;
        this.profileImage = profileImage;
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

    public int getCommentaireId() {
        return commentaireId;
    }

    public void setCommentaireId(int commentaireId) {
        this.commentaireId = commentaireId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }


    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "commentaireId=" + commentaireId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", referenceId=" + referenceId +
                ", message='" + message + '\'' +
                ", DateCreated='" + dateCreated + '\'' +
                '}';
    }
}
