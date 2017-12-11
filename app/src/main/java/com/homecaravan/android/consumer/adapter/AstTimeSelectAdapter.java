package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.AstTime;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AstTimeSelectAdapter extends RecyclerView.Adapter<AstTimeSelectAdapter.AstTimeSelectHolder> {
    private ArrayList<AstTime> mArrTime;
    private Context mContext;

    public AstTimeSelectAdapter(ArrayList<AstTime> mArrTime, Context mContext) {
        this.mArrTime = mArrTime;
        this.mContext = mContext;
    }

    @Override
    public AstTimeSelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ast_time_select_item, null, false);
        return new AstTimeSelectHolder(view);
    }

    @Override
    public void onBindViewHolder(AstTimeSelectHolder holder, int position) {
        holder.mTvTime.setText(mArrTime.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mArrTime.size();
    }

    public class AstTimeSelectHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv)
        ImageView mImageView;
        @Bind(R.id.tvTime)
        TextView mTvTime;

        public AstTimeSelectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
