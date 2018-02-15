package com.aweshams.cinematch;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by irteza on 2017-12-05.
 */

public class CinematchApplication extends Application
        implements Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "CinematchApplication";

    public static CinematchApplication instance;

    public CinematchApplication() {
        // initialize instance
        instance = this;
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
