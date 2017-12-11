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
import com.homecaravan.android.consumer.listener.IBottomSheetCIAListener;
import com.homecaravan.android.consumer.model.cia.ParticipantsMarker;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anh Dao on 10/15/2017.
 */

public class CIAParticipantsInListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ParticipantsMarker> mArrParticipants;
    private IBottomSheetCIAListener mListener;
    private boolean isHasOnThroughAdapter;

    public CIAParticipantsInListingAdapter(Context context, ArrayList<ParticipantsMarker> mArrParticipants,
                                           IBottomSheetCIAListener mListener, boolean isHasOnThroughAdapter) {
        this.mContext = context;
        this.mArrParticipants = mArrParticipants;
        this.mListener = mListener;
        this.isHasOnThroughAdapter = isHasOnThroughAdapter;
    }

    @Override
    public CIAParticipantsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cia_bottom_sheet_item, null, false);
        return new CIAParticipantsHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ParticipantsMarker participant = mArrParticipants.get(position);
        CIAParticipantsHolder myHolder = (CIAParticipantsHolder) holder;

        Glide.with(mContext.getApplicationContext()).load(participant.getParticipants().getAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(myHolder.mImgAvatarParticipant);
        myHolder.mTvParticipantName.setText(participant.getParticipants().getFullName());

        myHolder.mLayoutParticipantItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isHasOnThroughAdapter)
                    mListener.onItemParticipantsClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrParticipants.size();
    }

    public class CIAParticipantsHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutParticipantItem)
        RelativeLayout mLayoutParticipantItem;
        @Bind(R.id.imgAvatarParticipant)
        RoundedImageView mImgAvatarParticipant;
        @Bind(R.id.tvParticipantName)
        TextView mTvParticipantName;
        @Bind(R.id.vStatus)
        View mVStatus;


        public CIAParticipantsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}