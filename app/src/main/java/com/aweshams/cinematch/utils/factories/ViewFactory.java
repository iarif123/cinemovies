package com.aweshams.cinematch.utils.factories;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Constructor;

/**
 * Created by irteza on 2018-05-21.
 */

public class ViewFactory<T extends View>{
    protected final Context mContext;
    protected final Class<T> mType;

    public ViewFactory(Context context, Class<T> type){
        mContext = context;
        mType    = type;
    }

    public Object instantiate(){
        if(mContext != null && mType != null){

            Class<?> target = mType;
            Class[] args    = {Context.class};
            try {
                Constructor constructor = target.getConstructor(args);
                T component = (T)constructor.newInstance(mContext);
                return component;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
