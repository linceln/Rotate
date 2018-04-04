package com.xyz.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class RotateItemAnimator extends DefaultItemAnimator {

    private Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimationsMap = new HashMap<>();

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
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo,
                                 @NonNull ItemHolderInfo postInfo) {

        cancelCurrentAnimationIfExists(newHolder);

        animate(newHolder);

        return false;
    }

    private void cancelCurrentAnimationIfExists(RecyclerView.ViewHolder item) {
        if (likeAnimationsMap.containsKey(item)) {
            likeAnimationsMap.get(item).cancel();
        }
    }

    private void animate(final RecyclerView.ViewHolder holder) {

        ValueAnimator animatorPadding = ValueAnimator.ofInt(0, 30);
        animatorPadding.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                holder.itemView.setPadding((Integer) valueAnimator.getAnimatedValue(), 0,
                        (Integer) valueAnimator.getAnimatedValue(), 0);
            }
        });

        ValueAnimator animatorCollapse = ValueAnimator.ofInt(30, 0);
        animatorCollapse.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                holder.itemView.setPadding((Integer) valueAnimator.getAnimatedValue(), 0,
                        (Integer) valueAnimator.getAnimatedValue(), 0);
            }
        });


        ObjectAnimator animRotate = ObjectAnimator.ofFloat(holder.itemView, "rotation", mStartAngle, mEndAngle);
        ObjectAnimator animX = ObjectAnimator.ofFloat(holder.itemView, "translationX", mTranslationX);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        if (mTranslationX != 0) {
            set.play(animRotate).with(animX).with(animatorPadding);
        } else {
            set.play(animRotate).with(animX).with(animatorCollapse);
        }

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                likeAnimationsMap.remove(holder);
                dispatchChangeFinishedIfAllAnimationsEnded(holder);
            }
        });
        set.start();

        likeAnimationsMap.put(holder, set);
    }

    private void dispatchChangeFinishedIfAllAnimationsEnded(RecyclerView.ViewHolder holder) {
        if (likeAnimationsMap.containsKey(holder)) {
            return;
        }
        dispatchAnimationFinished(holder);
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        super.endAnimation(item);
        cancelCurrentAnimationIfExists(item);
    }

    @Override
    public void endAnimations() {
        super.endAnimations();
        for (AnimatorSet animatorSet : likeAnimationsMap.values()) {
            animatorSet.cancel();
        }
    }
}