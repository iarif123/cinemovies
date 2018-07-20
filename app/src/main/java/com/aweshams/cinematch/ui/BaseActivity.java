package com.aweshams.cinematch.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.R;
import com.aweshams.cinematch.ui.components.InternetErrorBar;
import com.aweshams.cinematch.models.ActionTabSection;
import com.aweshams.cinematch.utils.StringHelper;
import com.aweshams.cinematch.utils.analytics.AnalyticsHelper;
import com.aweshams.cinematch.utils.connectivity.NetworkConnectivityListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Created by irteza on 2018-05-19.
 */
public class BaseActivity extends AppCompatActivity {
    public static int SHOULD_HIDE_LOADER_ON_LOAD = 1000;

    private static boolean mWasInBackground = false;

    public String pageName = this.getClass().getName();
    public AnalyticsHelper.Section section = AnalyticsHelper.Section.General;

    protected View mContentView;
    protected InternetErrorBar mInternetErrorBar;
    protected boolean mShouldTrackPageView = true;

    private CoordinatorLayout mCoordinatorLayout;

    private Handler mConnectivityHandler = null;

    private Handler mGlobalSplashHandler = null;
    private Runnable runnable;

    private FirebaseAnalytics mFirebaseAnalytics;

    protected Uri _deepLinkUri;

    public BaseActivity() {
        super();

        // inject dependencies
        CinematchApplication.instance
                .getComponent()
                .inject(this);
    }

    public void showLoadingView() {

        RelativeLayout container = (RelativeLayout) findViewById(R.id.loading_container);
        if (container != null) {

            hideLoadingView();

            View loadingView = getLayoutInflater().inflate(R.layout.base_loading_item, null);
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.alignWithParent = true;
            loadingView.setLayoutParams(lp);

            // disable interactions behind loading overlay
            loadingView.setClickable(true);
            loadingView.setFocusable(true);
            loadingView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });

            container.addView(loadingView);
        }
    }

    public void hideLoadingView() {
        RelativeLayout container = (RelativeLayout) findViewById(R.id.loading_container);
        if (container != null) {

            View loadingView = container.findViewById(R.id.loading_layout);

            if (loadingView != null) {

                ViewParent parent = loadingView.getParent();

                if (parent instanceof ViewGroup) {

                    ((ViewGroup)parent).removeView(loadingView);
                } else {

                    container.removeView(loadingView);
                }
            }
        }
    }

    public void showEmptyStateError(ActionTabSection section) {
        showEmptyStateError(section, null);
    }

    public void showEmptyStateError(ActionTabSection section, View.OnClickListener clickListener) {

        RelativeLayout container = (RelativeLayout) findViewById(R.id.loading_container);

        if (container != null) {

            View emptyStateErrorView = getLayoutInflater().inflate(R.layout.empty_state_error, null);

            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.alignWithParent = true;
            emptyStateErrorView.setLayoutParams(lp);


            hideEmptyErrorState();
            container.addView(emptyStateErrorView);
        }
    }

    public void hideEmptyErrorState() {

        RelativeLayout container = (RelativeLayout) findViewById(R.id.loading_container);

        if (container != null) {

            View loadingView = container.findViewById(R.id.empty_state_error);
            container.removeView(loadingView);
        }
    }

    public void showSnackBar(String text) {
        showSnackBar(text, null, Snackbar.LENGTH_LONG, null);
    }

    public void showSnackBar(String text, String actionText, int length, View.OnClickListener clickListener) {
        showSnackBar(false, text, null, Snackbar.LENGTH_LONG, clickListener);
    }

    public void showSnackBar(boolean isGranolaBar, String text, String actionText, int length, View.OnClickListener clickListener) {
        try {
            if (mCoordinatorLayout != null) {
                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, text, length);
                if (actionText != null && clickListener != null) {
                    snackbar.setAction(actionText, clickListener);
                }

                View snackBarView = snackbar.getView();
                TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
                //textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_exclamation, 0, 0, 0);
                textView.setFocusable(true);
                textView.setFocusableInTouchMode(true);
                int dp15 = (int) (15 * getResources().getDisplayMetrics().density + 0.5f);

                textView.setCompoundDrawablePadding(dp15);
                textView.setMaxLines(5);
                textView.setGravity(Gravity.TOP);

                //snackbar.getView().setContentDescription(text + getString(R.string.snackbar_talkback_dismiss));
                snackbar.getView().sendAccessibilityEvent(AccessibilityEvent.TYPE_ANNOUNCEMENT);

                if (isGranolaBar) {
                    textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

                } else {
                    textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                }
                snackbar.show();
            }
        } catch (Exception ex) {
            Log.e("BaseActivity", "Cannot show snackbar", ex);
        }
    }

    /**
     * Simple method to display error dialog. The dialog handles highly interruptive
     * errors that require user action and / or dismissal
     *
     * @param msg message to display to the user
     */
    public void showErrorDialog(String msg) {
        showErrorDialog(msg, null, null);
    }

    /**
     * A more robust method to display error dialog
     *
     * @param msg message to display to the user
     * @param actionText
     * @param clickListener
     */
    public void showErrorDialog(String msg, String actionText, DialogInterface.OnClickListener clickListener) {

        HashMap<String, Object> cData = new HashMap<>();
        cData.put("aa.error.user.event", 1);
        cData.put("aa.error.messaging", actionText);
        AnalyticsHelper.instance.trackError(cData);

        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.AppTheme);
        alert.setCancelable(false);
        alert.setTitle("error");
        //alert.setIcon(R.drawable.icon_error_circle);
        alert.setMessage(msg);

        if (!StringHelper.isNullOrEmpty(actionText) && clickListener != null) {
            alert.setPositiveButton(actionText, clickListener);
            alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    hideLoadingView();
                }
            });
        } else {
            alert.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    hideLoadingView();
                }
            });
        }

        if (!isFinishing()) {
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    }

    public void showDialog(String msg, String actionText, String dialogMessage, DialogInterface.OnClickListener clickListener) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.AppTheme);
        alert.setCancelable(false);
        alert.setTitle(dialogMessage);
        alert.setMessage(msg);

        if (!StringHelper.isNullOrEmpty(actionText) && clickListener != null) {
            alert.setPositiveButton(actionText, clickListener);
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    hideLoadingView();
                }
            });
        } else {
            alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    hideLoadingView();
                }
            });
        }

        if (!isFinishing())
            alert.show();
    }

    @Override
    protected void onDestroy() {
        // TODO: Unregister Handlers
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Add custom error handling
        final Thread.UncaughtExceptionHandler oldHandler =
                Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(
                (paramThread, paramThrowable) -> {
                    // Do your own error handling here
                    Log.e("UNCAUGHT_EXCEPTION", "Uncaught Exception", paramThrowable);

                    HashMap<String, Object> cData = new HashMap<>();
                    cData.put("aa.error.system.event", 1);
                    cData.put("aa.error.messaging", paramThrowable.toString());
                    AnalyticsHelper.instance.trackError(cData);

                    if (oldHandler != null) {
                        //Delegates to Android's error handling
                        oldHandler.uncaughtException(
                                paramThread,
                                paramThrowable
                        );
                    } else {
                        //Prevents the service/app from freezing
                        System.exit(2);
                    }
                });

        // start connectivity checks
        CinematchApplication.instance.connectivityListener.startListening(
                getApplicationContext());
    }

    private void onContentViewSet(View view) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(this.getTitle());
        }

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        if (appBarLayout != null) {

            // Internet Check
            mInternetErrorBar = new InternetErrorBar(this);
            int internetErrorIndex = 0;
            if (appBarLayout.getChildCount() > 0) {
                internetErrorIndex = appBarLayout.getChildCount() >= 2 ? 2 : appBarLayout.getChildCount() - 1;
            }
            appBarLayout.addView(mInternetErrorBar, internetErrorIndex);
            // Set the initial state to GONE before updateInternetStatus() is called.
            mConnectivityHandler = new Handler(msg -> {
                updateInternetStatus();
                return false;
            });

            mInternetErrorBar.setVisibility(
                    CinematchApplication.instance.connectivityListener.getState() ==
                            NetworkConnectivityListener.State.CONNECTED ?
                            View.GONE : View.VISIBLE);
            CinematchApplication.instance.connectivityListener.registerHandler(mConnectivityHandler, 0);
        }

        if (view instanceof CoordinatorLayout) {
            mCoordinatorLayout = (CoordinatorLayout) view;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onContentViewSet(getContentView());
    }

    @Override
    public void setContentView(View view) {
        mContentView = view;
        super.setContentView(view);
        onContentViewSet(view);
    }

    @Override
    public void setContentView(View view, android.view.ViewGroup.LayoutParams params) {
        mContentView = view;
        super.setContentView(view, params);
        onContentViewSet(view);
    }

    //endregion

    //region public methods

    public void updateLastUpdatedDate(DateTime date) {
        if (mInternetErrorBar != null) {
            mInternetErrorBar.setLastUpdatedDate(date);
        }
    }

    public View getContentView() {
        final ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        return root.getChildAt(0);
    }

    public void hideKeyboard() {
        try {
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            AnalyticsHelper.instance.trackBackButtonClicked();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // using this to hide a loader view when an activity is restarted if the loading view was used to transfer between activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SHOULD_HIDE_LOADER_ON_LOAD) {
            hideLoadingView();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(null != getIntent().getData())
        {
            _deepLinkUri = getIntent().getData();
        }
    }

    private void updateInternetStatus() {
        NetworkConnectivityListener.State oldState = CinematchApplication.instance.connectivityListener.getPreviousState();
        NetworkConnectivityListener.State newState = CinematchApplication.instance.connectivityListener.getState();
        if (newState == NetworkConnectivityListener.State.CONNECTED) {
            if (oldState == NetworkConnectivityListener.State.NOT_CONNECTED) {
                onConnectionGained();
            }
            if (mInternetErrorBar != null) {
                mInternetErrorBar.setVisibility(View.GONE);
            }
        } else if (newState == NetworkConnectivityListener.State.NOT_CONNECTED) {
            if (oldState == NetworkConnectivityListener.State.CONNECTED) {
                onConnectionLost();
            }

            if (mInternetErrorBar != null) {
                mInternetErrorBar.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void onConnectionGained() {
    }

    protected void onConnectionLost() {
        //Analytics - Track the connection lost event
        HashMap<String, Object> cData = new HashMap<>();
        cData.put("aa.error.http.event", 1);
        cData.put("aa.error.messaging", "message");
        AnalyticsHelper.instance.trackError(cData);
    }
}
