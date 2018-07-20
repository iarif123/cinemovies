package com.aweshams.cinematch.di;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.services.api.tmdb.TMDbApiClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by irteza on 2018-05-20.
 */
@Module()
public class ApiModule {
    CinematchApplication _application;

    public ApiModule(CinematchApplication application) {
        _application = application;
    }

    @Provides
    @Singleton
    public TMDbApiClient providesTMDBApiClient() {
        // try to create api client
        try {

            // create http client using certificates
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build();

            // create and return api client
            TMDbApiClient apiClient = new TMDbApiClient(httpClient);
            return apiClient;
        }

        // rethrow exception on failure
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create TMDbApiClient", e);
        }
    }
}
