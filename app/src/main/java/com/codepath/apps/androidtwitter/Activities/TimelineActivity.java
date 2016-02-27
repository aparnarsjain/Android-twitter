package com.codepath.apps.androidtwitter.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.androidtwitter.Dialogs.ComposeFragment;
import com.codepath.apps.androidtwitter.Fragments.HomeTimelineFragment;
import com.codepath.apps.androidtwitter.Fragments.MentionsTimelineFragment;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.codepath.apps.restclienttemplate.ProfileActivity;

public class TimelineActivity extends AppCompatActivity implements ComposeFragment.ComposeFragmentListener {
//    @Bind(R.id.viewpager) ViewPager vpPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //get the viewpager
//        ButterKnife.bind(this);
        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        //set the viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        //find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        //attach the tabstrip to the view pager
        tabStrip.setViewPager(vpPager);


    }
    public void onProfileView(MenuItem mi) {
         //launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);

    }
    public void mCreateAndSaveFile(String params, String mJsonResponse) {
        SharedPreferences preferences = this.getSharedPreferences("Tweets", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("hometimeline", mJsonResponse);
        Log.d("DEBUG", preferences.getString("hometimeline", ""));
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
    public void onFinishEditDialog(Tweet justComposed) {
//        tweets.add(0, justComposed);
//        aTweets.notifyDataSetChanged();
    }

    public static void main(String[] args) {

    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = {"Home" , "Mentions"};
//        private String tabTitles[] = {"Home"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new HomeTimelineFragment();
                case 1:
                    return new MentionsTimelineFragment();
                default:
                    return new HomeTimelineFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];

        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
