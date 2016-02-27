package com.codepath.apps.androidtwitter.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.codepath.apps.restclienttemplate.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aparna on 2/17/16.
 */
public class TweetsArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Tweet> mTweets;
    Context mContext;
    
    // Define listener member variable
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // Pass in the Article array into the constructor
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }

    public void addAll(ArrayList<Tweet> tweets) {
        mTweets.addAll(tweets);
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @Bind(R.id.tvUserName) TextView tvUserName;
        @Bind(R.id.tvBody) TextView tvBody;
        @Bind(R.id.ivImage) ImageView ivImage;
        @Bind(R.id.tvCreatedAt) TextView tvCreatedAt;
        private Context context;
        private Tweet tweet;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public TweetViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // We can access the data within the views
            Intent i = new Intent(context, ProfileActivity.class);
            i.putExtra("tweet", tweet);
            context.startActivity(i);
        }
    }
    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        TweetViewHolder viewHolder = new TweetViewHolder(tweetView );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TweetViewHolder tvHolder = (TweetViewHolder)viewHolder;
        tvHolder.tvUserName.setText("");
        Tweet tweet = mTweets.get(position);
        ((TweetViewHolder) viewHolder).tweet = tweet;
        tvHolder.tvBody.setText(tweet.getText());
        tvHolder.tvCreatedAt.setText(tweet.getCreatedAt());
        tvHolder.ivImage.setImageResource(android.R.color.transparent);
        if (tweet.getUser() != null){
            tvHolder.tvUserName.setText(tweet.getUser().getScreen_name());
            Glide.with(mContext).load(tweet.getUser().getProfile_image_url()).centerCrop().into(tvHolder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public List<Tweet> getTweets() {
        return mTweets;
    }
}
