package com.aweshams.cinematch.serviceclients.tmdb.models;

import java.util.List;

/**
 * Created by irteza on 2018-02-17.
 */

public class MovieSummary {
    public int id;
    public int vote_count;
    public boolean video;
    public double vote_average;
    public String title;
    public double popularity;
    public String poster_path;
    public List<Integer> genre_ids;
    public String backdrop_path;
    public boolean adult;
    public String overview;
    public String release_date;
}
