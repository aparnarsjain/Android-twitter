package com.codepath.apps.androidtwitter.Dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.apps.androidtwitter.Activities.TimelineActivity;
import com.codepath.apps.androidtwitter.R;
import com.codepath.apps.androidtwitter.TwitterApplication;
import com.codepath.apps.androidtwitter.TwitterClient;
import com.codepath.apps.androidtwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aparna on 2/19/16.
 */
public class ComposeFragment extends DialogFragment implements View.OnClickListener {
    private TwitterClient client;

    @Bind(R.id.editText) EditText etCompose;
    @Bind(R.id.btnTweet) Button btnTweet;
    @Bind(R.id.ivUserImage) ImageView userImage;


    private OnFragmentInteractionListener mListener;

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
//        final User currUser = client.getCurrentUser();
//        Glide.with(getContext()).load(currUser.getProfileImageUrl()).centerCrop().into(userImage);
        final String newTweetText = etCompose.getText().toString();
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.postTweetOrReply(newTweetText, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        //maybe add to the adapter
                        Log.d("DEBUG", "success: post tweet " + response.toString());
                        Tweet justComposed = new Tweet();
                        justComposed.setBody(newTweetText);
//                        justComposed.setUser(currUser);
                        TimelineActivity tlActivity = (TimelineActivity)getActivity();
                        tlActivity.processNewTweetFromFragment(justComposed);

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

