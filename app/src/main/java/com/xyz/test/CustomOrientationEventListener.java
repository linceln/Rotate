package com.xyz.test;

import android.content.Context;
import android.view.OrientationEventListener;


public class CustomOrientationEventListener extends OrientationEventListener {

    private RotationAdapter mAdapter;

    private RotationItemAnimator mAnimator;

    private float mCurrentOrientation; // 记录当前所在的角度(0, 90, 180, -90)

    private int mSize; // 列表大小

    public CustomOrientationEventListener(Context context) {
        super(context);
    }

    public void setRecyclerAdapter(RotationAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void setRecyclerItemAnimator(RotationItemAnimator animator) {
        this.mAnimator = animator;
    }

    public void setListSize(int size) {
        this.mSize = size;
    }

    @Override
    public void onOrientationChanged(int orientation) {

        if ((orientation > 350 || orientation < 10) && mCurrentOrientation != 0f) {
            // 0度
            mAnimator.setAngle(mCurrentOrientation, 0f);
            mAnimator.setX(0f);

            mAdapter.notifyItemRangeChanged(0, mSize);
            mCurrentOrientation = 0f;
            mAdapter.setCurrentOrientation(mCurrentOrientation);
        } else if ((orientation > 80 && orientation < 100) && mCurrentOrientation != -90f) {
            // 90度
            mAnimator.setAngle(mCurrentOrientation, -90f);
            mAnimator.setX(-RotationItemAnimator.TRANSLATION_X);

            mAdapter.notifyItemRangeChanged(0, mSize);
            mCurrentOrientation = -90f;
            mAdapter.setCurrentOrientation(mCurrentOrientation);
        } else if ((orientation > 170 && orientation < 190) && mCurrentOrientation != 180f) {
            // 180度
            mAnimator.setAngle(mCurrentOrientation, 180f);
            mAnimator.setX(0f);

            mAdapter.notifyItemRangeChanged(0, mSize);
            mCurrentOrientation = 180f;
            mAdapter.setCurrentOrientation(mCurrentOrientation);
        } else if ((orientation > 260 && orientation < 280) && mCurrentOrientation != 90f) {
            // 270度
            mAnimator.setAngle(mCurrentOrientation, 90f);
            mAnimator.setX(RotationItemAnimator.TRANSLATION_X);

            mAdapter.notifyItemRangeChanged(0, mSize);
            mCurrentOrientation = 90f;
            mAdapter.setCurrentOrientation(mCurrentOrientation);
        }
    }
}
