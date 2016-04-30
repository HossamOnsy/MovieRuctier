package com.example.android.Ructier.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.Ructier.R;
import com.example.android.Ructier.ReviewDetails;

import java.util.ArrayList;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class ReviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ReviewDetails> objects;

    private class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }

    public ReviewAdapter(Context context, ArrayList<ReviewDetails> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    public int getCount() {
        return objects.size();
    }

    public ReviewDetails getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.review_items, null);
            holder.tv1 = (TextView) convertView.findViewById(R.id.t1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.t2);
            holder.tv3 = (TextView) convertView.findViewById(R.id.t3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv1.setText(objects.get(position).getAuthor());
        holder.tv2.setText(objects.get(position).getUrl());
        holder.tv3.setText(objects.get(position).getContent());
        return convertView;
    }
}