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

    private float mCurrentAngle; // 记录当前所在的角度(0, 90, 180, -90)

    private int mOrientation; // 记录的当前角度(0, 90, 180, 270)

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
                if ((orientation > 350 || orientation < 10) && mOrientation != 0) {
                    // 0度
                    mAnimator.setAngle(mCurrentAngle, 0f);
                    mAnimator.setX(0f);
                    mAdapter.notifyItemRangeChanged(0, mList.size());
                    mOrientation = 0;
                    mCurrentAngle = 0;
                } else if ((orientation > 80 && orientation < 100) && mOrientation != 90) {
                    // 90度
                    mOrientation = 90;
                    mAnimator.setAngle(mCurrentAngle, -mOrientation);
                    mAnimator.setX(-RotationItemAnimator.TRANSLATION_X);
                    mAdapter.notifyItemRangeChanged(0, mList.size());
                    mCurrentAngle = -90;
                } else if ((orientation > 170 && orientation < 190) && mOrientation != 180) {
                    // 180度
                    mOrientation = 180;
                    mAnimator.setAngle(mCurrentAngle, mOrientation);
                    mAnimator.setX(0f);
                    mAdapter.notifyItemRangeChanged(0, mList.size());
                    mCurrentAngle = 180;
                } else if ((orientation > 260 && orientation < 280) && mOrientation != 270) {
                    // 270度
                    mOrientation = 270;
                    mAnimator.setAngle(mCurrentAngle, 360f - mOrientation);
                    mAnimator.setX(RotationItemAnimator.TRANSLATION_X);
                    mAdapter.notifyItemRangeChanged(0, mList.size());
                    mCurrentAngle = 90;
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

                holder.itemView.post(new Runnable() {
                    @Override
                    public void run() {
                        int padding = DpUtils.dp2px(MainActivity.this, RotationItemAnimator.PADDING);
                        if (mOrientation == 0) {
                            // 0度
                            holder.itemView.setPadding(0, 0, 0, 0);
                            holder.itemView.setRotation(0f);
                            holder.itemView.setTranslationX(0f);
                        } else if (mOrientation == 90) {
                            // 90度
                            holder.itemView.setPadding(padding, 0, padding, 0);
                            holder.itemView.setRotation(-90f);
                            holder.itemView.setTranslationX(-RotationItemAnimator.TRANSLATION_X);
                        } else if (mOrientation == 180) {
                             // 180度
                            holder.itemView.setPadding(0, 0, 0, 0);
                            holder.itemView.setRotation(180f);
                            holder.itemView.setTranslationX(0f);
                        } else if (mOrientation == 270) {
                            // 270度
                            holder.itemView.setPadding(padding, 0, padding, 0);
                            holder.itemView.setRotation(90f);
                            holder.itemView.setTranslationX(RotationItemAnimator.TRANSLATION_X);
                        }
                    }
                });
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