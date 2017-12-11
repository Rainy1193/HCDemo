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
import com.homecaravan.android.consumer.listener.IPickDateSingleBookListener;
import com.homecaravan.android.consumer.model.DayBookSingle;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CalendarBookSingleAdapter extends RecyclerView.Adapter<CalendarBookSingleAdapter.CalendarBookHolder> {
    private ArrayList<DayBookSingle> mArrDay;
    private Context mContext;
    private IPickDateSingleBookListener mListener;

    public CalendarBookSingleAdapter(ArrayList<DayBookSingle> mArrDay, Context mContext, IPickDateSingleBookListener mListener) {
        this.mArrDay = mArrDay;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public CalendarBookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.calendar_book_single_item, null, false);
        return new CalendarBookHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarBookHolder holder, final int position) {
        holder.mDay.setText(String.valueOf(Integer.parseInt(mArrDay.get(position).getDay())));
        if (mArrDay.get(position).isSelect()) {
            holder.mDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            holder.mDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_select));
        } else {
            if (mArrDay.get(position).getStatus().equalsIgnoreCase("available")) {
                holder.mDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mArrDay.get(position).getStatus().equalsIgnoreCase("unavailable")) {
                holder.mDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mArrDay.get(position).getStatus().equalsIgnoreCase("open")) {
                holder.mDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mArrDay.get(position).getStatus().equalsIgnoreCase("pending")) {
                holder.mDay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
            holder.mDay.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrDay.get(position).getStatus().equalsIgnoreCase("unavailable")) {
                    mListener.pickDate(position, false);
                } else {
                    mListener.pickDate(position, true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrDay.size();
    }

    public class CalendarBookHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvCalendar)
        TextView mDay;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public CalendarBookHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
