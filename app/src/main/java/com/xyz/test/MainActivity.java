package com.xyz.test;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private int mCurrentAngle;

    private int mOrientation;

    private OrientationEventListener mOrientationEventListener;

    private RecyclerView.Adapter mAdapter;

    private DividerItemDecoration mItemDecoration;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOrientation = 270;
        mOrientationEventListener = new OrientationEventListener(this) {

            @Override
            public void onOrientationChanged(int orientation) {
                if ((orientation > 350 || orientation < 10) && mOrientation != 0) {
                    // 0度
//                    mRecyclerView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mRecyclerView.removeItemDecoration(mItemDecoration);
//                        }
//                    });
                    mAdapter.notifyItemRangeChanged(0, 10);
                    mOrientation = 0;
                    mCurrentAngle = 0;
                } else if ((orientation > 80 && orientation < 100) && mOrientation != 90) {
                    // 90度
//                    mRecyclerView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mRecyclerView.addItemDecoration(mItemDecoration);
//                        }
//                    });
                    mAdapter.notifyItemRangeChanged(0, 10);
                    mOrientation = 90;
                    mCurrentAngle = -90;
                } else if ((orientation > 170 && orientation < 190) && mOrientation != 180) {
                    // 180度
//                    mRecyclerView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mRecyclerView.removeItemDecoration(mItemDecoration);
//                        }
//                    });
                    mAdapter.notifyItemRangeChanged(0, 10);
                    mOrientation = 180;
                    mCurrentAngle = 180;
                } else if ((orientation > 260 && orientation < 280) && mOrientation != 270) {
                    // 270度
//                    mRecyclerView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mRecyclerView.addItemDecoration(mItemDecoration);
//                        }
//                    });
                    mAdapter.notifyItemRangeChanged(0, 10);
                    mOrientation = 270;
                    mCurrentAngle = 90;
                }
            }
        };

        mAdapter = new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_test, parent, false);
                RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(view) {
                };
                viewHolder.setIsRecyclable(true);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

                if (mOrientation == 0) {
                    startPropertyAnim(holder.itemView, mCurrentAngle, 0f);
                } else if (mOrientation == 90) {
                    startPropertyAnim(holder.itemView, mCurrentAngle, -90f);
                } else if (mOrientation == 180) {
                    startPropertyAnim(holder.itemView, mCurrentAngle, 180f);
                } else if (mOrientation == 270) {
                    startPropertyAnim(holder.itemView, mCurrentAngle, 90f);
                }
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        };

        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
//        layout.setInitialPrefetchItemCount(20);
        mRecyclerView.setLayoutManager(layout);
        mItemDecoration = new DividerItemDecoration(this, layout.getOrientation());
        // TODO Transparent Divider
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mOrientationEventListener.enable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOrientationEventListener.disable();
    }

    private void startPropertyAnim(View view, float start, float end) {
        // 0f -> 360f，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", start, end);
        anim.setDuration(300);
        anim.start();
    }
}