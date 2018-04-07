package com.xyz.test

import android.content.Context
import android.view.OrientationEventListener


class CustomOrientationEventListener(context: Context) : OrientationEventListener(context) {

    private var mAdapter: RotationAdapter? = null

    private var mAnimator: RotationItemAnimator? = null

    /**
     * 记录当前所在的角度(0f, 90f, 180f, -90f)
     */
    private var mCurrentOrientation: Float = 0.toFloat()

    /**
     * 列表大小
     */
    private var mSize: Int = 0

    fun setRecyclerAdapter(adapter: RotationAdapter) {
        this.mAdapter = adapter
    }

    fun setRecyclerItemAnimator(animator: RotationItemAnimator) {
        this.mAnimator = animator
    }

    fun setListSize(size: Int) {
        this.mSize = size
    }

    override fun onOrientationChanged(orientation: Int) {

        if ((orientation > 350 || orientation < 10) && mCurrentOrientation != 0f) {
            // 0度
            mAnimator!!.setAngle(mCurrentOrientation, 0f)
            mAnimator!!.setX(0f)

            mAdapter!!.notifyItemRangeChanged(0, mSize)
            mCurrentOrientation = 0f
            mAdapter!!.setCurrentOrientation(mCurrentOrientation)
        } else if (orientation in 81..99 && mCurrentOrientation != -90f) {
            // 90度
            mAnimator!!.setAngle(mCurrentOrientation, -90f)
            mAnimator!!.setX((-RotationItemAnimator.TRANSLATION_X).toFloat())

            mAdapter!!.notifyItemRangeChanged(0, mSize)
            mCurrentOrientation = -90f
            mAdapter!!.setCurrentOrientation(mCurrentOrientation)
        } else if (orientation in 171..189 && mCurrentOrientation != 180f) {
            // 180度
            mAnimator!!.setAngle(mCurrentOrientation, 180f)
            mAnimator!!.setX(0f)

            mAdapter!!.notifyItemRangeChanged(0, mSize)
            mCurrentOrientation = 180f
            mAdapter!!.setCurrentOrientation(mCurrentOrientation)
        } else if (orientation in 261..279 && mCurrentOrientation != 90f) {
            // 270度
            mAnimator!!.setAngle(mCurrentOrientation, 90f)
            mAnimator!!.setX(RotationItemAnimator.TRANSLATION_X.toFloat())

            mAdapter!!.notifyItemRangeChanged(0, mSize)
            mCurrentOrientation = 90f
            mAdapter!!.setCurrentOrientation(mCurrentOrientation)
        }
    }
}
