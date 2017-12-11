package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IShowingListener;
import com.homecaravan.android.consumer.model.responseapi.AppointmentShowing;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.ui.CircleImageView;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleAppointmentItem extends AbstractItem<SingleAppointmentItem, SingleAppointmentItem.AppointmentShowingHolder> {
    private SimpleDateFormat formatYYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
    private AppointmentShowing mAppointment;
    private Context mContext;
    private IShowingListener mListener;


    public void setAppointment(AppointmentShowing mAppointment) {
        this.mAppointment = mAppointment;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(IShowingListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void bindView(final AppointmentShowingHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.mTimeStart.setText(Utils.getTimeStartCaravan(mAppointment.getStart()));
        holder.mTimeEnd.setText(Utils.getTimeEndCaravan(mAppointment.getEnd()));
        holder.mAdd1.setText(mAppointment.getRefAppointment().getListing().getAddress().getAddress1());
        holder.mAdd2.setText(mAppointment.getRefAppointment().getListing().getAddress().getAddress2());
        Glide.with(mContext.getApplicationContext()).load(mAppointment.getRefAppointment().getListing().getListingImages().getImage()).asBitmap().fitCenter()
                .placeholder(R.drawable.ic_placeholder_listing_consumer)
                .dontAnimate()
                .into(holder.mIvListing);
        if (mAppointment.getEventStatus().equalsIgnoreCase("pending")) {
            holder.mLayoutTimeStart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_appt_caravan_pending));
            holder.mLayoutTimeEnd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_appt_caravan_pending1));
        }
        if (mAppointment.getEventStatus().equalsIgnoreCase("canceled")) {
            holder.mLayoutTimeStart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_appt_caravan_canceled));
            holder.mLayoutTimeEnd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_appt_caravan_canceled1));
        }
        final boolean isCaravanTime = checkCaravanTime();
        if (isCaravanTime) {
            holder.mTvStatus.setText("In progress");
            holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
        } else {
            holder.mTvStatus.setText("Start soon");
            holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue_color_caravan_showing));
        }
        Glide.with(mContext.getApplicationContext()).load(mAppointment.getRefAppointment().getAgent().getAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(holder.mIvAgent);
        holder.mTvAgent.setText(mAppointment.getRefAppointment().getAgent().getFullName());
        holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.mLayoutChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public AppointmentShowingHolder getViewHolder(View v) {
        return new AppointmentShowingHolder(v);
    }

    @Override
    public int getType() {
        return R.id.appt_showing_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.single_appointment_showing_item;
    }

    public static class AppointmentShowingHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivListing)
        ImageView mIvListing;
        @Bind(R.id.tvTimeStart)
        TextView mTimeStart;
        @Bind(R.id.layoutTimeStart)
        RelativeLayout mLayoutTimeStart;
        @Bind(R.id.tvTimeEnd)
        TextView mTimeEnd;
        @Bind(R.id.layoutTimeEnd)
        RelativeLayout mLayoutTimeEnd;
        @Bind(R.id.tvAdd1)
        TextView mAdd1;
        @Bind(R.id.tvAdd2)
        TextView mAdd2;
        @Bind(R.id.imgAppointmentAvatar)
        CircleImageView mIvAgent;
        @Bind(R.id.tvAgent)
        TextView mTvAgent;
        @Bind(R.id.tvStatus)
        TextView mTvStatus;
        @Bind(R.id.layoutEditCaravan)
        RelativeLayout mLayoutEdit;
        @Bind(R.id.layoutChat)
        RelativeLayout mLayoutChat;

        private AppointmentShowingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private boolean checkCaravanTime() {
        Calendar caravanCalendar = Calendar.getInstance();
        long currentTime = caravanCalendar.getTimeInMillis();
        long caravanStartTime;
        long caravanEndTime;
        Date date;
        try {
            date = formatYYYYMMDDHHMM.parse(mAppointment.getStart());
            caravanCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        caravanStartTime = caravanCalendar.getTimeInMillis() - 1800000; //before 30 minutes

        if (mAppointment.getEnd() != null) {
            try {
                date = formatYYYYMMDDHHMM.parse(mAppointment.getEnd());
                caravanCalendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            caravanEndTime = caravanCalendar.getTimeInMillis();
            return caravanStartTime < currentTime && currentTime < caravanEndTime;
        } else {
            return caravanStartTime < currentTime && currentTime < caravanStartTime + 1800000;
        }
    }

}
