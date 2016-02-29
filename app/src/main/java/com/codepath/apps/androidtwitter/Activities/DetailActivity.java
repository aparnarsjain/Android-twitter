package com.codepath.apps.androidtwitter.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.codepath.apps.androidtwitter.models.User;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
        tvDetailTweet.setText(tweet.getText());
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.with(this).load(user.getProfile_image_url()).transform(transformation).into(ivUserImage);
//        Glide.with(this).load(user.getProfile_image_url()).centerCrop().into(ivDetailImage);

    }

}
