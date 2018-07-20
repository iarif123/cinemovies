package com.aweshams.cinematch.ui.components;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.aweshams.cinematch.ui.BaseActivity;

/**
 * Created by irteza on 2018-05-20.
 */

public abstract class BaseComponent extends LinearLayout {

    public boolean mCanHide;
    protected LinearLayout mComponentContainer;

    /*public void setComponentVisibility(CardVisualState visibility)
    {
        if(visibility == HIDDEN)
            hide();
        else
            show();
    }*/


    public BaseComponent(Context context)
    {
        super(context);
    }

    public BaseComponent(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BaseComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    protected void initialize(Context context) {
    }

    private void show()
    {
        if(mCanHide) {
            this.setVisibility(VISIBLE);
        }

    }

    private void hide()
    {
        if(mCanHide) {
            this.setVisibility(GONE);
        }
    }

    protected BaseActivity getBaseActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof BaseActivity) {
                return (BaseActivity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
