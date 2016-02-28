package com.codepath.apps.androidtwitter.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.androidtwitter.Fragments.UserTimelineFragment;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.TwitterApplication;
import com.codepath.apps.androidtwitter.TwitterClient;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.codepath.apps.androidtwitter.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;
    @Bind(R.id.tvUName)TextView tvUserName;
    @Bind(R.id.tvTagLine)TextView tvTagLine;
    @Bind(R.id.ivProfileImage)ImageView ivUserImage;
    @Bind(R.id.tvFollowers)TextView tvFollowers;
    @Bind(R.id.tvFollowing)TextView tvFollowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        String screenName = "";

        Tweet tweet = (Tweet)getIntent().getSerializableExtra("tweet");
//        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        if (tweet != null){
            user = tweet.getUser();
            screenName = user.getScreenName();
            getSupportActionBar().setTitle(user.getScreenName());
            populateProfileHeader(user);

        }else {
            client = TwitterApplication.getRestClient();
            //Get the account info
            client.getCurrentUser(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Type collectionType = new TypeToken<User>() {
                    }.getType();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    user = gson.fromJson(response.toString(), collectionType);

//                    user = User.fromJsonObject(response);
                    getSupportActionBar().setTitle(user.getScreenName());
                    populateProfileHeader(user);
                }
            });
            screenName = getIntent().getStringExtra("screenName");
        }


        if (savedInstanceState == null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();//changes the fragment
        }
    }

    private void populateProfileHeader(User user) {
        tvUserName.setText(user.getScreenName());
        tvTagLine.setText(user.getDescription());
        tvFollowers.setText(Long.toString(user.getFollowersCount()));
        tvFollowing.setText(Long.toString(user.getFavouritesCount()));
        Glide.with(this).load(user.getProfileImageUrl()).centerCrop().into(ivUserImage);

    }


}
