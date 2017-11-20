package com.estagio3.dog_care;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by matheus on 19/11/2017.
 */

@IgnoreExtraProperties
public class Track {
    private String id;
    private String trackName;

    public Track() {

    }

    public Track(String id, String trackName) {
        this.trackName = trackName;
        this.id = id;
//        this.rating=rating;
    }

    public String getTrackName() {
        return trackName;
    }

}