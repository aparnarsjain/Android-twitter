package com.codepath.apps.androidtwitter.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.androidtwitter.Adapters.TweetsArrayAdapter;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.models.Tweet;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by aparna on 2/23/16.
 */
public class TweetsListFragment extends Fragment {
    TweetsArrayAdapter aTweets;
    RecyclerView rvTweets;
//    @Bind(R.id.rvTweets) RecyclerView rvTweets;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        ButterKnife.bind(this, v);
        rvTweets = (RecyclerView)v.findViewById(R.id.rvTweets);
        rvTweets.setAdapter(aTweets);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);
        //TODO: infinite pagination
//        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
////                fetchMoreTweets(page);
//                List<Tweet> tweets = aTweets.getTweets();
//                Tweet lastTweet = tweets.get(tweets.size()-1);
////                populateTimeline(lastTweet.getUid(), 25);
//            }
//        });
        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aTweets = new TweetsArrayAdapter(getActivity(), new ArrayList<Tweet>());

    }
    public void addAll(ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
        aTweets.notifyDataSetChanged();
    }
}
