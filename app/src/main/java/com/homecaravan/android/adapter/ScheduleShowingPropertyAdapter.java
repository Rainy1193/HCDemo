package com.homecaravan.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IScheduleStepOneListener;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleShowingPropertyAdapter extends RecyclerView.Adapter<ScheduleShowingPropertyAdapter.SchedulePropertyHolder> {
    private ArrayList<ConsumerListingSchedule> mArrListing;
    private Context mContext;
    private IScheduleStepOneListener mListener;

    public ScheduleShowingPropertyAdapter(ArrayList<ConsumerListingSchedule> mArrListing, Context mContext, IScheduleStepOneListener mListener) {
        this.mArrListing = mArrListing;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public SchedulePropertyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_showing_property_item, null, false);
        return new SchedulePropertyHolder(view);
    }

    @Override
    public void onBindViewHolder(SchedulePropertyHolder holder, final int position) {

        ConsumerListingSchedule listingScheduler = mArrListing.get(position);
        if (listingScheduler.isStart()) {
            holder.mLayoutStartHere.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutStartHere.setVisibility(View.GONE);
        }
        if (listingScheduler.getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).
                    load(listingScheduler.getListing().getListingImages().getImage())
                    .asBitmap().fitCenter().placeholder(R.drawable.no_image_b).dontAnimate().into(holder.mIvListing);
        }
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.pickStart(position);
            }
        });
        holder.mAddress1.setText(listingScheduler.getListing().getAddress().getAddress1());
        holder.mAddress2.setText(listingScheduler.getListing().getAddress().getCity());
    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class SchedulePropertyHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.ivListing)
        RoundedImageView mIvListing;
        @Bind(R.id.tvAddress1)
        TextView mAddress1;
        @Bind(R.id.tvAddress2)
        TextView mAddress2;
        @Bind(R.id.layoutStartHere)
        LinearLayout mLayoutStartHere;

        public SchedulePropertyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
