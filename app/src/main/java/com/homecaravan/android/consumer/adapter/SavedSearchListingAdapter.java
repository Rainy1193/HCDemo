package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.EventListingDetail;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedSearchListingAdapter extends RecyclerView.Adapter<SavedSearchListingAdapter.SavedSearchListingHolder> {
    private Context mContext;
    private ArrayList<ListingFull> mArrListing;

    public SavedSearchListingAdapter(Context mContext, ArrayList<ListingFull> mArrListing) {
        this.mContext = mContext;
        this.mArrListing = mArrListing;
    }

    @Override
    public SavedSearchListingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.saved_search_listing_item, null, false);
        return new SavedSearchListingHolder(v);
    }

    @Override
    public void onBindViewHolder(SavedSearchListingHolder holder, final int position) {
        if (mArrListing.get(position).getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(mArrListing.get(position).getListingImages().getImage()).asBitmap().fitCenter().placeholder(R.drawable.no_image_b).dontAnimate().into(holder.mIvListing);
        }
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventListingDetail(mArrListing.get(position).getId()));
            }
        });
        holder.mAddress1.setText(mArrListing.get(position).getAddress().getAddress1());
        holder.mAddress2.setText(mArrListing.get(position).getAddress().getAddress2());
    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class SavedSearchListingHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.ivListing)
        RoundedImageView mIvListing;
        @Bind(R.id.tvAddress1)
        TextView mAddress1;
        @Bind(R.id.tvAddress2)
        TextView mAddress2;

        public SavedSearchListingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
