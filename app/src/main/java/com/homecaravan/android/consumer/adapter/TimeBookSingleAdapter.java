package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.TimeBookSingle;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimeBookSingleAdapter extends RecyclerView.Adapter<TimeBookSingleAdapter.TimeBookSingleHolder> {
    private ArrayList<TimeBookSingle> mArrTime;
    private Context mContext;
    private PickTimeBookSingleListener mListener;

    public TimeBookSingleAdapter(ArrayList<TimeBookSingle> mArrTime, Context mContext, PickTimeBookSingleListener mListener) {
        this.mArrTime = mArrTime;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public TimeBookSingleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.time_book_single_item, null, false);
        return new TimeBookSingleHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeBookSingleHolder holder, final int position) {
        if (mArrTime.get(position).isSelected()) {
            holder.mTvTime.setVisibility(View.GONE);
            holder.mIvSelected.setVisibility(View.VISIBLE);
            holder.mLayoutItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_time_book_single_selected));
        } else {
            if (mArrTime.get(position).getStatus().equalsIgnoreCase("available")) {
                holder.mLayoutItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mArrTime.get(position).getStatus().equalsIgnoreCase("unavailable")) {
                holder.mLayoutItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mArrTime.get(position).getStatus().equalsIgnoreCase("open")) {
                holder.mLayoutItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mArrTime.get(position).getStatus().equalsIgnoreCase("pending")) {
                holder.mLayoutItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
            holder.mTvTime.setVisibility(View.VISIBLE);
            holder.mIvSelected.setVisibility(View.GONE);
        }
        holder.mTvTime.setText(mArrTime.get(position).getTime());
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrTime.get(position).isSelected()) {
                    mArrTime.get(position).setSelected(false);
                    mListener.pickTime(position, false);
                } else {
                    mArrTime.get(position).setSelected(true);
                    mListener.pickTime(position, true);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrTime.size();
    }

    public class TimeBookSingleHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.ivSelected)
        ImageView mIvSelected;
        @Bind(R.id.tvTime)
        TextView mTvTime;

        public TimeBookSingleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PickTimeBookSingleListener {
        void pickTime(int position, boolean b);
    }
}
