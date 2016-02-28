package com.codepath.apps.androidtwitter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.codepath.apps.androidtwitter.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.tvDetailUserName)TextView tvUserName;
    @Bind(R.id.tvDetailScreenName)TextView tvScreenName;
    @Bind(R.id.ivDetailProfileImage)ImageView ivUserImage;
    @Bind(R.id.ivDetailImage)ImageView ivDetailImage;
    @Bind(R.id.tvDetailTweet)TextView tvDetailTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        Tweet tweet = (Tweet)getIntent().getSerializableExtra("tweet");
        populateDetailPage(tweet);

    }

    private void populateDetailPage(Tweet tweet) {
        User user = tweet.getUser();
        tvUserName.setText(user.getName());
        tvScreenName.setText(user.getScreen_name());
        Glide.with(this).load(user.getProfile_image_url()).centerCrop().into(ivUserImage);
//        Glide.with(this).load(user.getProfile_image_url()).centerCrop().into(ivDetailImage);

    }

}
