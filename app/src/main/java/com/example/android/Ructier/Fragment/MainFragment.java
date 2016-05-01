
package com.example.android.Ructier.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.Ructier.Cloud.FetchMoviesList;
import com.example.android.Ructier.Cloud.FetchMoviesListListener;
import com.example.android.Ructier.MovieEntity;
import com.example.android.Ructier.MoviesListener;
import com.example.android.Ructier.R;
import com.example.android.Ructier.StaticVar;
import com.example.android.Ructier.DataBase.CashedMovies;
import com.example.android.Ructier.DataBase.FavoriteMovies;
import com.example.android.Ructier.adapters.GridViewAdapter;

import java.util.ArrayList;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class MainFragment extends Fragment {
    private FetchMoviesList fetchMoviesList;
    private GridViewAdapter adapter;
    private SwipeRefreshLayout Swipe;
    private GridView gridView;
    MoviesListener ml;
    StaticVar staticVar;
    ArrayList<MovieEntity> MovieArray;

    public ArrayList<MovieEntity> getArrayList() {
        return MovieArray;
    }

    public void setArrayList(ArrayList<MovieEntity> arrayList) {
        this.MovieArray = arrayList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        setHasOptionsMenu(true);


        gridView= (GridView) rootView.findViewById(R.id.gridView);
        staticVar =new StaticVar();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieEntity moviedata = (MovieEntity) adapter.getItem(position);
                ml.Set_SelectedMovie(moviedata);
            }
        });




        //Swipe refreshes main activity
        Swipe= (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SortMyMoviesList(staticVar.Popular);
                Swipe.setRefreshing(false);
            }
        });

        return rootView;
    }

    //Popular or Top Rated Pick up
         private void SortMyMoviesList(String SortType) {
           fetchMoviesList=new FetchMoviesList();
           fetchMoviesList.execute(SortType);
           fetchMoviesList.setTaskListener(new FetchMoviesListListener() {
            @Override
            public void OnTaskFinish(ArrayList<MovieEntity> arrayList) {
                setArrayList(arrayList);
                if (arrayList.size()<1)
                {
                    staticVar.ToastMessage(getActivity());
                }
                else
                {

                adapter=new GridViewAdapter(getActivity(),arrayList);
                gridView.setAdapter(adapter);
                    SaveData();

            }}
        });
    }

    public void SaveData()
    {
        CashedMovies CashedMovies = new CashedMovies(getActivity());
        for (int i=0;i<getArrayList().size();i++)
        {
            CashedMovies.openData();
            try {
                if (getArrayList().get(i).ID=="197584") {
                    continue;
                }
                else{ CashedMovies.CashedData(getArrayList().get(i));}

            }
            catch (Exception r)
            {

            }
            CashedMovies.close();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        ShowCacheData();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // on option selected from the 2 sorting techniques
        int id = item.getItemId();

        if (id==R.id.menu_item_share)
        {
            SortMyMoviesList(staticVar.Top_Rated);

        }
        else
        if (id==R.id.menu_popular)
        {
            SortMyMoviesList(StaticVar.Popular);
        }
        else
        if (id==R.id.menu_item_favourite)
        {
            Favorite();
        }
        return super.onOptionsItemSelected(item);
    }

    // Favorite loads movie from database

    private void  Favorite() //this is the phase 2 and in order to save the Favorite part we need Database
    {
        FavoriteMovies dataBaseController=new FavoriteMovies(getActivity()); // here we create
        dataBaseController.OpenData();
        ArrayList<MovieEntity> moviedataArrayList=new ArrayList<>();
        moviedataArrayList=dataBaseController.getData();
        if(moviedataArrayList.size()<1)
        {
            Toast.makeText(getActivity(),staticVar.Favourite_Message, Toast.LENGTH_SHORT).show();
        }
        else {
            adapter = new GridViewAdapter(getActivity(), moviedataArrayList);
            gridView.setAdapter(adapter);
            dataBaseController.Close();
        }
    }
    private void ShowCacheData()//after opening the app stuff that are loaded are now cached
    {
        CashedMovies cashedMovies=new CashedMovies(getActivity());
        cashedMovies.openData();
        ArrayList<MovieEntity>array=new ArrayList<>();
        array=cashedMovies.getchashedData();
        adapter=new GridViewAdapter(getActivity(),array);
        gridView.setAdapter(adapter);
        cashedMovies.close();
    }
    public void SetMovieListener(MoviesListener moviesListener) {
        ml=moviesListener;
    }
}
