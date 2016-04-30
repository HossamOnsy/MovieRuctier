package com.example.android.Ructier.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.Ructier.MovieEntity;

import java.util.ArrayList;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */

public class CashedMovies {

    static final String    DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "movie";
    //---------------------------------------------
    public static final String ID ="ID";
    public static final String TITLE = "title";
    public static final String POSTER_IMAGE = "poster_image";
    public static final String PICTURE = "back_drop";
    public static final String OVERVIEW = "overview";
    public static final String RATE = "rating";
    public static final String DATE = "date";
    private final Context context;

    SQLiteDatabase sqLiteDatabase;
    Helper helper;



    public CashedMovies
            (Context c)
    {
        this.context=c;
    }

    public  class Helper extends SQLiteOpenHelper
    {

        public Helper(Context context)
        {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }


        public void onCreate(SQLiteDatabase db)
        {
            final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TITLE + " TEXT NOT NULL, " +
                    POSTER_IMAGE + " TEXT, " +
                    PICTURE + " TEXT, " +
                    OVERVIEW + " TEXT, " +
                    RATE + " TEXT, " +
                    DATE + " TEXT);";

            db.execSQL(SQL_CREATE_MOVIE_TABLE);

        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

        {
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
            onCreate(db);

        }
    }


    public void openData()
    {
        Helper DataBaseHelper=new Helper(context);
        sqLiteDatabase=DataBaseHelper.getWritableDatabase();

    }
    public void close()

    {
        sqLiteDatabase.close();
    }



    public  void CashedData(MovieEntity modelMovies)
    {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, modelMovies.getTitle());
        cv.put(DATE, modelMovies.getDate());
        cv.put(POSTER_IMAGE, modelMovies.getPosterPath());
        cv.put(PICTURE, modelMovies.getPicture());
        cv.put(OVERVIEW, modelMovies.getOverview());
        cv.put(ID, modelMovies.getID());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }
    public ArrayList<MovieEntity> getchashedData()
    {
        ArrayList<MovieEntity>a=new ArrayList<>();
        String [] coulmns={ID,DATE,POSTER_IMAGE,PICTURE,OVERVIEW,RATE,TITLE};
        Cursor c=sqLiteDatabase.query(TABLE_NAME,coulmns,null,null,null,null,null,null);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            MovieEntity Movies=new MovieEntity();
            Movies.setTitle(c.getString(c.getColumnIndex(TITLE)));
            Movies.setDate( c.getString(c.getColumnIndex(DATE)));
            Movies.setPosterPath( c.getString(c.getColumnIndex(POSTER_IMAGE)));
            Movies.setPicture(c.getString(c.getColumnIndex(PICTURE)));
            Movies.setOverview(c.getString(c.getColumnIndex(OVERVIEW)));
            Movies.setID(c.getString(c.getColumnIndex(ID)));
            a.add(Movies);
        }
        return a;
    }

}
