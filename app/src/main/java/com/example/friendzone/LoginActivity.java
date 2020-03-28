package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);

            case R.id.createAccount:
                //Toast pour tester onCLick
                Toast.makeText(LoginActivity.this, "Create Account", Toast.LENGTH_SHORT).show();
        }
    }
}
