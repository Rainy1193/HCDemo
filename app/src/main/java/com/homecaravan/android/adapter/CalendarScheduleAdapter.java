package com.homecaravan.android.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IPickDateListener;
import com.homecaravan.android.consumer.model.DaySchedule;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CalendarScheduleAdapter extends RecyclerView.Adapter<CalendarScheduleAdapter.CalendarScheduleHolder> {
    private ArrayList<DaySchedule> mArrDay;
    private Context mContext;
    private IPickDateListener mListener;

    public CalendarScheduleAdapter(ArrayList<DaySchedule> mArrDay, Context mContext, IPickDateListener mListener) {
        this.mArrDay = mArrDay;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public CalendarScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.calendar_schedule_item, null, false);
        return new CalendarScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarScheduleHolder holder, final int position) {
        if (mArrDay.get(position).isVisible()) {
            holder.mLayoutItem.setEnabled(true);
            holder.mLayoutItem.setClickable(true);
            holder.mDay.setText(String.valueOf(Integer.parseInt(mArrDay.get(position).getDay())));
            if (mArrDay.get(position).isInMonth()) {
                holder.mDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorMenuConsumer));
            } else {
                holder.mDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            }
            if (mArrDay.get(position).isSelect()) {
                holder.mDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_day_select));
            } else {
                holder.mDay.setBackground(null);
            }
        } else {
            holder.mLayoutItem.setEnabled(false);
            holder.mLayoutItem.setClickable(false);
        }
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.pickDate(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrDay.size();
    }

    public class CalendarScheduleHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvCalendar)
        TextView mDay;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public CalendarScheduleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
