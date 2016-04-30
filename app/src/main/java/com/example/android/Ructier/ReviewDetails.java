package com.example.android.Ructier;

/**
 * Created by Hossam Eldeen Onsy on 4/29/2016.
 */
public class ReviewDetails {

    private String Author;  //The Writer of the Review
    private String Content; //The Content of the Review
    private String Url;     //The URL that we get in order to show this Review

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        this.Url = url;
    }
    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }






}