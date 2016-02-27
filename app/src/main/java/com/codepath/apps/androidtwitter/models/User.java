package com.codepath.apps.androidtwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * "user": {
 "name": "OAuth Dancer",
 "profile_sidebar_fill_color": "DDEEF6",
 "profile_background_tile": true,
 "profile_sidebar_border_color": "C0DEED",
 "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 "created_at": "Wed Mar 03 19:37:35 +0000 2010",
 "location": "San Francisco, CA",
 "follow_request_sent": false,
 "id_str": "119476949",
 "is_translator": false,
 "profile_link_color": "0084B4",
 }
 */
public class User implements Serializable {

    private String name;
    private long uid;
    private String screen_name;
    private String profile_image_url;
    private String tag_line;
    private long followers_count;
    private long following_count;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }


    public String getTag_line() {
        return tag_line;
    }

    public static User fromJsonObject(JSONObject json){
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screen_name = json.getString("screen_name");
            u.profile_image_url = json.getString("profile_image_url");
            u.tag_line = json.isNull("description") ? null : json.getString("description");
//            u.followers_count = json.isNull("followers_count") ? null : json.getLong("followers_count");
//            u.following_count = json.isNull("following_count") ? null : json.getLong("following_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return u;
    }


    public long getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(long following_count) {
        this.following_count = following_count;
    }

    public long getFollowers_count() {
        return followers_count;
    }
}
