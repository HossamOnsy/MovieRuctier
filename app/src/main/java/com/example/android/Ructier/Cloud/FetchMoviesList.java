package com.example.android.Ructier.Cloud;

import android.os.AsyncTask;

import com.example.android.Ructier.JsonParsing.JsonParser;
import com.example.android.Ructier.StaticVar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class FetchMoviesList extends AsyncTask<String, Void, String> {
    StaticVar staticVar =new StaticVar();

private FetchMoviesListListener fetchMoviesListListener;


        JsonParser parser = new JsonParser();

public void setTaskListener(FetchMoviesListListener fetchMoviesListListener) {
    this.fetchMoviesListListener = fetchMoviesListListener;
}
protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr = null;

        try {

   URL url = new URL(staticVar.BASE_URI+params[0]+staticVar.Fetch_S+staticVar.API_KEY);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {

        return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {

        buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {

        return null;
        }
        forecastJsonStr = buffer.toString();

        return forecastJsonStr;
        } catch (IOException e) {

        return null;
        } finally {
        if (urlConnection != null) {
        urlConnection.disconnect();
        }
        if (reader != null) {
                try {
                        reader.close();
                } catch (final IOException e) {
                }
        }
        }
        }

@Override
protected void onPostExecute(String s) {

        super.onPostExecute(s);
        try {
        parser.JsonData(s);
            fetchMoviesListListener.OnTaskFinish(parser.JsonData(s));
        }
        catch (JSONException e) {
        e.printStackTrace();
        }
        }
}
