package com.homecaravan.android.consumer.fastadapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IShowingListener;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.ui.CircleImageView;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AppointmentCaravanItem extends AbstractItem<AppointmentCaravanItem, AppointmentCaravanItem.AppointmentCaravanHolder> {

    private ResponseCaravan.Appointment mAppointment;
    private Context mContext;
    private int mPosition;
    private String caravanId;
    private IShowingListener mListener;

    public void setDestinations(ResponseCaravan.Appointment mAppointment) {
        this.mAppointment = mAppointment;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void setListener(IShowingListener mListener) {
        this.mListener = mListener;
    }

    public void setCaravanId(String caravanId) {
        this.caravanId = caravanId;
    }

    @Override
    public void bindView(AppointmentCaravanHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        Glide.with(mContext.getApplicationContext()).load(mAppointment.getListing().getAgentAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(holder.mAvatarAgent);
        holder.mNameAgent.setText(mAppointment.getListing().getAgentName());
        if (mAppointment.getTimeFrom() != null && mAppointment.getTimeTo() != null) {
            holder.mTvTime.setText(Utils.getTimeShowing(mAppointment.getTimeFrom().getDate(), mAppointment.getTimeTo().getDate()));
        }
        holder.mAddress.setText(mAppointment.getListing().getAddress().getFullAddress().getOneLine());
        String status = mAppointment.getStatus().getStatus();
        if ("approved".equals(status)) {
            holder.mTvStatus.setText("Approved");
            holder.mTvStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_appt_caravan_approved));
        } else if ("pending".equals(status)) {
            holder.mTvStatus.setText("Pending");
            holder.mTvStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_appt_caravan_pending));
        } else if ("canceled".equals(status)) {
            holder.mTvStatus.setText("Cancelled");
            holder.mTvStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_appt_caravan_canceled));
        }

        holder.mFrmAppointmentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListener.onMessageClicked(mAppointment.getId(), mAppointment.getListing().getId(),
//                        caravanId, mAppointment.getListing().getAgentName(), mAppointment.getListing().getAgentId());
            }
        });
    }

    @Override
    public AppointmentCaravanHolder getViewHolder(View v) {
        return new AppointmentCaravanHolder(v);

    }

    @Override
    public int getType() {
        return R.id.appt_caravan_showing_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.appointment_caravain_item;
    }

    public static class AppointmentCaravanHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTime)
        TextView mTvTime;
        @Bind(R.id.tvAddress)
        TextView mAddress;
        @Bind(R.id.tvStatus)
        TextView mTvStatus;
        @Bind(R.id.imgAppointmentAvatar)
        CircleImageView mAvatarAgent;
        @Bind(R.id.tvAgent)
        TextView mNameAgent;
        @Bind(R.id.frmAppointmentMessage)
        RelativeLayout mFrmAppointmentMessage;

        public AppointmentCaravanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
