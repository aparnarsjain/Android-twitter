
package com.codepath.apps.androidtwitter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Sizes_ implements Serializable {

    @SerializedName("large")
    @Expose
    private Large_ large;
    @SerializedName("thumb")
    @Expose
    private Thumb_ thumb;
    @SerializedName("small")
    @Expose
    private Small_ small;
    @SerializedName("medium")
    @Expose
    private Medium___ medium;

    /**
     * 
     * @return
     *     The large
     */
    public Large_ getLarge() {
        return large;
    }

    /**
     * 
     * @param large
     *     The large
     */
    public void setLarge(Large_ large) {
        this.large = large;
    }

    /**
     * 
     * @return
     *     The thumb
     */
    public Thumb_ getThumb() {
        return thumb;
    }

    /**
     * 
     * @param thumb
     *     The thumb
     */
    public void setThumb(Thumb_ thumb) {
        this.thumb = thumb;
    }

    /**
     * 
     * @return
     *     The small
     */
    public Small_ getSmall() {
        return small;
    }

    /**
     * 
     * @param small
     *     The small
     */
    public void setSmall(Small_ small) {
        this.small = small;
    }

    /**
     * 
     * @return
     *     The medium
     */
    public Medium___ getMedium() {
        return medium;
    }

    /**
     * 
     * @param medium
     *     The medium
     */
    public void setMedium(Medium___ medium) {
        this.medium = medium;
    }

}
