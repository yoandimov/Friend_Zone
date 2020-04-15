package com.example.friendzone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePostActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    EditText caption, postContent;
    ImageView imageView;
    Uri image_uri;

    private int PICK_IMAGE_REQUEST = 1;
    private int TAKE_PICTURE_REQUEST = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        getSupportActionBar().setTitle("Create a post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        caption = findViewById(R.id.caption);
        postContent = findViewById(R.id.textField);
        imageView = findViewById(R.id.imageView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Pick image result
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Take photo result
        else if(requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK){
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageURI(image_uri);
        }
    }

    public void checkCameraPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED){
                //no permission...request permission
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            }
            else{
                //permission granted
                openCamera();
            }
        }
        else{
            //permission granted
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                    if(grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                        openCamera();
                    }
                    else{
                        Toast.makeText(this,"Permission denied...",Toast.LENGTH_SHORT).show();
                    }
            }
        }
    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Friendzon");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, TAKE_PICTURE_REQUEST);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_post_menu, menu);
        return true;
    }

    //Actionbar menu click options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //ADD IMAGE
        switch (item.getItemId()) {
            //Back button
            case android.R.id.home:
                finish();
                return true;
            //Import picture from gallery
            case R.id.addImageOption:
                chooseImage();
                return true;
            //Take photo with camera
            case R.id.takePictureOption:
                checkCameraPermission();
                return true;
            //Post
            case R.id.postOption:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
