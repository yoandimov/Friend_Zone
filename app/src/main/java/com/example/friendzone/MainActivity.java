package com.example.friendzone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import com.example.friendzone.adapters.PostAdapter;
import com.example.friendzone.models.Post;
import com.example.friendzone.models.User;
import com.example.friendzone.controller.ControllerPost;
import com.example.friendzone.controller.ControllerUser;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PostAdapter.OnPostClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private ControllerUser controllerUser;
    private RecyclerView recyclerView;
    private List<Post> postList = new ArrayList<>();
    private PostAdapter postAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ControllerPost controllerPost;
    private AdapterView.OnItemClickListener listener;
    private User currentUser;
    private BroadcastReceiver receiver;

    public static final String EXTRA_POST_ID = "post_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerUser = new ControllerUser(Login.getAuthorization(this));
        controllerUser.getUserInfo(userCallback);

        currentUser = User.getInstance(this);

        recyclerView = findViewById(R.id.postsRecyclerView);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        controllerPost = new ControllerPost(Login.getAuthorization(this));
        controllerPost.GetAllPosts(getAllPostsCallback);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        registerReciever();

        /*
        NavHeaderBinding binding = DataBindingUtil.setContentView(this, R.layout.nav_header);
        currentUser = User.getInstance(this);
        binding.setUser(currentUser);

         */


        /*
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        NavHeaderBinding _bind = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header,
                binding.navView,
                false);
        binding.navView.addHeaderView(_bind.getRoot());
        currentUser = User.getInstance(this);
        _bind.setUser(currentUser);

         */
    }

    private void registerReciever() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.friendzone.POSTS_UPDATED");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                postAdapter = new PostAdapter(getApplicationContext(), postList);
                postAdapter.setOnPostClickListener(MainActivity.this);
                recyclerView.setAdapter(postAdapter);

            }
        };
        registerReceiver(receiver, filter);
    }

    Callback<List<Post>> getAllPostsCallback = new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (!response.isSuccessful()) {

                return;
            }
            postList = response.body();

            String broadcast = "com.example.friendzone.POSTS_UPDATED";
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
                    Toast.makeText(MainActivity.this, "Error displaying content", Toast.LENGTH_SHORT).show();
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

    /**
     * ANDROID BACK BUTTON ACTION
     * If the drawer is open when we click on the back button, it closes.
     * Otherwise, it goes to the previous activity.
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ACTIONS OF ITEMS IN THE MENU
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, UserProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settings:
                recyclerView.setVisibility(View.GONE);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsScreen())
                        .addToBackStack(null)
                        .commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Loads the settings view.
     */
    public static class SettingsScreen extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_screen);
        }
    }

    private Callback<User> userCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
                User user = response.body();
                String str = String.format("%d %s %s %s", user.userId, user.getFirstName(), user.getUsername(), user.getEmail());

                SharedPreferences.Editor prefs = getApplicationContext().getSharedPreferences
                        ("user", MODE_PRIVATE)
                        .edit();

                Gson gson = new Gson();
                String json = gson.toJson(user);
                prefs.putString("currentUser", json);
                prefs.commit();
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        unregisterReceiver(receiver);
        controllerPost.GetAllPosts(getAllPostsCallback);
    }

    //Ajoute option menu au toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post:
                Intent intent = new Intent(this, CreatePostActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}