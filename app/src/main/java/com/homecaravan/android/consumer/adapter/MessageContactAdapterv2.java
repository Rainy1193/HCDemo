package com.homecaravan.android.consumer.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.handling.ValidateData;
import com.homecaravan.android.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anh Dao on 11/24/2017.
 */

public class MessageContactAdapterv2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<ContactData> mArrMessageContact;
    private Picasso picasso;
    private OnItemClickListener mListener;

    public MessageContactAdapterv2(Context mContext, ArrayList<ContactData> mArrMessageContact, Picasso picasso) {
        this.mContext = mContext;
        this.mArrMessageContact = mArrMessageContact;
        this.picasso = picasso;
    }

    public void setOnClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_contact_item_v2, parent, false);
        return new MessageContactHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContactData data = mArrMessageContact.get(position);

        MessageContactHolder myHolder = (MessageContactHolder) holder;
        myHolder.mTvName.setText(data.getName());
        if (data.getEmail() != null && data.getEmail().isEmpty()) {
            myHolder.mTvCompany.setText(data.getPhone());
        } else {
            myHolder.mTvCompany.setText(data.getEmail());
        }

        String avatarUrl = data.getAvatar();
        if (avatarUrl != null) {
            if (avatarUrl.length() != 0) {
                String[] s = avatarUrl.split("/");
                if (s[s.length - 1].equals("avatar_default.jpg")) {
                    myHolder.mTvAvatarPersonal.setText(getFirstCharacter(data.getName()));
                    myHolder.mTvAvatarPersonal.setVisibility(View.VISIBLE);
                } else {
                    picasso.load(data.getAvatar()).fit().centerCrop().placeholder(R.drawable.avatar_default).into(myHolder.mImgAvatarPersonal);
                    myHolder.mTvAvatarPersonal.setVisibility(View.GONE);
                }
            } else {
                myHolder.mTvAvatarPersonal.setText(getFirstCharacter(data.getName()));
                myHolder.mTvAvatarPersonal.setVisibility(View.VISIBLE);
            }
        }

        myHolder.mImgContactMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(data);
            }
        });

        myHolder.mImgContactCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidateData.isPhone(data.getPhone())) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    String phoneNumber = "tel:" + data.getPhone();
                    callIntent.setData(Uri.parse(phoneNumber));
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        HomeCaravanApplication.askPermission((Activity) mContext, mContext, Manifest.permission.CALL_PHONE, 80);
                        return;
                    }
                    mContext.startActivity(callIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrMessageContact.size();
    }

    public class MessageContactHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgAvatarPersonal)
        CircleImageView mImgAvatarPersonal;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.tvCompany)
        TextView mTvCompany;
        @Bind(R.id.tvAvatarPersonal)
        TextView mTvAvatarPersonal;
        @Bind(R.id.imgContactMessage)
        ImageView mImgContactMessage;
        @Bind(R.id.imgContactCall)
        ImageView mImgContactCall;

        private MessageContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ContactData item);
    }

    private String getFirstCharacter(String name) {
        if (name == null || name.isEmpty())
            return name;
        String[] s = name.split(" ");
        String charName = "";
        for (String value : s) {
            charName += value.charAt(0);
        }
        return charName.toUpperCase();
    }
}
