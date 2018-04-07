package com.xyz.test;

import android.content.Context;
import android.view.OrientationEventListener;

/**
 * 2018/4/7
 */

public class CustomOrientationEventListener extends OrientationEventListener {

    public CustomOrientationEventListener(Context context, int rate) {
        super(context, rate);
    }

    @Override
    public void onOrientationChanged(int orientation) {

    }
}
