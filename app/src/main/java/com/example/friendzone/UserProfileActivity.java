package com.example.friendzone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendzone.Models.Post;
import com.example.friendzone.Models.User;
import com.example.friendzone.controller.ControllerUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.friendzone.MainActivity.EXTRA_POST_ID;

public class UserProfileActivity extends AppCompatActivity implements PostAdapter.OnPostClickListener {

    // Widgets
    private ImageView profilepic;
    private TextView username, firstname;
    private RecyclerView recyclerView;

    // CONSTANTS
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;

    // Objects
    private User currentUser;
    private ControllerUser controllerUser;
    private List<Post> postList = new ArrayList<>();
    private PostAdapter postAdapter;

    private LinearLayoutManager linearLayoutManager;

    BroadcastReceiver receiver;

    private String Image64 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profilepic = (ImageView) findViewById(R.id.profile_picture);
        username = (TextView) findViewById(R.id.username);
        firstname = (TextView) findViewById(R.id.fullname);
        recyclerView = findViewById(R.id.postsByUser);

        linearLayoutManager = new LinearLayoutManager(UserProfileActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        currentUser = User.getInstance(this);
        username.setText("@" + currentUser.getUsername());

        controllerUser = new ControllerUser(Login.getAuthorization(this));
        controllerUser.GetPostsByUser(getPostsCallback);

        registerReceiver();

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilepic.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] byteArrayImage = baos.toByteArray();
                String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                Image64 = encodedImage;
                updateUser(User.getInstance(this));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.friendzone.POSTS_BY_USER");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                postAdapter = new PostAdapter(getApplicationContext(), postList);
                postAdapter.setOnPostClickListener(UserProfileActivity.this);
                recyclerView.setAdapter(postAdapter);

            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
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

    public void updateUser(User user) {
        user.setProfileImage(Image64);
        controllerUser.UpdateProfile(getBoolCallback, user);
    }

    Callback<List<Post>> getPostsCallback = new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (!response.isSuccessful()) {

                return;
            }
            postList = response.body();

            String broadcast = "com.example.friendzone.POSTS_BY_USER";
            Intent sendPostUpdated = new Intent(broadcast);
            sendBroadcast(sendPostUpdated);

        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
            alert.setTitle("Error");
            alert.setMessage(t.getMessage());
            alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(UserProfileActivity.this, "Error displaying content", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    public void onPostClick(int id) {
        postList.get(id);
        Intent postDetailsIntent = new Intent(this, PostDetailsActivity.class);
        Post clickedPost = postList.get(id);

        if(clickedPost != null) {
            postDetailsIntent.putExtra(EXTRA_POST_ID, clickedPost.getPostId());
            startActivity(postDetailsIntent);
        }
    }
}