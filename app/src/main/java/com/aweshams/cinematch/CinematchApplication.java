package com.aweshams.cinematch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.aweshams.cinematch.di.ApiModule;
import com.aweshams.cinematch.di.AppModule;
import com.aweshams.cinematch.di.ApplicationComponent;
import com.aweshams.cinematch.di.DaggerApplicationComponent;
import com.aweshams.cinematch.managers.MovieManager;
import com.aweshams.cinematch.utils.connectivity.NetworkConnectivityListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

/**
 * Created by irteza on 2017-12-05.
 */

public class CinematchApplication extends Application
        implements Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "CinematchApplication";

    public static CinematchApplication instance;

    public final NetworkConnectivityListener connectivityListener;

    private ApplicationComponent _component;

    public CinematchApplication() {
        super();

        // initialize instance
        instance = this;

        // create connectivity listener
        connectivityListener = new NetworkConnectivityListener();
    }

    public Context getCurrentContext() {
        return getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return _component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // create di container
        _component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(this))
                .build();
        _component.inject(this);

        // bind listeners
        registerActivityLifecycleCallbacks(this);

        // Initialize the Firebase app instance
        FirebaseApp.initializeApp(this);
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true);

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
