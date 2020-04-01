package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
EditText email, password;
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

        email = findViewById(R.id.email);
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
                //Toast pour tester onCLick
                Toast.makeText(LoginActivity.this, "Sign in", Toast.LENGTH_SHORT).show();
                //Intent pour ouvrir activity Main
                Intent intentMain = new Intent(this,MainActivity.class);
                startActivity(intentMain);
                break;

            case R.id.createAccount:
                //Toast pour tester onCLick
                Toast.makeText(LoginActivity.this, "Create Account", Toast.LENGTH_SHORT).show();
                Intent intentCreateAccount = new Intent(this,CreateAccountActivity.class);
                startActivity(intentCreateAccount);
                break;
        }
    }
}
