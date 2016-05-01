package com.example.android.Ructier;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class StaticVar {
    public static final  String API_KEY="92268add4b3a98b593e13325b061f67b";      // Not for personal use Api_key and disclaimer i will not be responsible for anyone using this api key
    public static final  String Popular="popular";
    public static  final String Favourite_Message="Favorite List is Empty";
    public static final  String Top_Rated="top_rated";
    public static final  String Extra="DETAILS";
    public static final  String BASE_URI="http://api.themoviedb.org/3/movie/";
    public static final  String REVIEW_S="/reviews?api_key=";
    public static final  String TRAILER_S="/videos?api_key=";
    public static final  String Fetch_S="?api_key=";
    public static final  String YOUTUBE_BASE="http://www.youtube.com/watch?v=";

    public static final void ToastMessage(Activity activity) // to check for Connection
    {
        Toast.makeText(activity, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
}
