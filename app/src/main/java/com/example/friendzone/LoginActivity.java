package com.example.friendzone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendzone.controller.ControllerLogin;
import com.example.friendzone.controller.ControllerUser;
import com.example.friendzone.models.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button signIn;
    TextView createAccount;
    CheckBox checkbox;
    private ControllerUser controllerUser;
    boolean isChecked = false;
    private boolean  isNewUser;
    private   static  final  String CHANNEL_ID = "friendZone_id";
    public  static  final  String FROM_NOTIFICATION = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isNewUser = false;
        //Ce if fait en sorte que la "status bar" est transparente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signIn);
        createAccount = findViewById(R.id.createAccount);

        signIn.setOnClickListener(this);
        createAccount.setOnClickListener(this);

        checkbox = findViewById(R.id.checkbox);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                ControllerLogin controllerLogin = new ControllerLogin();
                controllerLogin.getLoginInfo(callbackLogin, this.username.getText().toString(), this.password.getText().toString());
                break;

            case R.id.createAccount:
                //Toast pour tester onCLick
                Toast.makeText(LoginActivity.this, "Create Account", Toast.LENGTH_SHORT).show();
                Intent intentCreateAccount = new Intent(this, CreateAccountActivity.class);
                startActivityForResult(intentCreateAccount, 1);
                break;
        }
    }

    public void validateLogin(Login login) {

        if (login.getAccessToken() != null && login.getError() == null) {
            Log.d("success", login.getAccessToken());
            String authorization = login.getTokenType() + " " + login.getAccessToken();
            setSharedPreference(authorization);
            controllerUser = new ControllerUser(Login.getAuthorization(this));
            controllerUser.getUserInfo(userCallback);
            //Toast pour tester onCLick

            Toast.makeText(LoginActivity.this, "Sign in", Toast.LENGTH_SHORT).show();
            //Intent pour ouvrir activity Main
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        }
    }

    private Callback<Login> callbackLogin = new Callback<Login>() {
        @Override
        public void onResponse(Call<Login> call, Response<Login> response) {
            if (response.isSuccessful()) {
                Login login = response.body();
                validateLogin(login);
            } else {
                Log.d("code", "onResponse: " + response.body().getErrorDescription());
                Log.d("code", String.valueOf(response.code()) + response.raw().message());
            }

        }

        @Override
        public void onFailure(Call<Login> call, Throwable t) {
            Log.d("falied", t.getMessage());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            isNewUser = data.getBooleanExtra(CreateAccountActivity.RESULT, false);
        }
    }


    private void sendNotification(){
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.setAction(FROM_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle("Welcome To FriendZone")
                .setContentText("For registration to complete Please complete your profile information")
                .setSmallIcon(R.drawable.ic_priority_high_black_24dp);

        builder.setContentIntent(contentIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Notification friendZone", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("description");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());

    }



    private Callback<User> userCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
                User user = response.body();
                SharedPreferences.Editor prefs = getApplicationContext().getSharedPreferences
                        ("user", MODE_PRIVATE)
                        .edit();
                Gson gson = new Gson();
                String json = gson.toJson(user);
                prefs.putString("currentUser", json);
                prefs.commit();
                if(isNewUser) {
                    sendNotification();
                }
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

        }
    };




    public void setSharedPreference(String authorization) {
        SharedPreferences.Editor prefs = this.getSharedPreferences(Login.AUTHORIZATION_PREF, MODE_PRIVATE).edit();
        prefs.putString(Login.AUTHORIZATION_HEADER_VALUE, authorization);
        prefs.apply();
    }
}
