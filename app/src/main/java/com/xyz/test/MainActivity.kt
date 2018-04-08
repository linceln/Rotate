package com.xyz.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {

        private val mList = ArrayList<String>()

        init {
            mList.add("无")
            mList.add("晚樱物语")
            mList.add("裂玻璃")
            mList.add("裂地术")
            mList.add("平行世界")
            mList.add("控雨")
            mList.add("灵魂出窍")
            mList.add("复古录像机")
            mList.add("缩放模糊")
            mList.add("人像虚化")
            mList.add("夜景光斑")
            mList.add("怀旧电影")
            mList.add("光晕")
            mList.add("炫光")
            mList.add("光斑")
            mList.add("时间静止")
        }
    }

    private var mOrientationEventListener: CustomOrientationEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemAnimator = RotationItemAnimator()
        val adapter = RotationAdapter(mList)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.itemAnimator = itemAnimator
        recyclerView.adapter = adapter

        mOrientationEventListener = CustomOrientationEventListener(this)
        mOrientationEventListener!!.mAdapter = adapter
        mOrientationEventListener!!.mAnimator = itemAnimator
        mOrientationEventListener!!.mSize = mList.size
    }

    override fun onStart() {
        super.onStart()
        // 开启旋转监听
        mOrientationEventListener!!.enable()
    }

    override fun onPause() {
        super.onPause()
        // 关闭旋转监听
        mOrientationEventListener!!.disable()
    }
}