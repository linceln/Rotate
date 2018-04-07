package com.xyz.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private float mCurrentOrientation; // 记录当前所在的角度(0, 90, 180, -90)

    private OrientationEventListener mOrientationEventListener;

    private RecyclerView.Adapter mAdapter;

    private RotationItemAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOrientationEventListener = new OrientationEventListener(this) {

            @Override
            public void onOrientationChanged(int orientation) {
                if ((orientation > 350 || orientation < 10) && mCurrentOrientation != 0f) {
                    // 0度
                    mAnimator.setAngle(mCurrentOrientation, 0f);
                    mAnimator.setX(0f);
                    mAdapter.notifyItemRangeChanged(0, mList.size());
                    mCurrentOrientation = 0f;
                } else if ((orientation > 80 && orientation < 100) && mCurrentOrientation != -90f) {
                    // 90度
                    mAnimator.setAngle(mCurrentOrientation, -90f);
                    mAnimator.setX(-RotationItemAnimator.TRANSLATION_X);
                    mAdapter.notifyItemRangeChanged(0, mList.size());
                    mCurrentOrientation = -90f;
                } else if ((orientation > 170 && orientation < 190) && mCurrentOrientation != 180f) {
                    // 180度
                    mAnimator.setAngle(mCurrentOrientation, 180f);
                    mAnimator.setX(0f);
                    mAdapter.notifyItemRangeChanged(0, mList.size());
                    mCurrentOrientation = 180f;
                } else if ((orientation > 260 && orientation < 280) && mCurrentOrientation != 90f) {
                    // 270度
                    mAnimator.setAngle(mCurrentOrientation, 90f);
                    mAnimator.setX(RotationItemAnimator.TRANSLATION_X);
                    mAdapter.notifyItemRangeChanged(0, mList.size());
                    mCurrentOrientation = 90f;
                }
            }
        };

        mAdapter = new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_test, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

                TextView tv = holder.itemView.findViewById(R.id.textView);
                tv.setText(mList.get(position));

                refreshItemView(holder);
            }

            @Override
            public int getItemCount() {
                return mList.size();
            }
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mAnimator = new RotationItemAnimator();
        recyclerView.setItemAnimator(mAnimator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);
    }

    private void refreshItemView(@NonNull final RecyclerView.ViewHolder holder) {
        holder.itemView.post(new Runnable() {
            @Override
            public void run() {
                int padding = DpUtils.dp2px(MainActivity.this, RotationItemAnimator.PADDING);
                int translationX = DpUtils.dp2px(MainActivity.this, RotationItemAnimator.TRANSLATION_X);
                if (mCurrentOrientation == 0f) {
                    // 0度
                    holder.itemView.setPadding(0, 0, 0, 0);
                    holder.itemView.setRotation(0f);
                    holder.itemView.setTranslationX(0f);
                } else if (mCurrentOrientation == -90) {
                    // 90度
                    holder.itemView.setPadding(padding, 0, padding, 0);
                    holder.itemView.setRotation(-90f);
                    holder.itemView.setTranslationX(-translationX);
                } else if (mCurrentOrientation == 180f) {
                    // 180度
                    holder.itemView.setPadding(0, 0, 0, 0);
                    holder.itemView.setRotation(180f);
                    holder.itemView.setTranslationX(0f);
                } else if (mCurrentOrientation == 90f) {
                    // 270度
                    holder.itemView.setPadding(padding, 0, padding, 0);
                    holder.itemView.setRotation(90f);
                    holder.itemView.setTranslationX(translationX);
                }
            }
        });
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