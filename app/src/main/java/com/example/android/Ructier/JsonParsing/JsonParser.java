package com.example.android.Ructier.JsonParsing;


import com.example.android.Ructier.MovieEntity;
import com.example.android.Ructier.ReviewDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class JsonParser {
   public ArrayList<MovieEntity>  JsonData(String string)
            throws JSONException {


           final String J_POSTER = "poster_path";
           final String J_TITLE = "title";
           final String J_PHOTO = "backdrop_path";
           final String J_DATE = "release_date";
           final String J_OVERVIEW = "overview";
           final String J_RATE = "vote_average";
           final String J_ID = "id";
           final String results = "results";
           String Overview;
           String Title;
           String BackDropPhoto;
           String PosterPathPhoto;
           String Date;
           String id;


           ArrayList<MovieEntity> MoviesList = new ArrayList<MovieEntity>();
       if (string!=null) {
           JSONObject jsonObject = new JSONObject(string);
           JSONArray movieArray = jsonObject.getJSONArray(results);

           for (int i = 0; i < movieArray.length(); i++) {

               MovieEntity moviedata = new MovieEntity();
               JSONObject jsonObject1 = movieArray.getJSONObject(i);
               BackDropPhoto = FormatBackDropImage(jsonObject1.getString(J_PHOTO));
               PosterPathPhoto = FormatPosterImage(jsonObject1.getString(J_POSTER));
               Title = jsonObject1.getString(J_TITLE);
               Date = jsonObject1.getString(J_DATE);
               Overview = jsonObject1.getString(J_OVERVIEW);
               id = jsonObject1.getString(J_ID);


               moviedata.setTitle(Title);
               moviedata.setDate(Date);
               moviedata.setOverview(Overview);
               moviedata.setID(id);
               moviedata.setPosterPath(PosterPathPhoto);
               moviedata.setPicture(BackDropPhoto);
               MoviesList.add(moviedata);
           }


       }

       return MoviesList;
   }
    public String FormatPosterImage(String imageUrl) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String IMG_SIZE = "/w342";
        return BASE_URL + IMG_SIZE + imageUrl;
    }
    public String FormatBackDropImage(String imageUrl) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String IMG_SIZE = "w780/";
        return BASE_URL + IMG_SIZE + imageUrl;
    }

    public String JsonTrailer(String string)
            throws JSONException{
        try {


                JSONObject jsonObject = new JSONObject(string);
                //Here we get the instance of JSONArray that contains the JSONObjects
                JSONArray jsonMoviesArray = jsonObject.optJSONArray("results");
                String code = "";
                for (int i = 0; i < jsonMoviesArray.length(); i++) {
                    JSONObject jsonObject1 = jsonMoviesArray.getJSONObject(i);
                    String type = jsonObject1.optString("type");

                    if (type.equalsIgnoreCase("trailer"))
                        code = jsonObject1.optString("key");
                }
                return code;
            }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    public ArrayList<ReviewDetails> JsonReview(String s)
            throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonMoviesArray = jsonObject.getJSONArray("results");

            ArrayList<ReviewDetails> List = new ArrayList<>();
            for (int i = 0; i < jsonMoviesArray.length(); i++) {
                ReviewDetails reviews= new ReviewDetails();
                JSONObject jsonObject1 = jsonMoviesArray.getJSONObject(i);
                String author = jsonObject1.getString("author");

                String content = jsonObject1.getString("content");
                String url = jsonObject1.getString("url");
                reviews.setAuthor(author);
                reviews.setUrl(url);
                reviews.setContent(content);
                List.add(reviews);



            }
            return List;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
