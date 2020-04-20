package com.example.andreeagorcsa.popularmovies2.models;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.andreeagorcsa.popularmovies2.utils.JsonUtils;

import org.parceler.Parcel;

@Entity(tableName = "MOVIE_TABLE")
// at compile Room creates an SQLite table for this object

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

    // specifies the name of the column in the table
    @PrimaryKey
    @ColumnInfo(name = "MOVIE_ID")
    public int movieId;

    @ColumnInfo(name = "ORIGINAL_TITLE")
    public String originalTitle;

    @ColumnInfo(name = "MOVIE_POSTER")
    public String moviePoster;

    @ColumnInfo(name = "FINAL_URL")
    public String finalUrl;

    @ColumnInfo(name = "PLOT_SYNOPSIS")
    public String plotSynopsis;

    @ColumnInfo(name = "USER_RATING")
    public double userRating;

    @NonNull
    @ColumnInfo(name = "POPULARITY")
    public Double popularity;

    @ColumnInfo(name = "RELEASE_DATE")
    public String releaseDate;

    @ColumnInfo(name = "IS_FAVORITE")
    public boolean isFavorite;

    @Ignore
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
     * @param isFavorite
     */
    public Movie(int movieId, String originalTitle, String moviePoster, String plotSynopsis, double userRating, double popularity, String releaseDate, boolean isFavorite) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.moviePoster = moviePoster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.isFavorite = isFavorite;
    }

    // reads from parcel
    @Ignore
    protected Movie(android.os.Parcel in) {
        movieId = in.readInt();
        originalTitle = in.readString();
        moviePoster = in.readString();
        finalUrl = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readDouble();
        popularity = in.readDouble();
        releaseDate = in.readString();
        // we will read an int and transform it into a boolean
        isFavorite = in.readInt() == 1;
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
        return userRating;
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

    public boolean getIsFavorite() { return isFavorite;}

    public void setIsFavorite(boolean favorite) { isFavorite = favorite;}

    public double getPopularity() { return popularity; }

    public void setPopularity(Double popularity) { this.popularity = popularity;}


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
        // we write a boolean and transform it into an int
        // 1 -> true
        // 0 -> false
        dest.writeInt(isFavorite ? 1 : 0);
    }
}

