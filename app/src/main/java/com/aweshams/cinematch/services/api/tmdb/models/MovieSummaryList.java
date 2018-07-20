package com.aweshams.cinematch.services.api.tmdb.models;

import java.util.List;

/**
 * Created by irteza on 2018-02-18.
 */

public class MovieSummaryList {
    public List<MovieSummary> results;
    public int page;
    public int total_results;
    public MovieSummaryDates dates;
    public int total_pages;
}
