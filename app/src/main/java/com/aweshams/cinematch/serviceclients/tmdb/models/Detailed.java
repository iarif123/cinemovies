package com.aweshams.cinematch.serviceclients.tmdb.models;

/**
 * Created by irteza on 2018-01-23.
 */

public class Detailed {
    public int id;
    public String imdb_id;
    public boolean adult;
    public String backdrop_path;
    public Object belongs_to_collection;
    public int budget;
    public Genre[] genres;
    public String homepage;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public ProductionCompany[] production_companies;
    public ProductionCountry[] production_countries;
    public String release_date; // yyyy-mm-dd
    public int revenue;
    public int runtime; // in minutes
    public SpokenLanguage[] spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
}
