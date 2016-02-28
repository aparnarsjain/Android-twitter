package com.codepath.apps.androidtwitter.Dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.androidtwitter.Activities.TimelineActivity;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.TwitterApplication;
import com.codepath.apps.androidtwitter.TwitterClient;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.codepath.apps.androidtwitter.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aparna on 2/19/16.
 */
public class ComposeFragment extends DialogFragment {
    private TwitterClient client;

    @Bind(R.id.editText) EditText etCompose;
    @Bind(R.id.btnTweet) Button btnTweet;
    @Bind(R.id.ivUserImage) ImageView userImage;
    @Bind(R.id.tvCharCount) TextView tvCharCount;

    ComposeFragmentListener listener;


    // 1. Defines the listener interface with a method passing back data result.
    public interface ComposeFragmentListener {
        void onFinishEditDialog(Tweet justComposed);
    }


    public ComposeFragment() {
        // Required empty public constructor
    }

    public static ComposeFragment newInstance() {
        ComposeFragment fragment = new ComposeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_compose, container, false);
        ButterKnife.bind(this, view);
        final User currUser = client.getCurrentUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
//                User thisUser = User.fromJsonObject(response);
                Gson gson = new GsonBuilder().create();
                User thisUser = gson.fromJson(response.toString(), User.class);
                Glide.with(getContext()).load(thisUser.getProfileImageUrl()).centerCrop().into(userImage);


            }
        });
        etCompose.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCharCount.setText(Integer.toString(140-s.length()));
                if(s.length() > 140) {
                    tvCharCount.setTextColor(ContextCompat.getColor(getContext(), R.color.Red));

                }else {
                    tvCharCount.setTextColor(ContextCompat.getColor(getContext(), R.color.Green));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.postTweetOrReply(etCompose.getText().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        //maybe add to the adapter
                        Log.d("DEBUG", "success: post tweet " + response.toString());
//                        Tweet justComposed = Tweet.fromJSon(response);
                        Gson gson = new GsonBuilder().create();
                        // Define Response class to correspond to the JSON response returned
                        Tweet justComposed = gson.fromJson(response.toString(), Tweet.class);

                        listener.onFinishEditDialog(justComposed);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("DEBUG", "failed to post tweet " + responseString);
                    }
                });
                dismiss();
            }
        });

//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ComposeFragmentListener)getActivity();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        TimelineActivity searchActivity = (TimelineActivity) getActivity();
        //reload the tweets feed
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

