package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendzone.controller.ControllerLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
EditText username, password;
Button signIn;
TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signIn:
                ControllerLogin controllerLogin = new ControllerLogin();
                controllerLogin.getLoginInfo(callbackLogin, this.username.getText().toString(), this.password.getText().toString());
                break;

            case R.id.createAccount:
                //Toast pour tester onCLick
                Toast.makeText(LoginActivity.this, "Create Account", Toast.LENGTH_SHORT).show();
                Intent intentCreateAccount = new Intent(this,CreateAccountActivity.class);
                startActivity(intentCreateAccount);
                break;
        }
    }

    public void validateLogin(Login login) {

        if(login.getAccessToken() != null && login.getError() == null){
            Log.d("success", login.getAccessToken());
            String authorization = login.getTokenType() + " " + login.getAccessToken();
            setSharedPreference(authorization);
            //Toast pour tester onCLick
            Toast.makeText(LoginActivity.this, "Sign in", Toast.LENGTH_SHORT).show();
            //Intent pour ouvrir activity Main
            Intent intentMain = new Intent(this,MainActivity.class);
            startActivity(intentMain);
        }
    }

    private Callback<Login> callbackLogin = new Callback<Login>() {
        @Override
        public void onResponse(Call<Login> call, Response<Login> response) {
            if(response.isSuccessful()) {
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
    public void setSharedPreference(String authorization) {
        SharedPreferences.Editor prefs = this.getSharedPreferences(Login.AUTHORIZATION_PREF, MODE_PRIVATE).edit();
        prefs.putString(Login.AUTHORIZATION_HEADER_VALUE, authorization);
        prefs.apply();
    }



}
