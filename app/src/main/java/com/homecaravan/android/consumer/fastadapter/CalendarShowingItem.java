package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IPickDateListener;
import com.homecaravan.android.consumer.model.DayShowing;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CalendarShowingItem extends AbstractItem<CalendarShowingItem, CalendarShowingItem.CalendarShowingHolder> {

    private DayShowing mDayShowing;
    private Context mContext;
    private IPickDateListener mListener;
    private int mPosition;
    private CalendarShowingHolder mHolder;

    public DayShowing getDayShowing() {
        return mDayShowing;
    }

    public void setDayShowing(DayShowing mDayShowing) {
        this.mDayShowing = mDayShowing;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public IPickDateListener getListener() {
        return mListener;
    }

    public void setListener(IPickDateListener mListener) {
        this.mListener = mListener;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    public void bindView(CalendarShowingHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        this.mHolder = holder;
        holder.mDay.setText(String.valueOf(Integer.parseInt(mDayShowing.getDay())));
        if (mDayShowing.isInMonth()) {
            if (mDayShowing.isSelect()) {
                holder.mDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            } else {
                holder.mDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorMenuConsumer));
            }
        } else {
            if (mDayShowing.isSelect()) {
                holder.mDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            } else {
                holder.mDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            }
        }
        if (mDayShowing.isSelect()) {
            holder.mDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_calendar_showing));
        } else {
            holder.mDay.setBackground(null);
        }

        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.pickDate(mDayShowing.getFullDay());
            }
        });
        if (mDayShowing.isShowApproved()) {
            holder.mLayoutCaravan.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutCaravan.setVisibility(View.GONE);
        }
        if (mDayShowing.isShowCancelled()) {
            holder.mLayoutCanceled.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutCanceled.setVisibility(View.GONE);
        }
        if (mDayShowing.isShowPending()) {
            holder.mLayoutPending.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutPending.setVisibility(View.GONE);
        }
    }

    public void updateApproved(boolean b) {
        mDayShowing.setShowApproved(b);
        if (b) {
            mHolder.mLayoutCaravan.setVisibility(View.VISIBLE);
        } else {
            mHolder.mLayoutCaravan.setVisibility(View.GONE);
        }
    }

    public void updatePending(boolean b) {
        mDayShowing.setShowPending(b);
        if (b) {
            mHolder.mLayoutPending.setVisibility(View.VISIBLE);
        } else {
            mHolder.mLayoutPending.setVisibility(View.GONE);
        }
    }

    public void updateCancelled(boolean b) {
        mDayShowing.setShowCancelled(b);
        if (b) {
            mHolder.mLayoutCanceled.setVisibility(View.VISIBLE);
        } else {
            mHolder.mLayoutCanceled.setVisibility(View.GONE);
        }
    }

    @Override
    public CalendarShowingHolder getViewHolder(View v) {
        return new CalendarShowingHolder(v);
    }

    @Override
    public int getType() {
        return R.id.calendar_showing_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.calendar_showing_item;
    }

    public static class CalendarShowingHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvCalendar)
        TextView mDay;
        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.viewCanceled)
        RelativeLayout mLayoutCanceled;
        @Bind(R.id.viewCaravan)
        RelativeLayout mLayoutCaravan;
        @Bind(R.id.viewPending)
        RelativeLayout mLayoutPending;

        public CalendarShowingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
