package com.example.android.Ructier.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.Ructier.Fragment.DetailsFragment;
import com.example.android.Ructier.R;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class DetailsActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_details);
        if (null==savedInstanceState)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.Detalis_frame_layout, new DetailsFragment()).commit();
        }
    }
}
