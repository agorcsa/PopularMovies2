package com.example.andreeagorcsa.popularmovies2.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = "DatabaseHelper";

    public static final String DATABASE_NAME = "Movie.db";
    public static final int DATABASE_VERSION = 1;

    // movie table
    public static final String TABLE_NAME = "movie_table";
    // columns of the movie table
    public static final String MOVIE_ID = "MOVIE_ID";
    public static final String ORIGINAL_TITLE = "ORIGINAL_TITLE";
    public static final String POSTER = "POSTER";
    public static final String URL = "URL";
    public static final String PLOT_SYNOPSIS = "PLOT_SYNOPSIS";
    public static final String USER_RATING = "USER_RATING";
    public static final String POPULARITY = "POPULARITY";
    public static final String RELEASE_DATE = "RELEASE_DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(LOG_TAG, "error");
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "MOVIE_ID INTEGER, " +
                "ORIGINAL_TITLE TEXT," +
                "POSTER TEXT," +
                "URL TEXT," +
                "PLOT_SYNOPSIS TEXT," +
                "USER_RATING TEXT," +
                "POPULARITY REAL," +
                "RELEASE_DATE REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //if (DATABASE_VERSION == DATABASE_VERSION + 1)
        onCreate(db);
    }
}
