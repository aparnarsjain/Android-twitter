package com.codepath.apps.androidtwitter;

import android.content.Context;

import com.codepath.apps.androidtwitter.models.User;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "CPok8vyQQjdo4Dt3fUPfJqA8l";       // Change this
	public static final String REST_CONSUMER_SECRET = "bc5PQ8bTRKqmij3hcfEQXfbbddJYstZISui9Y62o5kCAtuZADZ"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)
	private static  User currentUser;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	public void getHomeTimeline(double max_id, int count, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", count);
		if (max_id > 0){
			params.put("max_id", max_id);
		}
		params.put("since_id", 1);
		client.get(apiUrl, params, handler);
//		getClient().get(apiUrl, params, handler); //-- which one to use?
	}
	public void postTweetOrReply(String text, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status",text);
		getClient().post(apiUrl, params, handler);
	}
	public User getCurrentUser(JsonHttpResponseHandler handler) {
		if (currentUser == null) {
			String apiUrl = getApiUrl("account/verify_credentials.json");
			client.get(apiUrl, handler);
		}
		return currentUser;
	}

	public void getMentionsTimeline(double max_id, int count, JsonHttpResponseHandler jsonHttpResponseHandler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", count);
		if (max_id > 0){
			params.put("max_id", max_id);
		}
		client.get(apiUrl, params, jsonHttpResponseHandler);
	}
	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("screen_name", screenName);
		client.get(apiUrl, params, handler);
	}
}