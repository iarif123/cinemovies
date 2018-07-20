package com.aweshams.cinematch.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.R;
import com.aweshams.cinematch.models.ActionTabSection;
import com.aweshams.cinematch.utils.analytics.AnalyticsHelper;
import com.aweshams.cinematch.utils.connectivity.NetworkConnectivityListener;

import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Created by irteza on 2018-05-19.
 */

public abstract class BaseFragment extends Fragment {

    // TODO: initialize analytics if required
    public String pageName = this.getClass().getName();
    public final HashMap<String, Object> customFlags = new HashMap<>();
    protected boolean mShouldTrackPageView = true;

    private Handler mConnectivityHandler = null;

    protected DateTime mLastUpdatedDate = null;

    // TODO: inject languageService

    public BaseFragment() {
        super();

        // TODO: inject dependencies
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create and register handler
        mConnectivityHandler = new Handler(msg -> {
            updateInternetStatus();
            return false;
        });

        // TODO: Register handler
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            updateLastUpdatedDate(mLastUpdatedDate);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {

        super.onStop();

        // TODO: Unregister handler
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        // TODO: Unregister handler
    }

    public void updateLastUpdatedDate(DateTime calendar) {
        mLastUpdatedDate = calendar;
        try {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.updateLastUpdatedDate(calendar);
            }
        } catch (Exception ex) {
            Log.e("BaseFragment", "Cannot update last updated date", ex);
        }
    }

    public void showErrorDialog(String msg) {
        showErrorDialog(msg, null, null);
    }

    public void showErrorDialog(String msg, String actionText, DialogInterface.OnClickListener clickListener) {
        HashMap<String, Object> cData = new HashMap<>();
        cData.put("aa.error.user.event", 1);
        cData.put("aa.error.messaging", actionText);
        AnalyticsHelper.instance.trackError(cData);

        try {
            BaseActivity activity = getBaseActivity();

            if (activity != null) {

                if(activity instanceof ViewPagerActivity) {

                    ViewPagerActivity viewPagerActivity = (ViewPagerActivity) activity;
                    int fragmentIndex = viewPagerActivity.mFragments.indexOf(this);

                    // Only shows error message when in current view
                    // Show when fragmentIndex < 0 not all fragments are added to the viewPager
                    if(fragmentIndex == viewPagerActivity.mViewPager.getCurrentItem() || fragmentIndex < 0) {

                        activity.runOnUiThread(() -> {
                            try {
                                activity.showErrorDialog(msg, actionText, clickListener);
                            } catch (Exception ex) {
                                Log.e("BaseFragment", "Cannot show error dialog", ex);
                            }
                        });
                    }
                }
                else {

                    activity.runOnUiThread(() -> {
                        try {
                            activity.showErrorDialog(msg, actionText, clickListener);
                        } catch (Exception ex) {
                            Log.e("BaseFragment", "Cannot show error dialog", ex);
                        }
                    });
                }
            }
        } catch (Exception ex) {
            Log.e("BaseFragment", "Cannot show error dialog", ex);
        }
    }

    public abstract void showEmptyStateError(ActionTabSection section);

    public abstract void showEmptyStateError(ActionTabSection actionTabSection, View.OnClickListener clickListener);


    public void hideEmptyStateError() {
        View view = getView();
        if(view != null) {

            RelativeLayout container = (RelativeLayout) view.findViewById(R.id.loading_container);

            if (container != null) {

                View loadingView = container.findViewById(R.id.empty_state_error);
                container.removeView(loadingView);
            }
        }
    }


    public void showSnackBar(String text) {
        showSnackBar(false, text, null, null);
    }

    public void showSnackBar(boolean isGranolaBar, String text, String actionText, View.OnClickListener clickListener) {
        try {
            BaseActivity activity = getBaseActivity();

            if (activity != null) {
                activity.runOnUiThread(() -> {
                    try {
                        activity.showSnackBar(isGranolaBar, text, actionText, Snackbar.LENGTH_LONG, clickListener);
                    } catch (Exception ex) {
                        Log.e("BaseFragment", "Cannot show snackbar", ex);
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("BaseFragment", "Cannot show snackbar", ex);
        }
    }

    public synchronized void showLoadingView() {
        View view = getView();
        if (view != null) {

            RelativeLayout container = (RelativeLayout) view.findViewById(R.id.loading_container);

            if (container != null) {
                //Check to see if this is a duplicate call to show loading view.
                View loadingView = container.findViewById(R.id.loading_layout);
                if (loadingView != null) {
                    return;
                }

                loadingView = getActivity().getLayoutInflater().inflate(R.layout.base_loading_item, null);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
    }

    public synchronized void hideLoadingView() {

        View view = getView();
        if (view != null) {

            RelativeLayout container = (RelativeLayout) view.findViewById(R.id.loading_container);

            if (container != null) {

                View loadingView = container.findViewById(R.id.loading_layout);
                container.removeView(loadingView);
            }
        }
    }

    private void updateInternetStatus() {
        NetworkConnectivityListener.State oldState = CinematchApplication.instance.connectivityListener.getPreviousState();
        NetworkConnectivityListener.State newState = CinematchApplication.instance.connectivityListener.getState();
        if (oldState == NetworkConnectivityListener.State.NOT_CONNECTED && newState == NetworkConnectivityListener.State.CONNECTED) {
            onConnectionGained();
        } else if(oldState == NetworkConnectivityListener.State.CONNECTED && newState == NetworkConnectivityListener.State.NOT_CONNECTED) {
            onConnectionLost();
        }
    }

    protected void onConnectionGained() {
    }

    protected void onConnectionLost() {
    }

    protected BaseActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }

        return null;
    }
}
