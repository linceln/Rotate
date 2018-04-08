package com.xyz.test

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

class RotationAdapter(list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mList = ArrayList<String>()

    var mCurrentOrientation: Float = 0f

    init {
        mList.clear()
        mList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_test, parent, false)
        return object : RecyclerView.ViewHolder(view) {
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tv = holder.itemView.findViewById<TextView>(R.id.textView)
        tv.text = mList[position]

        refreshItemView(holder)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    private fun refreshItemView(holder: RecyclerView.ViewHolder) {

        val context = holder.itemView.context
        val padding = DpUtils.dp2px(context, RotationItemAnimator.PADDING.toFloat()).toInt()
        val translationX = DpUtils.dp2px(context, RotationItemAnimator.TRANSLATION_X.toFloat())

        holder.itemView.post {
            when (mCurrentOrientation) {
                0f -> {
                    // 0度
                    holder.itemView.setPadding(0, 0, 0, 0)
                    holder.itemView.rotation = 0f
                    holder.itemView.translationX = 0f
                }
                -90f -> {
                    // 90度
                    holder.itemView.setPadding(padding, 0, padding, 0)
                    holder.itemView.rotation = -90f
                    holder.itemView.translationX = -translationX
                }
                180f -> {
                    // 180度
                    holder.itemView.setPadding(0, 0, 0, 0)
                    holder.itemView.rotation = 180f
                    holder.itemView.translationX = 0f
                }
                90f -> {
                    // 270度
                    holder.itemView.setPadding(padding, 0, padding, 0)
                    holder.itemView.rotation = 90f
                    holder.itemView.translationX = translationX
                }
            }
        }
    }
}