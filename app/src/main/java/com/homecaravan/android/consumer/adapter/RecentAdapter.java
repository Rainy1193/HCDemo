package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentHolder> {
    private ArrayList<ContactData> mArrRecent;
    private Context mContext;

    public RecentAdapter(ArrayList<ContactData> mArrRecent, Context mContext) {
        this.mArrRecent = mArrRecent;
        this.mContext = mContext;
    }

    @Override
    public RecentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recent_item, null, false);
        return new RecentHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(R.drawable.sana).asBitmap().fitCenter().placeholder(R.drawable.avatar_default).dontAnimate().into(holder.mIvContact);
        if ((mArrRecent.get(position).getName() != null)) {
            holder.mTvNameContact.setText(mArrRecent.get(position).getName());
        } else {
            if ((mArrRecent.get(position).getPhone() != null)) {
                holder.mTvNameContact.setText(mArrRecent.get(position).getPhone());
            } else {
                holder.mTvNameContact.setText(mArrRecent.get(position).getEmail());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mArrRecent.size();
    }

    public class RecentHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivContact)
        CircleImageView mIvContact;
        @Bind(R.id.tvName)
        TextView mTvNameContact;

        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public RecentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
