package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactInvitedAdapter extends RecyclerView.Adapter<ContactInvitedAdapter.ContactInvitedHolder> {

    private ArrayList<ContactManagerData> mArrContact;
    private Context mContext;
    private IContactManager mListener;

    public ContactInvitedAdapter(ArrayList<ContactManagerData> mArrContact, Context mContext, IContactManager mListener) {
        this.mArrContact = mArrContact;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public ContactInvitedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_invited_item, null, false);
        return new ContactInvitedHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactInvitedHolder holder, final int position) {
        holder.mEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.editContact(mArrContact.get(position), position);
            }
        });
        if (!mArrContact.get(position).getName().isEmpty()) {
            holder.mTvName.setText(mArrContact.get(position).getName());
        } else {
            if (mArrContact.get(position).getPhone() != null) {
                holder.mTvName.setText(mArrContact.get(position).getPhone());
            } else {
                if (mArrContact.get(position).getEmail() != null) {
                    holder.mTvName.setText(mArrContact.get(position).getEmail());
                }
            }
        }
        Glide.with(mContext.getApplicationContext()).load(mArrContact.get(position).getAvatar())
                .asBitmap().fitCenter().placeholder(R.drawable.avatar_default)
                .dontAnimate().into(holder.mIvContact);
    }

    @Override
    public int getItemCount() {
        return mArrContact.size();
    }

    public class ContactInvitedHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivContact)
        CircleImageView mIvContact;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.ivEditContact)
        ImageView mEditContact;

        public ContactInvitedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
