package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.ISchedulePropertyListener;
import com.homecaravan.android.consumer.model.EventListingDetail;
import com.homecaravan.android.consumer.model.responseapi.Listing;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleConsumerAdapter extends RecyclerView.Adapter<ScheduleConsumerAdapter.ScheduleHolder> {
    private Context mContext;
    private ArrayList<Listing> mArrListing;
    private ISchedulePropertyListener mListener;

    public ScheduleConsumerAdapter(Context mContext, ArrayList<Listing> mArrListing, ISchedulePropertyListener mListener) {
        this.mContext = mContext;
        this.mArrListing = mArrListing;
        this.mListener = mListener;
    }

    @Override
    public ScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_consumer_item, null, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleHolder holder, final int position) {
        holder.mCity.setText(mArrListing.get(position).getAddress().getAddress1());
        holder.mIvRemoveListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.removeSchedule(mArrListing.get(position), position);
            }
        });
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventListingDetail(mArrListing.get(position).getId()));
            }
        });
        if (mArrListing.get(position).getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(mArrListing.get(position).getListingImages().getImage()).asBitmap().fitCenter()
                    .placeholder(R.drawable.ic_placeholder_listing_consumer).dontAnimate().into(holder.mIvListing);
        }
    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.ivListing)
        ImageView mIvListing;
        @Bind(R.id.ivRemoveListing)
        ImageView mIvRemoveListing;
        @Bind(R.id.tvCity)
        TextView mCity;

        public ScheduleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
