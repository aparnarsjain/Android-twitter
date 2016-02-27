package com.codepath.apps.androidtwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.androidtwitter.TwitterApplication;
import com.codepath.apps.androidtwitter.TwitterClient;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * Created by aparna on 2/25/16.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(-1, 25);
    }
    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment fragmentUserTimeline = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        fragmentUserTimeline.setArguments(args);
        return fragmentUserTimeline;
    }

    private void populateTimeline(long max_id, int count) {
        String screenName = getArguments().getString("screenName");
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                addAll(Tweet.fromJsonArray(response));
                Log.d("DEBUG", "success " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
