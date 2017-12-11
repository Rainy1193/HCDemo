package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IBottomSheetCIAListener;
import com.homecaravan.android.consumer.model.cia.ListingCIAMarker;
import com.homecaravan.android.consumer.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CaravanActionAdapter extends RecyclerView.Adapter<CaravanActionAdapter.CaravanHolder> {

    private ArrayList<ListingCIAMarker> mArrListing;
    private Context mContext;
    private IBottomSheetCIAListener mListener;

    public CaravanActionAdapter(ArrayList<ListingCIAMarker> mArrListing, Context mContext, IBottomSheetCIAListener mListener) {
        this.mArrListing = mArrListing;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public CaravanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.caravan_action_item, parent, false);
        return new CaravanHolder(view);
    }

    @Override
    public void onBindViewHolder(CaravanHolder holder, final int position) {
        final ListingCIAMarker caravan = mArrListing.get(position);
        if (caravan.getDestinations().getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(caravan.getDestinations().getListing().getListingImages().getImage())
                    .asBitmap().fitCenter().placeholder(R.drawable.no_image_b).dontAnimate().into(holder.mIvListing);
        }

        final boolean statusAppointment = "cancelled".equals(caravan.getDestinations().getAppointment().getStatus().getStatus());

        holder.mTvAddress.setText(caravan.getDestinations().getListing().getAddress().getAddress1());

        if(caravan.isStatus()){
            holder.mTime.setVisibility(View.GONE);
            holder.mImgStatus.setVisibility(View.VISIBLE);
            Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_caravan_action_1).asBitmap().dontAnimate().into(holder.mImgStatusTop);
            if(statusAppointment){
                Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_unavailable).asBitmap().dontAnimate().into(holder.mImgStatus);
            }else{
                Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_caravan_action_done_v).asBitmap().dontAnimate().into(holder.mImgStatus);
            }
        }else{
            if(statusAppointment){
                holder.mTime.setVisibility(View.GONE);
                holder.mImgStatus.setVisibility(View.VISIBLE);
                Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_unavailable).asBitmap().dontAnimate().into(holder.mImgStatus);
                Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_caravan_action_1).asBitmap().dontAnimate().into(holder.mImgStatusTop);
            }else{
                holder.mTime.setVisibility(View.VISIBLE);
                holder.mImgStatus.setVisibility(View.GONE);
                holder.mTime.setText(getTimeMetting(caravan.getDestinations().getTimeFrom().getData()));
                Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_caravan_action).asBitmap().dontAnimate().into(holder.mImgStatusTop);
            }
        }

        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!caravan.isStatus() && !statusAppointment) {
                    mListener.onItemListingClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class CaravanHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTime)
        TextView mTime;
        @Bind(R.id.tvAddress)
        TextView mTvAddress;
        @Bind(R.id.ivListing)
        ImageView mIvListing;
        @Bind(R.id.layoutItem)
        FrameLayout mLayoutItem;
        @Bind(R.id.imgStatus)
        ImageView mImgStatus;
        @Bind(R.id.imgStatusTop)
        ImageView mImgStatusTop;

        public CaravanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String getTimeMetting(String firstTime) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        return dateFormat.format(Utils.createDateFromString(firstTime));
    }
}
