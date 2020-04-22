package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.friendzone.Models.User;
import com.example.friendzone.controller.ControllerUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private ControllerUser controllerUser;
    ImageView profilepic;
    TextView username, post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profilepic = (ImageView) findViewById(R.id.imageView);
        username = (TextView) findViewById(R.id.username);
        post = (TextView) findViewById(R.id.post);

        controllerUser = new ControllerUser(Login.getAuthorization(this));
        controllerUser.getUserInfo(userCallback);
    }

    private Callback<User> userCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if(response.isSuccessful()) {
                User user = response.body();
                String str = String.format("%d %s %s %s", user.userId, user.getFirstName(), user.getUsername(), user.getEmail());
                Log.d("Userlog", str);
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

        }
    };
}
