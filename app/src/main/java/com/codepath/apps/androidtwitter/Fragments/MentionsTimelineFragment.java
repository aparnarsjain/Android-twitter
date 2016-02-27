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
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(-1, 25);
    }

    private void populateTimeline(long max_id, int count) {
        client.getMentionsTimeline(max_id, count, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                addAll(Tweet.fromJsonArray(response));
//                Gson gson = new GsonBuilder().create();
//                Type listType = new TypeToken<ArrayList<Tweet>>(){}.getType();
//                ArrayList<Tweet> tweets = gson.fromJson(response.toString(),listType);
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
