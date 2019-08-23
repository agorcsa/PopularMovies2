package com.example.andreeagorcsa.popularmovies2.models;

import org.parceler.Parcel;

@Parcel
public class Review {

    public String author;
    public String content;

    // Empty constructor for Parcel
    public Review() {

    }

    /**
     * Review constructor
     * @param author
     * @param content
     */
    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    // Getter and Setter methods for the Review parameters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
