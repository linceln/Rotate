package com.xyz.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RotationItemAnimator extends android.support.v7.widget.DefaultItemAnimator{

    public static final int PADDING = 12;   // (dp) 旋转时 item 之间的间距

    private float mStartAngle;

    private float mEndAngle;

    private float mTranslationX;

    /**
     * 设置旋转动画的角度
     *
     * @param mStartAngle 起始角度
     * @param mEndAngle   结束角度
     */
    public void setAngle(float mStartAngle, float mEndAngle) {
        this.mStartAngle = mStartAngle;
        this.mEndAngle = mEndAngle;
    }

    /**
     * 设置 X 轴平移动画的距离
     *
     * @param mTranslationX 距离
     */
    public void setX(float mTranslationX) {
        this.mTranslationX = mTranslationX;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        if(oldHolder == newHolder) {
            runAnimatorSet(newHolder.itemView, newHolder);
            return false;
        }
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY);
    }

    private void runAnimatorSet(final View view, final RecyclerView.ViewHolder viewHolder) {

        int padding = DpUtils.dp2px(view.getContext(), PADDING);

        ValueAnimator animatorPadding = ValueAnimator.ofInt(0, padding);
        animatorPadding.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding((Integer) valueAnimator.getAnimatedValue(), 0,
                        (Integer) valueAnimator.getAnimatedValue(), 0);
            }
        });

        ValueAnimator animatorCollapse = ValueAnimator.ofInt(padding, 0);
        animatorCollapse.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding((Integer) valueAnimator.getAnimatedValue(), 0,
                        (Integer) valueAnimator.getAnimatedValue(), 0);
            }
        });

        ObjectAnimator animRotate = ObjectAnimator.ofFloat(view, "rotation", mStartAngle, mEndAngle);
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", mTranslationX);

        final AnimatorSet set = new AnimatorSet();
        set.setDuration(200);
        if (mTranslationX != 0) {
            set.play(animRotate).with(animX).with(animatorPadding);
        } else {
            set.play(animRotate).with(animX).with(animatorCollapse);
        }

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                dispatchChangeStarting(viewHolder, true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                set.removeAllListeners();
                dispatchChangeFinished(viewHolder, true);
                dispatchFinishedWhenDone();
            }
        });

        set.start();
    }

    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }
}
