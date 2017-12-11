package com.homecaravan.android.consumer.fastadapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.AppointmentShowing;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.ui.CircleImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AppointmentShowingItem extends AbstractItem<AppointmentShowingItem, AppointmentShowingItem.AppointmentShowingHolder> {

    private AppointmentShowing mAppointment;
    private Context mContext;

    public void setAppointment(AppointmentShowing mAppointment) {
        this.mAppointment = mAppointment;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void bindView(AppointmentShowingHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        if (mAppointment.getRefAppointment().getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(mAppointment.getRefAppointment().getListing().getListingImages().getImage()).asBitmap().fitCenter()
                    .placeholder(R.drawable.no_image_b)
                    .dontAnimate()
                    .into(holder.mImgAppointmentHome);
        }
        Glide.with(mContext.getApplicationContext()).load(mAppointment.getRefAppointment().getAgent().getAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(holder.mImgAppointmentAvatar);
        holder.mTvAppointmentAddress.setText(mAppointment.getRefAppointment().getListing().getAddress().getAddress1());
        holder.mTvAppointmentName.setText(mAppointment.getRefAppointment().getAgent().getFullName());
        holder.mTvAppointmentDate.setText(Utils.getDateShowing(mAppointment.getStart()));
        holder.mTvAppointmentTime.setText(Utils.getTimeShowing(mAppointment.getStart(), mAppointment.getEnd()));
    }

    @Override
    public AppointmentShowingHolder getViewHolder(View v) {
        return new AppointmentShowingHolder(v);
    }

    @Override
    public int getType() {
        return R.id.appt_caravan_showing_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.single_appointment_item;
    }

    public class AppointmentShowingHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgAppointmentHome)
        RoundedImageView mImgAppointmentHome;
        @Bind(R.id.vAppointmentAction)
        View mVAppointmentAction;
        @Bind(R.id.tvAppointmentAddress)
        TextView mTvAppointmentAddress;
        @Bind(R.id.imgAppointmentAvatar)
        CircleImageView mImgAppointmentAvatar;
        @Bind(R.id.tvAppointmentName)
        TextView mTvAppointmentName;
        @Bind(R.id.frmAppointmentMessage)
        FrameLayout mFrmAppointmentMessage;
        @Bind(R.id.tvAppointmentDate)
        TextView mTvAppointmentDate;
        @Bind(R.id.tvAppointmentTime)
        TextView mTvAppointmentTime;
        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;

        public AppointmentShowingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
