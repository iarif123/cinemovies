package com.aweshams.cinematch.utils.analytics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aweshams.cinematch.ui.BaseActivity;
import com.aweshams.cinematch.ui.BaseFragment;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by irteza on 2018-05-19.
 */

public class AnalyticsHelper {
    //region constants

    public enum Section
    {
        NowPlaying,
        General
    }

    public static final String LOG_TAG = "AnalyticsHelper";

    private String currentPage;
    private DateTime startCurrentPageView;
    private boolean backButtonClicked;
    private HashMap<String, Object> currentPageStateData;
    public String countrySearch;

    // TODO: inject managers and resources and logger and languageService?

    public static final AnalyticsHelper instance = new AnalyticsHelper();

    private AnalyticsHelper()
    {
        // inject dependencies
    }

    public void init(Context context) {

    }

    public void pauseCollectingLifecycleData() {

    }

    public void collectLifecycleData(BaseActivity baseActivity) {

    }

    public static String retrieveVisitorIdentification() {
        return "";
    }



    public void trackBackButtonClicked()
    {
        backButtonClicked = true;
    }

    public void trackPageView(BaseActivity baseActivity, HashMap<String, Object> customEvents)
    {
    }

    public void trackPageView(BaseFragment baseFragment, HashMap<String, Object> customEvents)
    {
    }


    public void trackAction(BaseActivity baseActivity, String customEvent)
    {
    }

    public void trackAction(BaseFragment baseFragment, String customEvent)
    {
    }

    public void trackAction(BaseActivity baseActivity, Map<String, Object> customEvents)
    {
    }

    public void trackAction(BaseFragment baseFragment, HashMap<String, Object> customEvents)
    {
    }

    public void trackSearch(BaseFragment baseFragment, String searchTerm, Integer resultCount, String topic)
    {
        //Analytics
        HashMap<String, Object> action = getSearchContext(searchTerm, resultCount, topic);

        AnalyticsHelper.instance.trackAction(baseFragment, action);
    }

    public void trackSearch(BaseActivity baseActivity, String searchTerm, Integer resultCount, String topic)
    {
        //Analytics
        HashMap<String, Object> action = getSearchContext(searchTerm, resultCount, topic);

        AnalyticsHelper.instance.trackAction(baseActivity, action);
    }

    @NonNull
    private HashMap<String, Object> getSearchContext(String searchTerm, Integer resultCount, String topic) {
        return new HashMap<>();
    }

    public void trackError(HashMap<String,Object> customEvents)
    {
        trackAction(null, null, customEvents, true);
    }

    public void trackError(String pageName, Section section, HashMap<String,Object> customEvents)
    {
        trackAction(pageName, section, customEvents, true);
    }

    public void trackAction(Map<String,Object> customEvents)
    {
        trackAction(null, null, customEvents, false);
    }

    public void trackAction(String customEvent)
    {
        HashMap<String, Object> customEvents = new HashMap<>();
        customEvents.put(customEvent, "1");
        trackAction(null, null, customEvents, false);
    }


    public synchronized void trackAction(String pageName, Section section, Map<String, Object> customEvents, boolean isError) {
        trackAction(null, pageName, section, customEvents, isError);
    }

    public synchronized void trackAction(String actionName, String pageName, Section section, Map<String, Object> customEvents, boolean isError) {

    }


    public synchronized void trackPageView(String pageName, Section section, Map<String, Object> customEvents)
    {

    }


    public String getTimeSpentOnPreviousPage() {
        if (startCurrentPageView == null)
        {
            return "0";
        }
        DateTime now = DateTime.now();
        int secondsElapsed = Seconds.secondsBetween(startCurrentPageView, now).getSeconds();
        return "" + secondsElapsed;
    }

    private String mapToString(HashMap<String, Object> map) {
        StringBuilder data = new StringBuilder();

        for (Object o : map.entrySet()) {
            Map.Entry e = (Map.Entry) o;
            if (e.getValue() != null) {
                data.append(e.getKey()).append(" : ").append(e.getValue().toString()).append("\n");
            }
        }

        return data.toString();
    }
}
