package com.xyz.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class RotateItemAnimator extends DefaultItemAnimator {

    public static final int DURATION = 200; // (毫秒)动画效果持续时间

    public static final int PADDING = 12; // (dp) 横向转动时 item 之间的距离

    private static int TRANSLATION_X = 50; // (dp)旋转时需要调整的距离

    private Map<RecyclerView.ViewHolder, AnimatorSet> animatorMap = new HashMap<>();

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

        Log.e("rotate", "animateChange: new: " + newHolder.getAdapterPosition() + " old: " + oldHolder.getAdapterPosition());

        cancelCurrentAnimationIfExists(oldHolder);
        cancelCurrentAnimationIfExists(newHolder);

        animate(newHolder);

        return false;
    }

    private void cancelCurrentAnimationIfExists(RecyclerView.ViewHolder item) {
        if (animatorMap.containsKey(item)) {
            animatorMap.get(item).cancel();
        }
    }

    private void animate(final RecyclerView.ViewHolder holder) {

        AnimatorSet set = getAnimatorSet(holder);

        animatorMap.put(holder, set);
    }

    @NonNull
    private AnimatorSet getAnimatorSet(final RecyclerView.ViewHolder holder) {
        int padding = DpUtils.dp2px(holder.itemView.getContext(), PADDING);

        ValueAnimator animatorPadding = ValueAnimator.ofInt(0, padding);
        animatorPadding.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                holder.itemView.setPadding((Integer) valueAnimator.getAnimatedValue(), 0,
                        (Integer) valueAnimator.getAnimatedValue(), 0);
            }
        });

        ValueAnimator animatorCollapse = ValueAnimator.ofInt(padding, 0);
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
        set.setDuration(DURATION);
        if (mTranslationX != 0) {
            set.play(animRotate).with(animX).with(animatorPadding);
        } else {
            set.play(animRotate).with(animX).with(animatorCollapse);
        }

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorMap.remove(holder);
                dispatchChangeFinishedIfAllAnimationsEnded(holder);
            }
        });
        set.start();
        return set;
    }

    private void dispatchChangeFinishedIfAllAnimationsEnded(RecyclerView.ViewHolder holder) {
        if (animatorMap.containsKey(holder)) {
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
        for (AnimatorSet animatorSet : animatorMap.values()) {
            animatorSet.cancel();
        }
    }
}