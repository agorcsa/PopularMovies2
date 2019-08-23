package com.example.andreeagorcsa.popularmovies2.models;

import android.os.Parcelable;

import com.example.andreeagorcsa.popularmovies2.utils.JsonUtils;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;


@Parcel
public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(android.os.Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    // Declaration of the Movie variables
    public int movieId;
    public String originalTitle;
    public String moviePoster;
    public String finalUrl;
    public String plotSynopsis;
    public double userRating;
    public double popularity;
    public String releaseDate;

    // Empty constructor for Parcel
    public Movie() {
    }

    /**
     * Movie constructor
     *
     * @param originalTitle
     * @param moviePoster
     * @param plotSynopsis
     * @param userRating
     * @param releaseDate
     */
    public Movie(int movieId, String originalTitle, String moviePoster, String plotSynopsis, double userRating, double popularity, String releaseDate) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.moviePoster = moviePoster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
    }

    // reads from parcel
    protected Movie(android.os.Parcel in) {
        movieId = in.readInt();
        originalTitle = in.readString();
        moviePoster = in.readString();
        finalUrl = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readDouble();
        popularity = in.readDouble();
        releaseDate = in.readString();
    }

    /**
     * Creates the Sring URL for the movie poster
     *
     * @return finalUrl
     */
    public String createFinalMoviePosterUrl() {
        finalUrl = JsonUtils.BASE_URL.concat(JsonUtils.POSTER_SIZE).concat(JsonUtils.POSTER_PATH);
        return finalUrl;
    }

    // Getter and Setter methods for the Movie parameters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return moviePoster;
    }

    public void setPosterPoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getOverview() {
        return plotSynopsis;
    }

    public void setOverview(String overview) {
        this.plotSynopsis = plotSynopsis;
    }

    public double getVoteAverage() {
        return  userRating;
    }

    public void setVoteAverage(double voteAverage) {
        this.userRating = voteAverage;
    }

    public String getReleaseDate() {
        return "Release Date: " + releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //// writes to parcel
    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(originalTitle);
        dest.writeString(moviePoster);
        dest.writeString(finalUrl);
        dest.writeString(plotSynopsis);
        dest.writeDouble(userRating);
        dest.writeDouble(popularity);
        dest.writeString(releaseDate);
    }
}

