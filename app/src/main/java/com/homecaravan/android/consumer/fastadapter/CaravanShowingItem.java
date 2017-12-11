package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.CaravanInActionActivity;
import com.homecaravan.android.consumer.listener.IShowingListener;
import com.homecaravan.android.consumer.model.CurrentCaravan;
import com.homecaravan.android.consumer.model.responseapi.CaravanShowing;
import com.homecaravan.android.consumer.utils.Utils;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CaravanShowingItem extends AbstractItem<CaravanShowingItem, CaravanShowingItem.CaravanShowingHolder> {

    private SimpleDateFormat formatYYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
    private CaravanShowing mCaravan;
    private Context mContext;
    private IShowingListener mListener;
    private FastItemAdapter<AppointmentCaravanItem> mApptAdapter;
    private boolean mPast;

    public void setPast(boolean mPast) {
        this.mPast = mPast;
    }

    public void setApptAdapter(FastItemAdapter<AppointmentCaravanItem> mApptAdapter) {
        this.mApptAdapter = mApptAdapter;
    }

    public void setCaravan(CaravanShowing mCaravan) {
        this.mCaravan = mCaravan;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(IShowingListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void bindView(final CaravanShowingHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.mNameCaravan.setText(mCaravan.getTitle());
        holder.mTimeStart.setText(Utils.getTimeStartCaravan(mCaravan.getStart()));
        holder.mTimeEnd.setText(Utils.getTimeEndCaravan(mCaravan.getEnd()));

        if (mCaravan.getRefCaravan().getScheduleStatus().getApproved() != 0) {
            holder.mTvStatusCaravanApproved.setText(mCaravan.getRefCaravan().getScheduleStatus().getApproved() + "");
            holder.mTvStatusCaravanApproved.setVisibility(View.VISIBLE);
        }

        if (mCaravan.getRefCaravan().getScheduleStatus().getPending() != 0) {
            holder.mTvStatusCaravanPending.setText(mCaravan.getRefCaravan().getScheduleStatus().getPending() + "");
            holder.mTvStatusCaravanPending.setVisibility(View.VISIBLE);
        }
        if (mCaravan.getRefCaravan().getScheduleStatus().getCancelled() != 0) {
            holder.mTvStatusCaravanCancelled.setText(mCaravan.getRefCaravan().getScheduleStatus().getCancelled() + "");
            holder.mTvStatusCaravanCancelled.setVisibility(View.VISIBLE);
        }

        if (mCaravan.isExpand()) {
            holder.mRvExpandAppointment.setVisibility(View.VISIBLE);
            holder.mIvShow.setImageResource(R.drawable.ic_collapse_caravan);
        } else {
            holder.mRvExpandAppointment.setVisibility(View.GONE);
            holder.mIvShow.setImageResource(R.drawable.ic_expand_caravan);
        }
        holder.mTvStatusCaravanApproved.setText(String.valueOf(mCaravan.getRefCaravan().getScheduleStatus().getApproved()));
        holder.mTvStatusCaravanCancelled.setText(String.valueOf(mCaravan.getRefCaravan().getScheduleStatus().getCancelled()));
        holder.mTvStatusCaravanPending.setText(String.valueOf(mCaravan.getRefCaravan().getScheduleStatus().getPending()));
        holder.mTvStatusCaravanUndefined.setText(String.valueOf(mCaravan.getRefCaravan().getScheduleStatus().getUndefined()));

        holder.mLayoutShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mApptAdapter.getItemCount() == 0) {
                    return;
                }
                if (mCaravan.isExpand()) {
                    holder.mRvExpandAppointment.setVisibility(View.GONE);
                    holder.mIvShow.setImageResource(R.drawable.ic_expand_caravan);
                    mCaravan.setExpand(false);
                } else {
                    if (holder.mRvExpandAppointment.getAdapter() == null) {
                        holder.mRvExpandAppointment.setAdapter(mApptAdapter);
                        holder.mRvExpandAppointment.setLayoutManager(createLayoutManagerHorizontal());
                        holder.mRvExpandAppointment.setHasFixedSize(true);
                    }
                    holder.mRvExpandAppointment.setVisibility(View.VISIBLE);
                    holder.mIvShow.setImageResource(R.drawable.ic_collapse_caravan);
                    mCaravan.setExpand(true);
                }
            }
        });
        final boolean isCaravanTime = checkCaravanTime();
        if (!mPast) {
            if (isCaravanTime) {
                holder.mStatus.setText("In progress");
                holder.mStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
            } else {
                holder.mStatus.setText("Start soon");
                holder.mStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue_color_caravan_showing));
            }
        }
        holder.mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCaravan.isPast() && mApptAdapter.getItemCount() != 0 && isCaravanTime) {
                    CurrentCaravan.getInstance().setData(mCaravan.getRefCaravan());
//                    mListener.showCaravanAction();
                    mContext.startActivity(new Intent(mContext, CaravanInActionActivity.class));
                }
            }
        });

        holder.mLayoutEditCaravan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCaravan.isPast() && mApptAdapter.getItemCount() != 0 && !isCaravanTime) {
                    CurrentCaravan.getInstance().setData(mCaravan.getRefCaravan());
                    mListener.editCaravan();
                }
            }
        });
    }


    private LinearLayoutManager createLayoutManagerHorizontal() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public CaravanShowingHolder getViewHolder(View v) {
        return new CaravanShowingHolder(v);
    }

    @Override
    public int getType() {
        return R.id.caravan_showing_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.caravan_showing_item;
    }

    public static class CaravanShowingHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTimeStart)
        TextView mTimeStart;
        @Bind(R.id.layoutTimeStart)
        RelativeLayout mLayoutTimeStart;
        @Bind(R.id.tvTimeEnd)
        TextView mTimeEnd;
        @Bind(R.id.layoutTimeEnd)
        RelativeLayout mLayoutTimeEnd;
        @Bind(R.id.tvNameCaravan)
        TextView mNameCaravan;
        @Bind(R.id.tvStatus)
        TextView mStatus;
        @Bind(R.id.tvStatusCaravanApproved)
        TextView mTvStatusCaravanApproved;
        @Bind(R.id.tvStatusCaravanPending)
        TextView mTvStatusCaravanPending;
        @Bind(R.id.tvStatusCaravanCancelled)
        TextView mTvStatusCaravanCancelled;
        @Bind(R.id.tvStatusCaravanUndefined)
        TextView mTvStatusCaravanUndefined;
        @Bind(R.id.layoutEditCaravan)
        RelativeLayout mLayoutEditCaravan;
        @Bind(R.id.ivShow)
        ImageView mIvShow;
        @Bind(R.id.layoutShow)
        RelativeLayout mLayoutShow;
        @Bind(R.id.rvExpandAppointment)
        RecyclerView mRvExpandAppointment;

        private CaravanShowingHolder(View itemView) {
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
            date = formatYYYYMMDDHHMM.parse(mCaravan.getStart());
            caravanCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        caravanStartTime = caravanCalendar.getTimeInMillis() - 1800000; //before 30 minutes

        if (mCaravan.getEnd() != null) {
            try {
                date = formatYYYYMMDDHHMM.parse(mCaravan.getEnd());
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
