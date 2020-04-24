package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.friendzone.Models.User;
import com.example.friendzone.controller.ControllerUser;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    // Widgets
    private EditText firstname, lastname, city, gender, country, bio, username, password, email;
    Button btnSave;

    // Objects
    private User currentUser;
    private ControllerUser controllerUser;

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
        btnSave = findViewById(R.id.btnSave);

        currentUser = User.getInstance(this);

        controllerUser = new ControllerUser(Login.getAuthorization(this));

        getAllAvailableInfo();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(currentUser);
                setSharedPreference(currentUser);
                Intent intent = new Intent();
                intent.putExtra(UserProfileActivity.RESPONSE, 0);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public void setSharedPreference(User user) {
        SharedPreferences.Editor prefs = getApplicationContext().getSharedPreferences
                ("user", MODE_PRIVATE)
                .edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefs.putString("currentUser", json);
        prefs.commit();
    }

    public void getAllAvailableInfo() {
        country.setText(currentUser.getCountry());
        firstname.setText(currentUser.getFirstName());
        lastname.setText(currentUser.getLastName());
        gender.setText(currentUser.getGender());
        city.setText(currentUser.getCity());
        bio.setText(currentUser.getDescription());
        username.setText(currentUser.getUsername());
        password.setText(currentUser.getPassword());
        email.setText(currentUser.getEmail());
    }


    public void updateUser(User user) {

        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String _city = city.getText().toString();
        String _gender = gender.getText().toString();
        String _country = country.getText().toString();
        String _bio = bio.getText().toString();


        String _username = username.getText().toString();
        String _password = password.getText().toString();
        String _email = email.getText().toString();

        user.setUsername(_username);
        user.setPassword(_password);
        user.setEmail(_email);

        user.setFirstName(fname);
        user.setLastName(lname);
        user.setCountry(_country);
        user.setGender(_gender);
        user.setCity(_city);
        user.setDescription(_bio);



        controllerUser.UpdateProfile(getBoolCallback, user);
    }

    Callback<Boolean> getBoolCallback = new Callback<Boolean>() {
        @Override
        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            if(response.isSuccessful()){
                Boolean successOrNot = response.body();
            } else {
            }
        }

        @Override
        public void onFailure(Call<Boolean> call, Throwable t) {
        }
    };
}
