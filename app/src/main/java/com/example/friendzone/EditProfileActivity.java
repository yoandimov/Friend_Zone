package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {

    EditText firstname, lastname, city, gender, country, bio, username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firstname = findViewById(R.id.editFistname);
        lastname = findViewById(R.id.editLastname);
        gender = findViewById(R.id.editGender);
        city = findViewById(R.id.editCity);
        country = findViewById(R.id.editCountry);
        bio = findViewById(R.id.editDesc);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPasswd);
        email = findViewById(R.id.editEmail);
    }
}
