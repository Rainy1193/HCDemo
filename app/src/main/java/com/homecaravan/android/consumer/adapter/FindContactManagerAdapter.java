package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindContactManagerAdapter extends RecyclerView.Adapter<FindContactManagerAdapter.ContactManagerHolder> {
    private IContactManager mListener;
    private ArrayList<ContactManagerData> mArrContact;
    private Context mContext;

    public FindContactManagerAdapter(ArrayList<ContactManagerData> mArrContact, Context mContext, IContactManager mListener) {
        this.mArrContact = mArrContact;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public FindContactManagerAdapter.ContactManagerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.find_contact_manager_item, null, false);
        return new ContactManagerHolder(view);
    }

    @Override
    public void onBindViewHolder(final FindContactManagerAdapter.ContactManagerHolder holder, final int position) {
        ContactManagerData contactData = mArrContact.get(position);
        Glide.with(mContext.getApplicationContext()).load(contactData.getAvatar()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default)
                .dontAnimate().into(holder.mIvContact);
        if (!contactData.getName().isEmpty()) {
            holder.mTvName.setText(contactData.getName());
        } else {
            if (contactData.getPhone() != null) {
                holder.mTvName.setText(contactData.getPhone());
            } else {
                holder.mTvName.setText(contactData.getEmail());
            }
        }
        if (contactData.isPick()) {
            holder.mIvPick.setVisibility(View.VISIBLE);
        } else {
            holder.mIvPick.setVisibility(View.GONE);
        }
        holder.mLayoutPickFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrContact.get(position).isPick()) {
                    mArrContact.get(position).setPick(false);
                    mListener.pickContact(mArrContact.get(position), mArrContact.get(position).getId(), position, false);
                    holder.mIvPick.setVisibility(View.GONE);
                } else {
                    mArrContact.get(position).setPick(true);
                    mListener.pickContact(mArrContact.get(position), mArrContact.get(position).getId(), position, true);
                    holder.mIvPick.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrContact.get(position).isPick()) {
                    mArrContact.get(position).setPick(false);
                    mListener.pickContact(mArrContact.get(position), mArrContact.get(position).getId(), position, false);
                    holder.mIvPick.setVisibility(View.GONE);
                } else {
                    mArrContact.get(position).setPick(true);
                    mListener.pickContact(mArrContact.get(position), mArrContact.get(position).getId(), position, true);
                    holder.mIvPick.setVisibility(View.VISIBLE);
                }
            }
        });
        if (position == mArrContact.size() - 1) {
            holder.mLayoutBottom.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutBottom.setVisibility(View.GONE);
        }
    }


    public void updateList(ArrayList<ContactManagerData> mArrContact) {
        this.mArrContact = mArrContact;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mArrContact.size();
    }


    public class ContactManagerHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivContact)
        CircleImageView mIvContact;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.tvRole)
        TextView mTvRole;
        @Bind(R.id.layoutPick)
        RelativeLayout mLayoutPickFriend;
        @Bind(R.id.ivPick)
        ImageView mIvPick;
        @Bind(R.id.layoutBottom)
        LinearLayout mLayoutBottom;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public ContactManagerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
