package com.example.friendzone.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.friendzone.R;

import java.util.List;

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

    @BindingAdapter({ "bind:imageBitmap" })
    public static void loadImage(ImageView imageView, String image) {
        if(image != null) {
            imageView.setVisibility(View.VISIBLE);
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }
    }


    @BindingAdapter({ "bind:profileImageBitmap" })
    public static void loadProfileImage(ImageView imageView, String image) {
        if(image != null) {
            if(image.contains("https://")){
                Glide.with(imageView.getContext())
                        .setDefaultRequestOptions(new RequestOptions()
                                .circleCrop())
                        .load(image)
                        .placeholder(R.drawable.ic_person_black_24dp)
                        .into(imageView);
            } else {
                imageView.setVisibility(View.VISIBLE);
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }
        } else {
            Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_person_black_24dp);
            imageView.setImageDrawable(drawable);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + PostId +"Title: " + Title + " ;Content: " + Content + " ;Image: " + Image + " ;Data created: " + DateCreated;
    }
}