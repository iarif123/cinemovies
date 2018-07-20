package com.aweshams.cinematch.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aweshams.cinematch.R;
import com.aweshams.cinematch.utils.DateHelper;

import org.joda.time.DateTime;

/**
 * Created by irteza on 2018-05-20.
 */

public class InternetErrorBar extends LinearLayout {

    // region Properties
    private Context _context;
    private DateTime mlastUpdatedDate;
    private TextView mLastUpdatedText;
    //endregion

    // region Constructors
    public InternetErrorBar(Context context) {
        super(context);
        init(context);
    }

    public InternetErrorBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InternetErrorBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    // endregion

    //region Public Methods

    public void setLastUpdatedDate (DateTime lastUpdatedDate) {

        mlastUpdatedDate = lastUpdatedDate;
        if (mlastUpdatedDate != null) {
            mLastUpdatedText.setVisibility(View.VISIBLE);
            mLastUpdatedText.setText(String.format("Last updated: %1$s",
                    DateHelper.formatDateForLastUpdatedDate(mlastUpdatedDate)));
        }
        else {
            mLastUpdatedText.setVisibility(View.GONE);
        }
    }


    //endregion

    //region Helper Methods

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.error_internet_bar, this);

        _context = context;

        mLastUpdatedText = (TextView) findViewById(R.id.last_updated_text_bar);
    }

    //endregion

}