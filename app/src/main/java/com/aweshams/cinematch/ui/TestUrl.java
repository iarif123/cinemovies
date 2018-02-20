package com.aweshams.cinematch.ui;

import android.os.AsyncTask;
import android.util.Log;

import com.aweshams.cinematch.controls.adapters.RecyclerViewAdapter;
import com.aweshams.cinematch.serviceclients.tmdb.TMDbApiClient;
import com.aweshams.cinematch.serviceclients.tmdb.models.MovieSummaryList;
import com.aweshams.cinematch.utils.promises.Promise;

import okhttp3.OkHttpClient;

/**
 * Created by irteza on 2018-01-23.
 */

public class TestUrl extends AsyncTask {
    private RecyclerViewAdapter mAdapter;

    public TestUrl(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        OkHttpClient client = new OkHttpClient();
        TMDbApiClient tmDbApiClient = new TMDbApiClient(client);
        Promise<MovieSummaryList> promise = tmDbApiClient.getNowPlayingMovies(1);

        promise.then(result -> {
            mAdapter.updateList(result.results);
            return null;
        }).error(e -> {
            Log.d("TEST", e.getMessage());
        });

        return null;

        /*String API_KEY = "282cd636ba9feea46a58ad7077fb5aaa";
        String sample_uri = "https://api.themoviedb.org/3/movie/550?api_key=282cd636ba9feea46a58ad7077fb5aaa";

        URLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(sample_uri);
            urlConnection = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            Log.d(MainActivity.class.getSimpleName(), stringBuffer.toString());

            return new JSONObject(stringBuffer.toString());
        }
        catch (Exception ex) {
            Log.d(MainActivity.class.getSimpleName(), "ERROR DURING API CALL");
            return null;
        }*/
    }
}
