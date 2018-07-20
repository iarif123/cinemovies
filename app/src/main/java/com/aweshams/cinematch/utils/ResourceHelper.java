package com.aweshams.cinematch.utils;

import android.content.res.Resources;

import com.aweshams.cinematch.CinematchApplication;

/**
 * Created by irteza on 2018-05-20.
 */

public class ResourceHelper {
    public static String getStringResource(int id)
    {
        return CinematchApplication.instance.getCurrentContext().getResources().getString(id);
    }

    public static int getIntegerResource(int id)
    {
        return CinematchApplication.instance.getCurrentContext().getResources().getInteger(id);
    }

    public static Resources getResources() {
        return CinematchApplication.instance.getCurrentContext().getResources();
    }

    /**
     * Perform a null check on string and then compare to expected value
     * @param actual
     * @param expected
     * @return
     */
    public static boolean isEqualTo(String actual, String expected) {
        return !StringHelper.isNullOrEmpty(actual) && actual.equalsIgnoreCase(expected);
    }
}
