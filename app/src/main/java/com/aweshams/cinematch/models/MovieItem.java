package com.aweshams.cinematch.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by irteza on 2018-03-02.
 */

public class MovieItem extends BaseItem {
    public Bitmap poster;
    public String release_date;
    public String title;
    public String genre;
    public Bitmap tmdb_logo;
    public String rating;

    public MovieItem(String release_date, String title, String genre, String rating) {
        this.release_date = release_date;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }


}
