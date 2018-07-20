package com.aweshams.cinematch.di;

import android.content.Context;
import android.content.res.Resources;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.services.data.IImageService;
import com.aweshams.cinematch.services.data.ImageService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by irteza on 2018-05-20.
 */

@Module()
public class AppModule {

    private final CinematchApplication _application;


    public AppModule(CinematchApplication application) {
        _application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return _application;
    }

    @Provides
    @Singleton
    public Resources providesResources() {
        return _application.getResources();
    }

    @Provides
    @Singleton
    public IImageService providesImageService() {
        return new ImageService();
    }
}
