package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.AppointmentShowing;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.ui.CircleImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AppointmentShowingAdapter extends RecyclerView.Adapter<AppointmentShowingAdapter.AppointmentHolder> {

    private ArrayList<AppointmentShowing> mArrAppointment = new ArrayList<>();
    private Context mContext;

    public AppointmentShowingAdapter(Context mContext, ArrayList<AppointmentShowing> mArrAppointment) {
        this.mContext = mContext;
        this.mArrAppointment = mArrAppointment;
    }

    @Override
    public AppointmentShowingAdapter.AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_appointment_item, parent, false);
        return new AppointmentHolder(v);
    }


    @Override
    public void onBindViewHolder(AppointmentShowingAdapter.AppointmentHolder holder, int position) {
        AppointmentShowing appointment = mArrAppointment.get(position);
        if (appointment.getRefAppointment().getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(appointment.getRefAppointment().getListing().getListingImages().getImage()).asBitmap().fitCenter()
                    .placeholder(R.drawable.no_image_b)
                    .dontAnimate()
                    .into(holder.mImgAppointmentHome);
        }
        Glide.with(mContext.getApplicationContext()).load(appointment.getRefAppointment().getAgent().getAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(holder.mImgAppointmentAvatar);
        holder.mTvAppointmentAddress.setText(appointment.getRefAppointment().getListing().getAddress().getAddress1());
        holder.mTvAppointmentName.setText(appointment.getRefAppointment().getAgent().getFullName());
        holder.mTvAppointmentDate.setText(Utils.getDateShowing(appointment.getStart()));
        holder.mTvAppointmentTime.setText(Utils.getTimeShowing(appointment.getStart(), appointment.getEnd()));
    }

    @Override
    public int getItemCount() {
        return mArrAppointment.size();
    }

    public class AppointmentHolder extends RecyclerView.ViewHolder {

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

        public AppointmentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
