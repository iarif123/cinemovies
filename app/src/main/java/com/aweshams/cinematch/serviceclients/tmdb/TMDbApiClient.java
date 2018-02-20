package com.aweshams.cinematch.serviceclients.tmdb;

import com.aweshams.cinematch.serviceclients.tmdb.models.Detailed;
import com.aweshams.cinematch.serviceclients.tmdb.models.MovieSummaryList;
import com.aweshams.cinematch.services.api.ApiClient;
import com.aweshams.cinematch.services.data.GsonTransform;
import com.aweshams.cinematch.utils.promises.Promise;

import okhttp3.OkHttpClient;

/**
 * Created by irteza on 2018-01-23.
 */

public class TMDbApiClient extends ApiClient {

    // region constants

    String API_KEY = "";
    String BASE_URL = "https://api.themoviedb.org/3/movie/";

    // endregion

    //region constructors

    public TMDbApiClient(OkHttpClient client) {
        super(client);
    }

    // engregion

    // region methods

    /**
     * Retrieve movie details
     *
     * @param id
     * @return
     */
    public Promise<Detailed> getMovieDetailsById(int id) {

        // create uri
        String uri = BASE_URL + id + "?api_key=" + API_KEY;

        GsonTransform<Detailed> transform = GsonTransform.create(Detailed.class);

        return createRequest().get(uri, null, transform);
    }

    /**
     * Retrieve now playing movies
     *
     * @param
     * @return
     */
    public Promise<MovieSummaryList> getNowPlayingMovies(int page) {

        // create uri
        String uri = BASE_URL + "now_playing" + "?api_key=" + API_KEY + "&language=en-US&page=" + page;

        GsonTransform<MovieSummaryList> transform = GsonTransform.create(MovieSummaryList.class);

        return createRequest().get(uri, null, transform);
    }

    // endregion
}
