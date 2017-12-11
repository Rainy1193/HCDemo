package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IScheduleStepTwoListener;
import com.homecaravan.android.consumer.listener.ISwapRouteListener;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.myinterface.ItemTouchHelperAdapter;
import com.homecaravan.android.myinterface.ItemTouchHelperViewHolder;
import com.homecaravan.android.myinterface.OnStartDragListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleRoutePropertyAdapter extends RecyclerView.Adapter<ScheduleRoutePropertyAdapter.RouterPropertyHolder>
        implements ItemTouchHelperAdapter {
    private ArrayList<ConsumerListingSchedule> mArrListing;
    private Context mContext;
    private IScheduleStepTwoListener mListener;
    private OnStartDragListener mDragStartListener;
    private ISwapRouteListener mSwapListener;

    public ScheduleRoutePropertyAdapter(ArrayList<ConsumerListingSchedule> mArrListing, Context mContext,
                                        IScheduleStepTwoListener mListener, OnStartDragListener mDragStartListener,
                                        ISwapRouteListener mSwapListener) {
        this.mArrListing = mArrListing;
        this.mContext = mContext;
        this.mListener = mListener;
        this.mDragStartListener = mDragStartListener;
        this.mSwapListener = mSwapListener;
    }


    @Override
    public RouterPropertyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_route_propety_item, parent, false);
        return new RouterPropertyHolder(view);
    }

    @Override
    public void onBindViewHolder(final RouterPropertyHolder holder, final int position) {
        ConsumerListingSchedule listing = mArrListing.get(position);
        if (listing.getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).
                    load(listing.getListing().getListingImages().getImage())
                    .asBitmap().fitCenter().placeholder(R.drawable.ic_placeholder_listing_consumer).dontAnimate().into(holder.mIvListing);
        }
        holder.mPosition.setText(String.valueOf(listing.getPosition() + 1));
        holder.mAddress1.setText(listing.getListing().getAddress().getAddress1());
        holder.mStartTime.setText(Utils.getTimeRoute(listing.getStartHour(), listing.getStartMin(), listing.getStartHaft()));
        holder.mEndTime.setText(Utils.getTimeRoute(listing.getEndHour(), listing.getEndMin(), listing.getEndHaft()));
        if (listing.isSelect()) {
            holder.mLayoutMain.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_route_property));
        } else {
            holder.mLayoutMain.setBackground(null);
        }
        holder.mLayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.openSetTime(position);
            }
        });
        holder.mLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mLayoutItem.setEnabled(false);
                holder.mLayoutItem.setClickable(false);
                mListener.setSelect(position);
                holder.mLayoutItem.setEnabled(true);
                holder.mLayoutItem.setClickable(true);
            }
        });
        holder.mLayoutItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition != toPosition) {
            Collections.swap(mArrListing, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            mSwapListener.swapRoute(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    public class RouterPropertyHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.layoutMain)
        LinearLayout mLayoutMain;
        @Bind(R.id.tvStartTime)
        TextView mStartTime;
        @Bind(R.id.tvEndTime)
        TextView mEndTime;
        @Bind(R.id.ivListing)
        RoundedImageView mIvListing;
        @Bind(R.id.tvPosition)
        TextView mPosition;
        @Bind(R.id.tvAddress1)
        TextView mAddress1;
        @Bind(R.id.layoutTime)
        LinearLayout mLayoutTime;

        public RouterPropertyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onItemSelected() {
            AnimUtils.scaleRouteDrag(itemView, 1f, 1.1f, 1f, 1.1f);
            AnimUtils.fadeView(itemView, 0.8f);
        }

        @Override
        public void onItemClear() {
            AnimUtils.scaleRouteDrag(itemView, 1.1f, 1f, 1.1f, 1f);
            AnimUtils.fadeView(itemView, 1f);
            mDragStartListener.onEndDrag(this);
        }
    }
}
