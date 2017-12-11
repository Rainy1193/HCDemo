package com.homecaravan.android.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewListingAdapter extends RecyclerView.Adapter<ReviewListingAdapter.ReviewListingHolder> {
    private ArrayList<ConsumerListingSchedule> mArrListing;
    private Context mContext;

    public ReviewListingAdapter(ArrayList<ConsumerListingSchedule> mArrListing, Context mContext) {
        this.mArrListing = mArrListing;
        this.mContext = mContext;
    }

    @Override
    public ReviewListingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_listing_item, parent, false);
        return new ReviewListingHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewListingHolder holder, int position) {
        ConsumerListingSchedule listing = mArrListing.get(position);
        if (listing.getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(listing.getListing().getListingImages().getImage()).asBitmap().fitCenter().placeholder(R.drawable.no_image_b).dontAnimate().into(holder.mIvListing);

        }
        holder.mAdd1.setText(listing.getListing().getAddress().getAddress1());
        holder.mAdd2.setText(listing.getListing().getAddress().getCity());
        holder.mTime.setText(Utils.getTimeRoute(listing.getStartHour(), listing.getStartMin(), listing.getStartHaft()) + " - " +
                Utils.getTimeRoute(listing.getEndHour(), listing.getEndMin(), listing.getEndHaft()));
        holder.mDuration.setText(String.valueOf(listing.getDuration()) + " mins");
        holder.mStep.setText(String.valueOf(listing.getPosition() + 1));
        if (position == 0) {
            holder.mLayoutStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_item_review_1));
        } else if (position == 1) {
            holder.mLayoutStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_item_review_2));
        } else {
            holder.mLayoutStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_item_review_3));
        }
    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class ReviewListingHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivListing)
        ImageView mIvListing;
        @Bind(R.id.tvAddress1)
        TextView mAdd1;
        @Bind(R.id.tvAddress2)
        TextView mAdd2;
        @Bind(R.id.layoutStep)
        RelativeLayout mLayoutStep;
        @Bind(R.id.tvStep)
        TextView mStep;
        @Bind(R.id.tvTime)
        TextView mTime;
        @Bind(R.id.tvDuration)
        TextView mDuration;

        public ReviewListingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
