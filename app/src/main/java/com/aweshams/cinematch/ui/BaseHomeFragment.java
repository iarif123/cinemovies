package com.aweshams.cinematch.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aweshams.cinematch.R;
import com.aweshams.cinematch.models.ActionTabSection;

/**
 * Created by irteza on 2018-05-19.
 */

public class BaseHomeFragment extends BaseFragment {

    public BaseHomeFragment() {
        super();
    }

    public void showEmptyStateError(ActionTabSection section) {

        showEmptyStateError(section, null);
    }

    public void showEmptyStateError(ActionTabSection actionTabSection, View.OnClickListener clickListener) {

        View view = getView();

        if (view != null) {
            RelativeLayout container = (RelativeLayout) view.findViewById(R.id.loading_container);

            if (container != null) {

                View emptyStateErrorView = getActivity().getLayoutInflater().inflate(R.layout.error_empty_state, null);

                //ImageView errorMessageIcon = (ImageView) emptyStateErrorView.findViewById(R.id.empty_state_icon);
                TextView errorMessageTextView = (TextView) emptyStateErrorView.findViewById(R.id.empty_state_text);
                Button errorMessageBtn = (Button) emptyStateErrorView.findViewById(R.id.empty_state_retry_button);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.alignWithParent = true;
                emptyStateErrorView.setLayoutParams(lp);


                if (emptyStateErrorView != null) {

                    int stringId;
                    int imageId;

                    if (actionTabSection != null) {
                        //stringId = R.string.no_internet_splash;
                        //imageId = R.drawable.icon_error_alert;
                    } else {

                        //stringId = R.string.no_internet_splash;
                        //imageId = R.drawable.icon_error_alert;
                    }

                    if (errorMessageTextView != null) {
                        errorMessageTextView.setText("Error");
                    }

                    /*if (errorMessageIcon != null) {
                        errorMessageIcon.setImageResource(imageId);
                    }*/

                    if (errorMessageBtn != null && clickListener != null) {
                        errorMessageBtn.setOnClickListener(clickListener);
                    } else {
                        errorMessageBtn.setVisibility(View.GONE);
                    }

                    hideEmptyStateError(); // Hide all empty error states before showing the correct one
                    container.addView(emptyStateErrorView);
                }
            }
        }
    }
}
