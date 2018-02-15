package com.aweshams.cinematch.ui;

import android.os.AsyncTask;
import android.util.Log;

import com.aweshams.cinematch.serviceclients.tmdb.TMDbApiClient;
import com.aweshams.cinematch.serviceclients.tmdb.models.Detailed;
import com.aweshams.cinematch.utils.Promise;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.OkHttpClient;

/**
 * Created by irteza on 2018-01-23.
 */

public class TestUrl extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {

        OkHttpClient client = new OkHttpClient();
        TMDbApiClient tmDbApiClient = new TMDbApiClient(client);
        Promise<Detailed> promise = tmDbApiClient.getMovieDetailsById(550);

        promise.then(result -> {
            Log.d("TEST", result.title);
            return result;
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
