package com.example.android.Ructier.Cloud;


import com.example.android.Ructier.MovieEntity;

import java.util.ArrayList;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public interface FetchMoviesListListener {

    public void OnTaskFinish(ArrayList<MovieEntity> arrayList) ;


}
