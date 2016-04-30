package com.example.android.Ructier;
import java.io.Serializable;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class MovieEntity implements Serializable {
    public String Title;
    public String Overview;
    public String PosterPath ;
    public String Date ;
    public String Picture;
    public String ID;

    public String getTitle() {
        return Title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String desc) {
        Overview = desc;
    }

    public String getPosterPath()
    {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }


}

