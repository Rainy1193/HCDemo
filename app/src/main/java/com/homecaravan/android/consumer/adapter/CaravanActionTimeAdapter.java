package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homecaravan.android.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CaravanActionTimeAdapter extends RecyclerView.Adapter<CaravanActionTimeAdapter.CaravanActionTimeHolder> {
    private ArrayList<String> mArrTime;
    private Context mContext;

    public CaravanActionTimeAdapter(ArrayList<String> mArrTime, Context mContext) {
        this.mArrTime = mArrTime;
        this.mContext = mContext;
    }

    @Override
    public CaravanActionTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.caravan_action_time_item, parent, false);
        return new CaravanActionTimeHolder(view);
    }

    @Override
    public void onBindViewHolder(CaravanActionTimeHolder holder, int position) {
        holder.mTime.setText(mArrTime.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrTime.size();
    }

    public class CaravanActionTimeHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTime)
        TextView mTime;

        public CaravanActionTimeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
