package com.homecaravan.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.listitem.FriendListItem;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListHolder> {

    private ArrayList<FriendListItem> mArrFriend;
    private Context mContext;
    private IPickFriend mListener;

    public FriendListAdapter(ArrayList<FriendListItem> mArrFriend, Context mContext, IPickFriend mListener) {
        this.mArrFriend = mArrFriend;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public FriendListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.friend_list_item, null, false);
        return new FriendListHolder(view);
    }

    @Override
    public void onBindViewHolder(final FriendListHolder holder, final int position) {
        holder.mLayoutAvatar.setBackgroundResource(mArrFriend.get(position).getBgAvatar());
        holder.mTvAvatar.setText(mArrFriend.get(position).getShortName());
        if (!mArrFriend.get(position).getName().equalsIgnoreCase(mArrFriend.get(position).getPhone())) {
            holder.mTvName.setText(mArrFriend.get(position).getName());
            holder.mTvName.setVisibility(View.VISIBLE);
        } else {
            holder.mTvName.setVisibility(View.GONE);
        }
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrFriend.get(position).isPick()) {
                    mArrFriend.get(position).setPick(false);
                    mListener.pickFriend(position, false);
                    holder.mIvPick.setVisibility(View.GONE);
                } else {
                    mArrFriend.get(position).setPick(true);
                    mListener.pickFriend(position, true);
                    holder.mIvPick.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.mTvPhone.setText(mArrFriend.get(position).getPhone());
        if (mArrFriend.get(position).isPick()) {
            holder.mIvPick.setVisibility(View.VISIBLE);
        } else {
            holder.mIvPick.setVisibility(View.GONE);
        }
        holder.mLayoutPickFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrFriend.get(position).isPick()) {
                    mArrFriend.get(position).setPick(false);
                    mListener.pickFriend(position, false);
                    holder.mIvPick.setVisibility(View.GONE);
                } else {
                    mArrFriend.get(position).setPick(true);
                    mListener.pickFriend(position, true);
                    holder.mIvPick.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void updateList(ArrayList<FriendListItem> mArrFriend) {
        this.mArrFriend = mArrFriend;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArrFriend.size();
    }

    public class FriendListHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivFriend)
        CircleImageView mIvFriend;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.tvPhone)
        TextView mTvPhone;
        @Bind(R.id.layoutPickFriend)
        RelativeLayout mLayoutPickFriend;
        @Bind(R.id.ivPick)
        ImageView mIvPick;
        @Bind(R.id.tvAvatar)
        TextView mTvAvatar;
        @Bind(R.id.layoutAvatar)
        RelativeLayout mLayoutAvatar;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public FriendListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IPickFriend {
        void pickFriend(int position, boolean b);
    }
}
