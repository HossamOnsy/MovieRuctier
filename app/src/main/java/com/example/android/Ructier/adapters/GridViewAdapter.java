package com.example.android.Ructier.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.Ructier.MovieEntity;
import com.example.android.Ructier.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */

public class GridViewAdapter extends BaseAdapter {
    Context mContext;
    private final LayoutInflater Inflater;
    public ArrayList<MovieEntity> arraylist;

    public GridViewAdapter(Context context, ArrayList<MovieEntity> arraylist) {
        this.Inflater = LayoutInflater.from(context);
        this.arraylist = arraylist;
        this.mContext = context;
    }
    @Override
    public int getCount()
    {
        return arraylist.size();
    }
    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder {
       ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = Inflater.inflate(R.layout.single_movie_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view_mainScreen);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MovieEntity moviedata = arraylist.get(position);
        Picasso.with(mContext).load(moviedata.getPosterPath()).into(holder.imageView);
        return convertView;
    }
}
