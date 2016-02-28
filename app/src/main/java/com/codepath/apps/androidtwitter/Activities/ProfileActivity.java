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
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

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
            screenName = user.getScreen_name();
            getSupportActionBar().setTitle(user.getScreen_name());
            populateProfileHeader(user);

        }else {
            client = TwitterApplication.getRestClient();
            //Get the account info
            client.getCurrentUser(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJsonObject(response);
                    getSupportActionBar().setTitle(user.getScreen_name());
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
        tvUserName.setText(user.getScreen_name());
        tvTagLine.setText(user.getTag_line());
        tvFollowers.setText(Long.toString(user.getFollowers_count()));
        tvFollowing.setText(Long.toString(user.getFollowing_count()));
        Glide.with(this).load(user.getProfile_image_url()).centerCrop().into(ivUserImage);

    }


}
