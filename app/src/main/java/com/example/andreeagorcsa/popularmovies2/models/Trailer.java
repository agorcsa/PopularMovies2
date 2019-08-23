package com.example.andreeagorcsa.popularmovies2.models;

import org.parceler.Parcel;

@Parcel
public class Trailer {
    // Declaration of the trailer constants
    public String key;
    public String name;

    // Empty constructor for Parcel
    public Trailer() {}


    /**
     * Trailer constructor
     * @param key
     * @param name
     */
    public Trailer( String key, String name) {
        this.key = key;
        this.name = name;
    }

    // Getter and Setter methods for the Trailer parameters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
