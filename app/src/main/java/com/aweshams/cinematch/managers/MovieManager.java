package com.aweshams.cinematch.managers;

import android.graphics.Bitmap;
import android.util.Log;

import com.aweshams.cinematch.models.MovieItem;
import com.aweshams.cinematch.services.api.tmdb.TMDbApiClient;
import com.aweshams.cinematch.services.api.tmdb.enums.MovieSummaryType;
import com.aweshams.cinematch.services.api.tmdb.models.MovieSummary;
import com.aweshams.cinematch.services.api.tmdb.models.MovieSummaryList;
import com.aweshams.cinematch.services.data.IImageService;
import com.aweshams.cinematch.utils.promises.Promise;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by irteza on 2018-03-02.
 */
@Singleton
public class MovieManager {

    @Inject TMDbApiClient tmDbApiClient;
    @Inject IImageService imageService;

    @Inject
    public MovieManager() {
    }

    /**
     * Get movie summaries
     *
     * @param page
     * @return Promise containing MovieItems
     */
    public Promise<List<MovieItem>> getMovieSummaries(int page, MovieSummaryType type) {
        // get movies to organise
        return this.tmDbApiClient.getMoviesSummaries(page, type)
                .then(result -> {

                    // populate movieList with movie info
                    ArrayList<MovieItem> movieItems = new ArrayList<>();
                    ArrayList<Promise<Bitmap>> promises = new ArrayList<>();

                    for (MovieSummary summary : result.results) {
                        String posterPath = summary.poster_path;

                        URL imageUrl = this.tmDbApiClient.getImageUrl(posterPath);
                        Promise<Bitmap> promise = this.imageService.getImage(imageUrl);
                        promises.add(promise);

                        movieItems.add(new MovieItem(summary.release_date, summary.title, "horror", Double.toString(summary.vote_average)));
                    }

                    return Promise.when(promises)
                            .then(bitmaps -> {
                                for (int i = 0; i < bitmaps.size(); i++) {
                                    movieItems.get(i).poster = bitmaps.get(i);
                                }

                                return movieItems;
                            });
                }).error(e -> {
                    Log.d("TEST", e.getMessage());
                });
    }
}
