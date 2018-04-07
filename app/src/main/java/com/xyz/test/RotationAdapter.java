package com.xyz.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RotationAdapter extends RecyclerView.Adapter {

    private List<String> mList = new ArrayList<>();

    private float mCurrentOrientation;

    public RotationAdapter(List<String> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setCurrentOrientation(float orientation){
        this.mCurrentOrientation = orientation;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_test, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView tv = holder.itemView.findViewById(R.id.textView);
        tv.setText(mList.get(position));

        refreshItemView(holder);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void refreshItemView(@NonNull final RecyclerView.ViewHolder holder) {

        final Context context = holder.itemView.getContext();
        final int padding = DpUtils.dp2px(context, RotationItemAnimator.PADDING);
        final int translationX = DpUtils.dp2px(context, RotationItemAnimator.TRANSLATION_X);

        holder.itemView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentOrientation == 0f) {
                    // 0度
                    holder.itemView.setPadding(0, 0, 0, 0);
                    holder.itemView.setRotation(0f);
                    holder.itemView.setTranslationX(0f);
                } else if (mCurrentOrientation == -90f) {
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
}