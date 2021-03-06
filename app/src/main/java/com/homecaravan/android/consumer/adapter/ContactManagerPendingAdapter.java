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

public class ContactManagerPendingAdapter extends RecyclerView.Adapter<ContactManagerPendingAdapter.ContactManagerHolder> {

    private ArrayList<ContactManagerData> mArrContact;
    private Context mContext;
    private IContactManager mListener;

    public ContactManagerPendingAdapter(ArrayList<ContactManagerData> mArrContact, Context mContext, IContactManager mListener) {
        this.mArrContact = mArrContact;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public ContactManagerPendingAdapter.ContactManagerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.contact_manager_pending_item, parent, false);
        return new ContactManagerHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactManagerPendingAdapter.ContactManagerHolder holder, final int position) {
        Glide.with(mContext.getApplicationContext()).load(mArrContact.get(position).getAvatar()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default)
                .dontAnimate().into(holder.mIvContact);
        ContactManagerData contactData = mArrContact.get(position);
        if (!contactData.getName().isEmpty()) {
            holder.mTvName.setText(contactData.getName());
        } else {
            if (contactData.getPhone() != null) {
                holder.mTvName.setText(contactData.getPhone());
            } else {
                holder.mTvName.setText(contactData.getEmail());
            }
        }
        holder.mIvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        @Bind(R.id.ivResend)
        ImageView mIvResend;
        @Bind(R.id.ivDelete)
        ImageView mIvDelete;
        @Bind(R.id.layoutChatCall)
        LinearLayout mLayoutChatCall;
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

