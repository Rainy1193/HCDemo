package com.homecaravan.android.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.listitem.FriendListItem;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendListPickAdapter extends RecyclerView.Adapter<FriendListPickAdapter.FriendListPickHolder> {
    private ArrayList<FriendListItem> mArrFriend;
    private Context mContext;

    public FriendListPickAdapter(ArrayList<FriendListItem> mArrFriend, Context mContext) {
        this.mArrFriend = mArrFriend;
        this.mContext = mContext;
    }

    @Override
    public FriendListPickHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.friend_list_pick_item, null, false);
        return new FriendListPickHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendListPickHolder holder, int position) {
        holder.mLayoutAvatar.setBackgroundResource(mArrFriend.get(position).getBgAvatar());
        holder.mTvAvatar.setText(mArrFriend.get(position).getShortName());
    }

    @Override
    public int getItemCount() {
        return mArrFriend.size();
    }

    public class FriendListPickHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvAvatar)
        TextView mTvAvatar;
        @Bind(R.id.layoutAvatar)
        RelativeLayout mLayoutAvatar;
        @Bind(R.id.ivFriend)
        CircleImageView mIvFriend;

        public FriendListPickHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
