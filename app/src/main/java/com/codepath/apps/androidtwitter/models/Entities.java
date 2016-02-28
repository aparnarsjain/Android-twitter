package com.codepath.apps.androidtwitter.models;

/**
 * Created by aparna on 2/28/16.
 *
 */

import org.json.JSONObject;

import java.util.List;



public class Entities {
    public class Media {
        public String url;
        public String expanded_url;
        public String display_url;

        public String getUrl() {
            return url;
        }

        public String getExpanded_url() {
            return expanded_url;
        }

        public String getDisplay_url() {
            return display_url;
        }
    }
    public List<Media> urls;

    public List<Media> getUrls() {
        return urls;
    }

    public static Entities fromJsonObject(JSONObject json){
        Entities entities = new Entities();
//        try {
//            entities.urls = json.getJSONArray("urls");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return entities;
    }
}
