package com.homecaravan.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ContactModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactInviteAdapter extends RecyclerView.Adapter<ContactInviteAdapter.ContactHolder> {
    private Context mContext;
    private ArrayList<ContactModel> mArrContact;
    private ContactListener mListener;

    public ContactInviteAdapter(Context mContext, ArrayList<ContactModel> mArrContact, ContactListener mListener) {
        this.mContext = mContext;
        this.mArrContact = mArrContact;
        this.mListener = mListener;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, final int position) {
        holder.mLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.pickContact(position);
            }
        });
        holder.mName.setText(mArrContact.get(position).getName());
        holder.mPhone.setText(mArrContact.get(position).getPhone());
    }


    public void updateContact(ArrayList<ContactModel> contacts) {
        this.mArrContact = contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArrContact.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvNameContact)
        TextView mName;
        @Bind(R.id.tvPhoneContact)
        TextView mPhone;
        @Bind(R.id.layoutMain)
        LinearLayout mLayoutMain;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ContactListener {
        void pickContact(int position);
    }
}
