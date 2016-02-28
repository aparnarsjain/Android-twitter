package com.codepath.apps.androidtwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.androidtwitter.Helpers.EndlessRecyclerViewScrollListener;
import com.codepath.apps.androidtwitter.TwitterApplication;
import com.codepath.apps.androidtwitter.TwitterClient;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aparna on 2/25/16.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(-1, 25);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                List<Tweet> tweets = aTweets.getTweets();
                Tweet lastTweet = tweets.get(tweets.size() - 1);
                populateTimeline(lastTweet.getId(), 25);
            }
        });
        return v;
    }

    private void populateTimeline(double max_id, int count) {
        client.getMentionsTimeline(max_id, count, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Type collectionType = new TypeToken<ArrayList<Tweet>>() {
                }.getType();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat(Tweet.DATE_FORMAT);
                Gson gson = gsonBuilder.create();
                ArrayList<Tweet> tweets = gson.fromJson(response.toString(), collectionType);
                addAll(tweets);
//                addAll(Tweet.fromJsonArray(response));
                Log.d("DEBUG", "success getMentionsTimeline " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
