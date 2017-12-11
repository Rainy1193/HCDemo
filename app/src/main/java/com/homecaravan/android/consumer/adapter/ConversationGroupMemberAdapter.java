package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ViewUserProfileConsumerActivity;
import com.homecaravan.android.consumer.model.message.MessageUserData;
import com.homecaravan.android.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConversationGroupMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<MessageUserData> mArrUserInGroup;
    private Picasso mPicasso;

    public ConversationGroupMemberAdapter(Context mContext, ArrayList<MessageUserData> mArrUserInGroup) {
        this.mContext = mContext;
        this.mArrUserInGroup = mArrUserInGroup;
        mPicasso = HomeCaravanApplication.getInstance().buildPicasso();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_group_member_item, parent, false);
        vh = new MemberHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageUserData user = mArrUserInGroup.get(position);
        MemberHolder myHolder = (MemberHolder) holder;

        mPicasso.load(user.getAvatar()).fit().centerCrop()
                .placeholder(R.drawable.avatar_default).into(myHolder.mImgAvatar);
        myHolder.mTvNamePersonal.setText(user.getName());

        myHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ViewUserProfileConsumerActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrUserInGroup.size();
    }

    public class MemberHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.imgAvatar)
        CircleImageView mImgAvatar;
        @Bind(R.id.vStatus)
        View mVStatus;
        @Bind(R.id.tvNamePersonal)
        TextView mTvNamePersonal;
        @Bind(R.id.tvJobTitlePersonal)
        TextView mTvJobTitlePersonal;

        private MemberHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
