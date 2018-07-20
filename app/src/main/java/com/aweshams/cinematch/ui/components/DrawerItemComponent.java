package com.aweshams.cinematch.ui.components;

import android.support.constraint.ConstraintLayout;

/**
 * Created by irteza on 2017-12-05.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;


public class DrawerItemComponent extends ConstraintLayout {
    // region Instance Variables
    //private DrawerItemComponentBinding binding;
    // endregion


    // region Constructors
    public DrawerItemComponent(Context context) {
        super(context);
        initialize(context);
    }

    public DrawerItemComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DrawerItemComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }
    // endregion


    // region Initialization
    private void initialize(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //binding = DrawerItemComponentBinding.inflate(inflater, this, true);
    }
    // endregion


    // region Setters
    /*public void setViewModel(DrawerItemViewModel viewModel) {
        binding.setViewModel(viewModel);
    }*/
    // endregion
}
