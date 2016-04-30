package com.example.android.Ructier.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Ructier.JsonParsing.JsonParser;
import com.example.android.Ructier.MovieEntity;
import com.example.android.Ructier.R;
import com.example.android.Ructier.ReviewDetails;
import com.example.android.Ructier.StaticVar;
import com.example.android.Ructier.DataBase.FavoriteMovies;
import com.example.android.Ructier.adapters.ReviewAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class DetailsFragment extends Fragment {
    Button ReviewButton;
    Button TrailerButton;
    ListView listView;
    CheckBox checkbox;
    JsonParser jp = new JsonParser();
    MovieEntity moviedata;
    StaticVar staticVar;
    private String Trailer_uri;
    private ArrayList<ReviewDetails> Review_uri;




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.share_trailer)
        {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "From Popular Movies App" );
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Movie Name: "+moviedata.Title+"\nRelease Data: "+moviedata.getDate()+
                    "\nTrailer Link: "+staticVar.YOUTUBE_BASE+getTrailer_uri()+"\nDescription:\n"+moviedata.getOverview()+"\n");
            sendIntent.setType("text/plain");
            String title = "Share with…";
            startActivity(Intent.createChooser(sendIntent, title));
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);

        staticVar = new StaticVar();
        listView = (ListView) rootView.findViewById(R.id.review_list_view);
        ReviewButton = (Button) rootView.findViewById(R.id.review_button);
        TrailerButton = (Button) rootView.findViewById(R.id.trailer);
        checkbox = (CheckBox) rootView.findViewById(R.id.checkBox);

        try {
            moviedata = (MovieEntity) getArguments().getSerializable(staticVar.Extra);

        }
        catch (Exception e)
        {
            Intent intent = getActivity().getIntent();
            moviedata = (MovieEntity) intent.getSerializableExtra(staticVar.Extra);
        }



        ImageView BackDropImageView = (ImageView) rootView.findViewById(R.id.back_drop_path);

        if (moviedata.getPicture() != null) {
            Picasso.with(null).load(moviedata.getPicture()).into(BackDropImageView);
        } else {
            Picasso.with(null).load(moviedata.getPosterPath()).into(BackDropImageView);
        }


        TextView TitleView = (TextView) rootView.findViewById(R.id.movie_title);
        TitleView.setText(moviedata.getTitle());


        TextView Description = (TextView) rootView.findViewById(R.id.movie_overview);
        Description.setText(moviedata.getOverview());


        TextView ReleaseData = (TextView) rootView.findViewById(R.id.release_data);
        ReleaseData.setText(moviedata.getDate());


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    ShowReview();

                } catch (Exception d) {
                }

            }
        });
        TrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoutubeVideo();
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    FavoriteMovies favoriteMovies = new FavoriteMovies(getActivity());
                    favoriteMovies.OpenData();
                    long c = favoriteMovies.InsertFavoriteMovie(moviedata);
                    favoriteMovies.Close();
                    if (c == -1) {
                        Toast.makeText(getActivity(), "Already In Favorite List  ! ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Successfully added to Favorite List ! ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FetchTrailersData fetchTrailersData = new FetchTrailersData();
        fetchTrailersData.execute(moviedata.getID());
        FetchReviewData fetchReviewData = new FetchReviewData();
        fetchReviewData.execute(moviedata.getID());

    }
    public void YoutubeVideo() {

          try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + getTrailer_uri()));
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(staticVar.YOUTUBE_BASE+getTrailer_uri()));
                startActivity(intent);
            }

    }

    public class FetchReviewData extends AsyncTask<String, Void, ArrayList<ReviewDetails>> {

        protected ArrayList<ReviewDetails> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String ReviewJsonStr = null;

            try {

                URL url = new URL(staticVar.BASE_URI + params[0] + staticVar.REVIEW_S + staticVar.API_KEY);

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
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                ReviewJsonStr = buffer.toString();
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
            try {
                return jp.JsonReview(ReviewJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ReviewDetails> list) {

            super.onPostExecute(list);
            if (list != null) {
               setReview_uri(list);
            }

            else
            {
                staticVar.ToastMessage(getActivity());
            }

        }
    }

    public void ShowReview()
    {

        if (getReview_uri().size()==0)
        {
            Toast.makeText(getActivity(), "There is no Review found!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(), "Loading Review ...", Toast.LENGTH_SHORT).show();
        ReviewAdapter reviewAdapter = new ReviewAdapter(getContext(), getReview_uri());
        listView.setAdapter(reviewAdapter);
        ReviewButton.setVisibility(View.GONE);}
    }

    public class FetchTrailersData extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String TrailerJsonStr = null;

            try {

                URL url = new URL(staticVar.BASE_URI + params[0] + staticVar.TRAILER_S + staticVar.API_KEY);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // if the stream is empty then it's done
                    return null;
                }
                TrailerJsonStr = buffer.toString();
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
            try {
                return jp.JsonTrailer(TrailerJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String list) {
            if (list != null) {
                setTrailer_uri(list);
                super.onPostExecute(list);
            } else {
                staticVar.ToastMessage(getActivity());
            }


        }
    }

    public String getTrailer_uri() {
        return Trailer_uri;
    }

    public void setTrailer_uri(String trailer_uri) {
        Trailer_uri = trailer_uri;
    }
    public ArrayList<ReviewDetails> getReview_uri() {
        return Review_uri;
    }

    public void setReview_uri(ArrayList<ReviewDetails> review_uri) {
        Review_uri=review_uri;
    }


}