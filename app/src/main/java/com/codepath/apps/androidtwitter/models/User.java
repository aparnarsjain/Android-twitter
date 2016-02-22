package com.codepath.apps.androidtwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

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
public class User {

    private String name;
    private long uid;
    private String screen_name;
    private String profile_image_url;

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

    public static User fromJsonObject(JSONObject json){
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screen_name = json.getString("screen_name");
            u.profile_image_url = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return u;
    }


}