package com.xyz.test

import android.content.Context
import android.util.TypedValue

object DpUtils {

    fun dp2px(context: Context, dpVal: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.resources.displayMetrics)
    }
}
