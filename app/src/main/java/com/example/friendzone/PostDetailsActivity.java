package com.example.friendzone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.friendzone.models.Commentaire;
import com.example.friendzone.models.Post;
import com.example.friendzone.controller.ControllerCommentaire;
import com.example.friendzone.controller.ControllerPost;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.friendzone.MainActivity.EXTRA_POST_ID;

public class PostDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView uPictureIv, pImageIv;
    TextView uNameTv, pTimeTv, pTitleTv, pDescriptionTv;
    ImageButton moreBtn;

    //add commentaire views
    EditText commentEt;
    ImageButton sendBtn;
    ImageView cAvatarIv;
    Commentaire commentaire;

    private Post post;
    private ControllerPost controllerPost;
    private ControllerCommentaire controllerCommentaire;

    private int userId = 0;
    private int post_id;
    private String cTimeStamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Intent intent = getIntent();
        post_id = intent.getIntExtra(EXTRA_POST_ID, -1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Post Details");
        actionBar.setDisplayHomeAsUpEnabled(true);

        uPictureIv = findViewById(R.id.uPictureIv);
        pImageIv = findViewById(R.id.pImageIv);
        uNameTv = findViewById(R.id.uNameTv);
        pTimeTv = findViewById(R.id.pTimeTv);
        pTitleTv = findViewById(R.id.pTitleTv);
        pDescriptionTv = findViewById(R.id.pDescriptionTv);
        moreBtn = findViewById(R.id.moreBtn);
        commentEt = findViewById(R.id.commentEt);
        sendBtn = findViewById(R.id.sendBtn);
        cAvatarIv = findViewById(R.id.cAvatarIv);

        controllerPost = new ControllerPost();
        controllerPost.GetPost(getPostCallback, post_id);

        controllerCommentaire = new ControllerCommentaire();

        registerReciever();

        sendBtn.setOnClickListener(this);
    }

    private void registerReciever() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.friendzone.POST_CLICKED");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String pTitle = post.getTitle();
                String pTimeStamp = post.getDateCreated();
                String pContent = post.getContent();
                String pImage = post.getImage();

                pTimeTv.setText(pTimeStamp);
                if(pTitleTv != null){
                    pTitleTv.setVisibility(View.VISIBLE);
                    pTitleTv.setText(pTitle);
                }
                if(pContent != null){
                    pDescriptionTv.setVisibility(View.VISIBLE);
                    pDescriptionTv.setText(pContent);
                }
                if(pImage != null){
                    pImageIv.setVisibility(View.VISIBLE);
                    byte[] decodedString = Base64.decode(pImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    pImageIv.setImageBitmap(decodedByte);
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    Callback<Post> getPostCallback = new Callback<Post>() {
        @Override
        public void onResponse(Call<Post> call, Response<Post> response) {
            if (!response.isSuccessful()) {
                return;
            }
            post = response.body();

            String broadcast = "com.example.friendzone.POST_CLICKED";
            Intent sendPostUpdated = new Intent(broadcast);
            sendBroadcast(sendPostUpdated);
        }

        @Override
        public void onFailure(Call<Post> call, Throwable t) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
            alert.setTitle("Error");
            alert.setMessage(t.getMessage());
            alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(PostDetailsActivity.this, "Error displaying content", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Callback<Commentaire> postCommentaireCallback = new Callback<Commentaire>() {
        @Override
        public void onResponse(Call<Commentaire> call, Response<Commentaire> response) {
            if (!response.isSuccessful()) {
                return;
            }
            Toast.makeText(PostDetailsActivity.this, "Posted", Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onFailure(Call<Commentaire> call, Throwable t) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
            alert.setTitle("Error");
            alert.setMessage(t.getMessage());
            alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(PostDetailsActivity.this, "Comment wasn't created", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //ADD IMAGE
        switch (item.getItemId()) {
            //Back button
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendBtn:
                createComment();

                break;
        }
    }

    public void createComment(){
        if(commentEt.getText().toString().trim().length() != 0) {
            String strMessage = commentEt.getText().toString();
            cTimeStamp = Calendar.getInstance().getTime().toString();
            commentaire = new Commentaire(userId, post_id, userId, strMessage, cTimeStamp);
            controllerCommentaire.CreateCommentaire(postCommentaireCallback, commentaire);
            commentEt.setText("");
            Log.d("Comment", "Comment: " + commentaire.toString());
        }else{
            Toast.makeText(PostDetailsActivity.this, "Please enter comment", Toast.LENGTH_SHORT).show();
        }

        //int userId, int postId, String message, String DateCreated
    }
}
