package com.codepath.apps.androidtwitter.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aparna on 2/17/16.
 */
public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    Context mContext;

//    public TweetsArrayAdapter(Context context, List<Tweet>tweets) {
//        super(context, android.R.layout.simple_list_item_1, tweets);
//    }
    // Define listener member variable
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
/*    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the tweet
        ViewHolder holder;

        Tweet tweet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvUserName.setText(tweet.getUser().getScreen_name());
        holder.tvBody.setText(tweet.getText());
        holder.tvCreatedAt.setText(tweet.getCreatedAt());
        holder.ivImage.setImageResource(android.R.color.transparent);
        Glide.with(getContext()).load(tweet.getUser().getProfile_image_url()).centerCrop().into(holder.ivImage);

        return convertView;
    }
    static class ViewHolder {
        @Bind(R.id.tvUserName) TextView tvUserName;
        @Bind(R.id.tvBody) TextView tvBody;
        @Bind(R.id.ivImage) ImageView ivImage;
        @Bind(R.id.tvCreatedAt) TextView tvCreatedAt;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }*/
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // Pass in the Article array into the constructor
    public TweetsArrayAdapter(Context context, List<Tweet> Articles) {
        mTweets = Articles;
        mContext = context;
    }

    public void addAll(ArrayList<Tweet> tweets) {
        mTweets.addAll(tweets);
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @Bind(R.id.tvUserName) TextView tvUserName;
        @Bind(R.id.tvBody) TextView tvBody;
        @Bind(R.id.ivImage) ImageView ivImage;
        @Bind(R.id.tvCreatedAt) TextView tvCreatedAt;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ArticleView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(ArticleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvUserName.setText("");
        Tweet tweet = mTweets.get(position);
        viewHolder.tvBody.setText(tweet.getText());
        viewHolder.tvCreatedAt.setText(tweet.getCreatedAt());
        viewHolder.ivImage.setImageResource(android.R.color.transparent);
        if (tweet.getUser() != null){
            viewHolder.tvUserName.setText(tweet.getUser().getScreen_name());
            Glide.with(mContext).load(tweet.getUser().getProfile_image_url()).centerCrop().into(viewHolder.ivImage);
        }
    }
    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
