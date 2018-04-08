package com.xyz.test

import android.animation.*
import android.support.v7.widget.RecyclerView.ViewHolder
import android.support.v7.widget.DefaultItemAnimator
import android.view.View

class RotationItemAnimator : DefaultItemAnimator() {

    companion object {

        /**
         * 动画持续时长
         */
        const val DURATION = 200L

        /**
         * (dp) 旋转时 item 之间的间距
         */
        const val PADDING = 12

        /**
         * (dp) 旋转时需要平移的距离
         */
        const val TRANSLATION_X = 18
    }

    private var mStartAngle: Float = 0f

    private var mEndAngle: Float = 0f

    private var mTranslationX: Float = 0f

    /**
     * 设置旋转动画的角度
     *
     * @param mStartAngle 起始角度
     * @param mEndAngle   结束角度
     */
    fun setAngle(mStartAngle: Float, mEndAngle: Float) {
        this.mStartAngle = mStartAngle
        this.mEndAngle = mEndAngle
    }

    /**
     * 设置 X 轴平移动画的距离
     *
     * @param mTranslationX 距离
     */
    fun setX(mTranslationX: Float) {
        this.mTranslationX = mTranslationX
    }

    override fun canReuseUpdatedViewHolder(viewHolder: ViewHolder): Boolean {
        return true
    }

    override fun animateChange(oldHolder: ViewHolder,
                               newHolder: ViewHolder,
                               fromX: Int, fromY: Int,
                               toX: Int, toY: Int): Boolean {

        if (oldHolder === newHolder) {
            runAnimatorSet(newHolder.itemView, newHolder)
            return false
        }
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
    }

    private fun runAnimatorSet(view: View, viewHolder: ViewHolder) {

        val padding = DpUtils.dp2px(view.context, PADDING.toFloat()).toInt()

        val animatorPadding = ValueAnimator.ofInt(0, padding)
        animatorPadding.addUpdateListener { valueAnimator ->
            view.setPadding(valueAnimator.animatedValue as Int, 0,
                    valueAnimator.animatedValue as Int, 0)
        }

        val animatorCollapse = ValueAnimator.ofInt(padding, 0)
        animatorCollapse.addUpdateListener { valueAnimator ->
            view.setPadding(valueAnimator.animatedValue as Int, 0,
                    valueAnimator.animatedValue as Int, 0)
        }

        val animRotate = ObjectAnimator.ofFloat(view, "rotation", mStartAngle, mEndAngle)

        val translationXPX = DpUtils.dp2px(view.context, mTranslationX)
        val animX = ObjectAnimator.ofFloat(view, "translationX", translationXPX)

        val set = AnimatorSet()
        set.duration = DURATION
        if (mTranslationX != 0f) {
            set.play(animRotate).with(animX).with(animatorPadding)
        } else {
            set.play(animRotate).with(animX).with(animatorCollapse)
        }

        set.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                dispatchChangeStarting(viewHolder, true)
            }

            override fun onAnimationEnd(animation: Animator) {
                set.removeAllListeners()
                dispatchChangeFinished(viewHolder, true)
                dispatchFinishedWhenDone()
            }
        })

        set.start()
    }

    private fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
        }
    }
}
