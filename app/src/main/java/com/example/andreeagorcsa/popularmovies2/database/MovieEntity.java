package com.example.andreeagorcsa.popularmovies2.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MOVIE_TABLE")
// at compile creates an SQLite table for this object
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "MOVIE_ID")
    private int movie_id;
    @ColumnInfo(name = "ORIGINAL_TITLE")
    private String original_title;
    @ColumnInfo(name = "MOVIE_POSTER")
    private String poster;
    @ColumnInfo(name = "FINAL_URL")
    private String url;
    @ColumnInfo(name = "PLOT_SYNOPSIS")
    private String synopsis;
    @ColumnInfo(name = "USER_RATING")
    private double user_rating;
    @ColumnInfo(name = "POPULARITY")
    private double popularity;
    @ColumnInfo(name = "RELEASE_DATE")
    private String release_date;
    @ColumnInfo(name = "IS_FAVORITE")
    private int is_favorite;

    public MovieEntity(int movie_id, String original_title, String poster, String url, String synopsis, double user_rating, double popularity, String release_date, int is_favorite) {
        this.movie_id = movie_id;
        this.original_title = original_title;
        this.poster = poster;
        this.url = url;
        this.synopsis = synopsis;
        this.user_rating = user_rating;
        this.popularity = popularity;
        this.release_date = release_date;
        this.is_favorite = is_favorite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster() {
        return poster;
    }

    public String getUrl() {
        return url;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getUser_rating() {
        return user_rating;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int isIs_favorite() {
        return is_favorite;
    }
}
