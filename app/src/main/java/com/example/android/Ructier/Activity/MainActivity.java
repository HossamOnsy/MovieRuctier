package com.example.android.Ructier.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.android.Ructier.Fragment.DetailsFragment;
import com.example.android.Ructier.Fragment.MainFragment;
import com.example.android.Ructier.MovieEntity;
import com.example.android.Ructier.MoviesListener;
import com.example.android.Ructier.R;
import com.example.android.Ructier.StaticVar;

public class MainActivity extends AppCompatActivity implements MoviesListener {

    Boolean b;
    StaticVar staticVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staticVar =new StaticVar();
        FrameLayout f= (FrameLayout) findViewById(R.id.f2);
        if (null==f) {b=false;} else {b=true;}


        if (null==savedInstanceState)
        {MainFragment mainFragment=new MainFragment();
            mainFragment.SetMovieListener(this);


           getSupportFragmentManager().beginTransaction().replace(R.id.f1, mainFragment).commit();
        }
    }

    @Override
    public void Set_SelectedMovie(MovieEntity movieDetails) {


        if(b)
        {

            DetailsFragment DF=new DetailsFragment();
            Bundle extra=new Bundle();
            extra.putSerializable(staticVar.Extra, movieDetails);
            DF.setArguments(extra);
            getSupportFragmentManager().beginTransaction().replace(R.id.f2,DF ).commit();

        }
        else { // send intent to the details activity with the movieDetails
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra(staticVar.Extra, movieDetails);
            startActivity(i);

        }

    }


}
