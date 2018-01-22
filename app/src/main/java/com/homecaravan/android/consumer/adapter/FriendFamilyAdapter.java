package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendFamilyAdapter extends RecyclerView.Adapter<FriendFamilyAdapter.FriendFamilyHolder> {
    private ArrayList<ContactManagerData> mArrContact;
    private Context mContext;

    public FriendFamilyAdapter(ArrayList<ContactManagerData> mArrContact, Context mContext) {
        this.mArrContact = mArrContact;
        this.mContext = mContext;
    }

    @Override
    public FriendFamilyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.friend_family_item, null, false);
        return new FriendFamilyHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendFamilyHolder friendFamilyHolder, int i) {
        Glide.with(mContext.getApplicationContext()).load(mArrContact.get(i).getAvatar()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default)
                .dontAnimate().into(friendFamilyHolder.mAvatar);
        ContactManagerData contactData = mArrContact.get(i);
        if (!contactData.getName().isEmpty()) {
            friendFamilyHolder.mName.setText(contactData.getName());
        } else {
            if (contactData.getPhone() != null) {
                friendFamilyHolder.mName.setText(contactData.getPhone());
            } else {
                friendFamilyHolder.mName.setText(contactData.getEmail());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mArrContact.size();
    }

    public class FriendFamilyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivAvatar)
        RoundedImageView mAvatar;
        @Bind(R.id.tvName)
        TextView mName;

        public FriendFamilyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
