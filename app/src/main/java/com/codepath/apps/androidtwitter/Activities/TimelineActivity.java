package com.codepath.apps.androidtwitter.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.androidtwitter.Adapters.TweetsArrayAdapter;
import com.codepath.apps.androidtwitter.Dialogs.ComposeFragment;
import com.codepath.apps.androidtwitter.Helpers.EndlessRecyclerViewScrollListener;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.TwitterApplication;
import com.codepath.apps.androidtwitter.TwitterClient;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity implements ComposeFragment.OnFragmentInteractionListener {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;

    @Bind(R.id.rvTweets) RecyclerView rvTweets;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        rvTweets.setAdapter(aTweets);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
//                fetchMoreTweets(page);
                Tweet lastTweet = tweets.get(tweets.size()-1);

                populateTimeline(lastTweet.getUid(), 25);
            }
        });

        client = TwitterApplication.getRestClient();//singleton client
        SharedPreferences preferences = this.getSharedPreferences("Tweets", Context.MODE_PRIVATE);
        String storedTimeline = preferences.getString("hometimeline", "");
        if (storedTimeline.length() <= 0) {
            populateTimeline(-1, 25);
        } else {
            mReadDataFromPreferences(storedTimeline);
        }
    }

    private void fetchMoreTweets(int page) {
    }

    private void mReadDataFromPreferences(String hometimeline) {
        JSONArray newJArray = null;
        try {
            newJArray = new JSONArray(hometimeline);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        aTweets.addAll(Tweet.fromJsonArray(newJArray));
        aTweets.notifyDataSetChanged();
    }

    private void populateTimeline(long max_id, int count) {
        client.getHomeTimeline(max_id, count, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mCreateAndSaveFile("offlineFile", response.toString());
                aTweets.addAll(Tweet.fromJsonArray(response));
                aTweets.notifyDataSetChanged();
                Log.d("DEBUG", aTweets.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG", "reached here ");
                Log.d("DEBUG", errorResponse.toString());

            }
        });
    }

    public void mCreateAndSaveFile(String params, String mJsonResponse) {
        SharedPreferences preferences = this.getSharedPreferences("Tweets", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("hometimeline", mJsonResponse);
        Log.d("DEBUG", preferences.getString("hometimeline", ""));
    }

    public void mReadJsonData(String params) {
        try {
            File f = new File("/data/data/" + getPackageName() + "/" + params);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            JSONArray newJArray = new JSONArray(mResponse);
            aTweets.addAll(Tweet.fromJsonArray(newJArray));
            aTweets.notifyDataSetChanged();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_compose);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return super.onCreateOptionsMenu(menu);
    }
    public void onClickCompose(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        ComposeFragment filtersFragment = ComposeFragment.newInstance();
        filtersFragment.show(fm, "fragment_compose");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void processNewTweetFromFragment(Tweet tweet) {
        tweets.add(0, tweet);
        aTweets.notifyDataSetChanged();
    }
}
