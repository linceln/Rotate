package com.xyz.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final ArrayList<String> mList = new ArrayList<>();

    static {
        mList.add("无");
        mList.add("晚樱物语");
        mList.add("裂玻璃");
        mList.add("裂地术");
        mList.add("平行世界");
        mList.add("控雨");
        mList.add("灵魂出窍");
        mList.add("复古录像机");
        mList.add("缩放模糊");
        mList.add("人像虚化");
        mList.add("夜景光斑");
        mList.add("怀旧电影");
        mList.add("光晕");
        mList.add("炫光");
        mList.add("光斑");
        mList.add("时间静止");
    }

    private CustomOrientationEventListener mOrientationEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RotationItemAnimator itemAnimator = new RotationItemAnimator();
        RotationAdapter adapter = new RotationAdapter(mList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(adapter);

        mOrientationEventListener = new CustomOrientationEventListener(this);
        mOrientationEventListener.setRecyclerAdapter(adapter);
        mOrientationEventListener.setRecyclerItemAnimator(itemAnimator);
        mOrientationEventListener.setListSize(mList.size());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 开启旋转监听
        mOrientationEventListener.enable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 关闭旋转监听
        mOrientationEventListener.disable();
    }
}