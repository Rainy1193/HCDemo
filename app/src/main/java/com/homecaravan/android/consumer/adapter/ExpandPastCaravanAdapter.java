package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.CaravanJson;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.ui.CircleImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExpandPastCaravanAdapter extends RecyclerView.Adapter<ExpandPastCaravanAdapter.ExpandPastAppointmentHolder> {

    private ArrayList<CaravanJson> mArrCaravan = new ArrayList<>();
    private Context mContext;

    public ExpandPastCaravanAdapter(Context mContext, ArrayList<CaravanJson> mArrCaravan) {
        this.mContext = mContext;
        this.mArrCaravan = mArrCaravan;
    }

    @Override
    public ExpandPastCaravanAdapter.ExpandPastAppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_past_appointment_item, parent, false);
        return new ExpandPastAppointmentHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpandPastCaravanAdapter.ExpandPastAppointmentHolder holder, int position) {
        CaravanJson item = mArrCaravan.get(position);
        if (item.getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(item.getListing().getListingImages().getImage()).asBitmap().fitCenter()
                    .placeholder(R.drawable.no_image_b)
                    .dontAnimate()
                    .into(holder.mImgAppointmentHome);
        }
        Glide.with(mContext.getApplicationContext()).load(item.getListing().getAgentAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(holder.mImgAppointmentAvatar);
        holder.mTvAppointmentAddress.setText(item.getListing().getAddress().getAddress1());
        holder.mTvAppointmentName.setText(item.getListing().getAgent());
        holder.mTvAppointmentTime.setText(Utils.getTimeShowing(item.getTimeFrom(), item.getTimeTo()));

    }

    @Override
    public int getItemCount() {
        return mArrCaravan.size();
    }

    public class ExpandPastAppointmentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgAppointmentHome)
        RoundedImageView mImgAppointmentHome;
        @Bind(R.id.tvAppointmentAddress)
        TextView mTvAppointmentAddress;
        @Bind(R.id.vAppointmentAction)
        View mVAppointmentAction;
        @Bind(R.id.rlReview)
        RelativeLayout mRlReview;
        @Bind(R.id.imgReview)
        ImageView mImgReview;
        @Bind(R.id.tvReview)
        TextView mTvReview;
        @Bind(R.id.imgAppointmentAvatar)
        CircleImageView mImgAppointmentAvatar;
        @Bind(R.id.tvAppointmentName)
        TextView mTvAppointmentName;
        @Bind(R.id.frmAppointmentMessage)
        FrameLayout mFrmAppointmentMessage;
        @Bind(R.id.tvAppointmentTime)
        TextView mTvAppointmentTime;
        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;

        public ExpandPastAppointmentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
