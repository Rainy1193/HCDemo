package com.homecaravan.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewListingMapAdapter extends RecyclerView.Adapter<ReviewListingMapAdapter.ReviewListingMapHolder> {
    private ArrayList<ConsumerListingSchedule> mArrListing;
    private Context mContext;

    public ReviewListingMapAdapter(ArrayList<ConsumerListingSchedule> mArrListing, Context mContext) {
        this.mArrListing = mArrListing;
        this.mContext = mContext;
    }

    @Override
    public ReviewListingMapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_listing_map_item, parent, false);
        return new ReviewListingMapHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewListingMapHolder holder, int position) {
        ConsumerListingSchedule listing = mArrListing.get(position);
        if (listing.getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(listing.getListing().getListingImages().getImage()).asBitmap().fitCenter().placeholder(R.drawable.no_image_b).dontAnimate().into(holder.mIvListing);
        }
        holder.mAdd1.setText(listing.getListing().getAddress().getAddress1());
        holder.mAdd2.setText(listing.getListing().getAddress().getCity());
        holder.mTime.setText(Utils.getTimeRoute(listing.getStartHour(), listing.getStartMin(), listing.getStartHaft()) + " - " +
                Utils.getTimeRoute(listing.getEndHour(), listing.getEndMin(), listing.getEndHaft()));
        holder.mDuration.setText(String.valueOf(listing.getDuration()) + " mins");
        holder.mPosition.setText(String.valueOf(listing.getPosition() + 1));
    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class ReviewListingMapHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivListing)
        ImageView mIvListing;
        @Bind(R.id.tvAddress1)
        TextView mAdd1;
        @Bind(R.id.tvAddress2)
        TextView mAdd2;
        @Bind(R.id.tvTime)
        TextView mTime;
        @Bind(R.id.tvDuration)
        TextView mDuration;
        @Bind(R.id.tvPosition)
        TextView mPosition;

        public ReviewListingMapHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
