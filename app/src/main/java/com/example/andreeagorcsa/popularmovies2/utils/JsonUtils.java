package com.example.andreeagorcsa.popularmovies2.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.andreeagorcsa.popularmovies2.ui.MainActivity;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.models.Review;
import com.example.andreeagorcsa.popularmovies2.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String LOG_TAG = JsonUtils.class.getName();

    // Declaration of the Movie Url constants
    public static final String BASE_URL = "https://api.themoviedb.org/3";
    public static final String QUERY_PARAM = "api_key";
    public static final String API_KEY = "ff509255d5c46038414ba35e03b99862";

    // Declaration of Json Movie constants
    public static final String RESULTS = "results";
    public static final String MOVIE_ID = "id";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_SIZE = "w185";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String POPULARITY = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_date";

    // Declaration of the Json Trailer constants
    public static final String TRAILER_KEY = "key";
    public static final String TRAILER_NAME = "name";

    // Declaration of Json Review constants
    public static final String REVIEW_AUTHOR = "author";
    public static final String REVIEW_CONTENT = "content";

    /**
     * Builds the Json String URL, according the given sortType
     *
     * @param sortType (popular or top_rated)
     * @return String URL (parameter for the createUrl method)
     * @throws IOException
     */
    public static String buildUrl(String sortType) throws IOException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(sortType)
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();
        String url = builder.build().toString();
        return url;
    }

    /**
     * Builds the Json String Trailer Url, according to the movie id
     *
     * @param id, which represents the Movie id
     * @return String trailerUrl
     * @throws IOException
     */
    public static String buildTrailerUrl(String id) throws IOException {
        Uri.Builder trailerBuilder = new Uri.Builder();
        trailerBuilder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id)
                .appendPath("videos")
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();
        String trailerUrl = trailerBuilder.build().toString();
        return trailerUrl;
    }

    /**
     * Builds the Json String Review Url, according to the movie id
     *
     * @param id, which represents the Movie id
     * @return reviewUrl
     * @throws IOException
     */
    public static String buildReviewUrl(String id) throws IOException {
        Uri.Builder reviewBuilder = new Uri.Builder();
        reviewBuilder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id)
                .appendPath("reviews")
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();
        String reviewUrl = reviewBuilder.build().toString();
        return reviewUrl;
    }

    /**
     * Creates an URL Object from the String URL
     *
     * @param stringUrl
     * @return url Object
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(MainActivity.LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Makes an HTTP request to the given URL Object
     *
     * @param url Object
     * @return String jsonResponse
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(MainActivity.LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(MainActivity.LOG_TAG, "Problem retrieving the movies JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Converts the {@link InputStream} into a String which contains the whole JSON response from the server
     *
     * @param inputStream
     * @return String output
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Parses the json, creates the Movie Object, adds each movie object to the list
     *
     * @param json String
     * @return movieList
     */

    public static List<Movie> parseMovieJson(String json) {

        // Checking if the json is empty
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        List<Movie> movieList = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(json);
            JSONArray resultsArray = rootObject.optJSONArray(RESULTS);

            // Looping in the resultsArray
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieObject = resultsArray.optJSONObject(i);

                int movieId = movieObject.optInt(MOVIE_ID);

                String originalTitle = movieObject.optString(ORIGINAL_TITLE);

                String posterPath = movieObject.optString(POSTER_PATH);

                String moviePoster = POSTER_BASE_URL + POSTER_SIZE + posterPath;

                String overview = movieObject.optString(OVERVIEW);

                double voteAverage = movieObject.optDouble(VOTE_AVERAGE);

                double popularity = movieObject.optDouble(POPULARITY);

                String releaseDate = movieObject.optString(RELEASE_DATE);

                // TO DO: not sure is it is correct
                boolean isFavorite = false;

                Movie movie = new Movie(movieId, originalTitle, moviePoster, overview, voteAverage, popularity, releaseDate, isFavorite);
                movieList.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    /**
     * Parses the json, creates a a new Trailer object, adds each review object to the list
     * @param jsonTrailer
     * @return trailerList
     */
    public static List<Trailer> parseTrailerJson(String jsonTrailer) {

        //Checking if the json is empty
        if (TextUtils.isEmpty(jsonTrailer)) {
            return null;
        }

        List<Trailer> trailerList = new ArrayList<>();

        try {
            JSONObject rootTrailerObject = new JSONObject(jsonTrailer);
            JSONArray resultsTrailerArray = rootTrailerObject.optJSONArray(RESULTS);

            //Looping in the resultsArray
            for (int i = 0; i < resultsTrailerArray.length(); i++) {
                JSONObject trailerObject = resultsTrailerArray.optJSONObject(i);

                String key = trailerObject.optString(TRAILER_KEY);

                String name = trailerObject.optString(TRAILER_NAME);

                Trailer trailer = new Trailer(key, name);

                trailerList.add(trailer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerList;
    }

    /**
     * Parses the json, creates a a new Review object, adds each review object to the list
     * @param jsonReview
     * @return reviewList
     */
    public static List<Review> parseReviewJson(String jsonReview) {

        //Checking is the json is empty
        if(TextUtils.isEmpty(jsonReview)){
            return null;
        }

        List<Review> reviewList = new ArrayList<>();
        try {
            JSONObject rootReviewObject = new JSONObject(jsonReview);
            JSONArray resultsReviewsArray = rootReviewObject.optJSONArray(RESULTS);

            //Looping through the resultsArray
            for (int i = 0; i < resultsReviewsArray.length(); i++) {
                JSONObject reviewObject = resultsReviewsArray.optJSONObject(i);

                String author = reviewObject.optString(REVIEW_AUTHOR);
                String content = reviewObject.optString(REVIEW_CONTENT);

                Review review = new Review(author, content);
                reviewList.add(review);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    /**
     * Queries the themovieDB API
     *
     * @param requestUrl (return value of the buildUrl method)
     * @return a list of {@link Movie} objects
     */
    public static List<Movie> fetchMovieData(String requestUrl) {
        // Creates URL object
        URL url = createUrl(requestUrl);
        // Performs HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<Movie> movies = parseMovieJson(jsonResponse);
        // Returns the list of {@link Movies}
        return movies;
    }

    public static List<Review> fetchReviewData(String reviewUrl) {
        URL url = createUrl(reviewUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Review> reviews = parseReviewJson(jsonResponse);
        return reviews;
    }

    public static List<Trailer> fetchTrailerData(String trailerUrl) {
        URL url = createUrl(trailerUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Trailer> trailers = parseTrailerJson(jsonResponse);
        return trailers;
    }
}
