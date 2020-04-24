package com.example.friendzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

import com.example.friendzone.models.Commentaire;
import com.example.friendzone.models.Post;
import com.example.friendzone.models.User;
import com.example.friendzone.adapters.CommentAdapter;
import com.example.friendzone.controller.ControllerCommentaire;
import com.example.friendzone.controller.ControllerPost;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private int userId = 1;
    private int post_id;
    private String cTimeStamp;
    private List<Commentaire> commentaireList;
    private User currentUser;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        recyclerView = findViewById(R.id.commentRecyclerView);

        linearLayoutManager = new LinearLayoutManager(PostDetailsActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

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

        controllerPost = new ControllerPost(Login.getAuthorization(this));
        controllerPost.GetPost(getPostCallback, post_id);

        controllerCommentaire = new ControllerCommentaire(Login.getAuthorization(this));
        controllerCommentaire.GetCommentairesByPost(getCommentairesCallback, post_id);

        //instancier le user
        currentUser = User.getInstance(this);

        registerReciever();

        sendBtn.setOnClickListener(this);
    }

    private void registerReciever() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.friendzone.POST_CLICKED");
        filter.addAction("com.example.friendzone.COMMENTS_RECIEVED");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()) {
                    case "com.example.friendzone.POST_CLICKED":

                        String uName = post.getUsername();
                        String uImage = post.getProfileImage();
                        String pTitle = post.getTitle();
                        String pTimeStamp = post.getDateCreated();
                        String pContent = post.getContent();
                        String pImage = post.getImage();


                        uNameTv.setText(uName);

                        if (uImage != null) {
                            byte[] decodedStringUser = Base64.decode(uImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedStringUser, 0, decodedStringUser.length);
                            uPictureIv.setImageBitmap(decodedByte);
                        }

                        pTimeTv.setText(pTimeStamp);

                        if (pTitle != null) {
                            Log.d("TAG", "Title isnt null");
                            pTitleTv.setVisibility(View.VISIBLE);
                            pTitleTv.setText(pTitle);
                        }
                        if (pContent != null) {
                            pDescriptionTv.setVisibility(View.VISIBLE);
                            pDescriptionTv.setText(pContent);
                        }
                        if (pImage != null) {
                            pImageIv.setVisibility(View.VISIBLE);
                            byte[] decodedStringPost = Base64.decode(pImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedStringPost, 0, decodedStringPost.length);
                            pImageIv.setImageBitmap(decodedByte);
                        }
                        Log.d("TAG", "Binding: " + post.toString());
                        break;

                    case "com.example.friendzone.COMMENTS_RECIEVED":
                        Log.d("TAG", "onReceive: " + commentaireList.toString());
                        commentAdapter = new CommentAdapter(getApplicationContext(), commentaireList);
                        recyclerView.setAdapter(commentAdapter);
                        break;
                }
            }
        };
        registerReceiver(receiver, filter);

    }

    Callback<List<Commentaire>> getCommentairesCallback = new Callback<List<Commentaire>>() {
        @Override
        public void onResponse(Call<List<Commentaire>> call, Response<List<Commentaire>> response) {
            if (!response.isSuccessful()) {
                Log.d("TAG", "onResponse: ERROR"+response.code());
                return;
            }
            commentaireList = response.body();
            Log.d("TAG", "onResponse: " + commentaireList.toString());

            String broadcast = "com.example.friendzone.COMMENTS_RECIEVED";
            Intent sendPostUpdated = new Intent(broadcast);
            sendBroadcast(sendPostUpdated);
        }

        @Override
        public void onFailure(Call<List<Commentaire>> call, Throwable t) {
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
                unregisterReceiver(receiver);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendBtn:
                createComment();
                unregisterReceiver(receiver);
                //controllerCommentaire.GetCommentairesByPost(getCommentairesCallback, post_id);
                //commentAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void createComment() {
        if (commentEt.getText().toString().trim().length() != 0) {
            String strMessage = commentEt.getText().toString();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-YY");
            String formatted = format1.format(cal.getTime());
            cTimeStamp = formatted;
            commentaire = new Commentaire(currentUser.getUserId(), currentUser.getUsername(), currentUser.getProfileImage(), post_id, userId, strMessage, cTimeStamp);
            controllerCommentaire.CreateCommentaire(postCommentaireCallback, commentaire);
            commentEt.setText("");
            Log.d("Comment", "Comment: " + commentaire.toString());
        } else {
            Toast.makeText(PostDetailsActivity.this, "Please enter comment", Toast.LENGTH_SHORT).show();
        }

        //int userId, int postId, String message, String DateCreated
    }
}
