package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.friendzone.models.User;
import com.example.friendzone.controller.ControllerLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateAccountActivity extends AppCompatActivity {
    private EditText username, password, confirmedPassword, email;
    private final  String EMPTY_FIELD_ERROR = "This field Cannot be empty";
    private ControllerLogin controllerLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmedPassword = findViewById(R.id.confirmPassword);
        email = findViewById(R.id.email);
        controllerLogin = new ControllerLogin();
        //Ce if fait en sorte que la "status bar" est transparente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    public boolean checkEmptyField(){
        if(TextUtils.isEmpty(username.getText().toString())){
            username.setError(EMPTY_FIELD_ERROR);
            return false;
        } else if(TextUtils.isEmpty(password.getText().toString())){
            password.setError(EMPTY_FIELD_ERROR);
            return false;
        } else if(TextUtils.isEmpty(confirmedPassword.getText().toString())){
            confirmedPassword.setError(EMPTY_FIELD_ERROR);
            return false;
        } else if (TextUtils.isEmpty(email.getText().toString())){
            email.setError(EMPTY_FIELD_ERROR);
            return false;
        }
        return true;
    }
    private Callback<Boolean> booleanCallback = new Callback<Boolean>() {
        @Override
        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            if(response.isSuccessful()){
                Boolean successOrNot = response.body();
                Log.d("Resulat", String.valueOf(successOrNot));
            } else {
                Log.d("Code", String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(Call<Boolean> call, Throwable t) {
            Log.d("error", t.getMessage());
        }
    };

    public void SignUp(View v){
        if(checkEmptyField()) {
            if (Login.ValidateSamePassword(password.getText().toString(), confirmedPassword.getText().toString())) {
                User user = new User(username.getText().toString(), password.getText().toString(), password.getText().toString());
                controllerLogin.SignUpUser(booleanCallback, user);
            }
        }
    }
}
