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
import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;
import com.homecaravan.android.consumer.listener.IDashboardListener;
import com.homecaravan.android.consumer.model.BaseDataRecyclerView;
import com.homecaravan.android.consumer.model.EventReviewSubmit;
import com.homecaravan.android.consumer.model.HeaderRvData;
import com.homecaravan.android.consumer.model.ViewAllRvData;
import com.homecaravan.android.consumer.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowingHistoryDashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_HEADER = 1;
    private final int VIEW_ITEM = 0;
    private final int VIEW_ALL = 2;
    private Context mContext;
    private ArrayList<BaseDataRecyclerView> mArrListing;
    private IDashboardListener mListener;

    public ShowingHistoryDashboardAdapter(Context mContext, ArrayList<BaseDataRecyclerView> mArrListing, IDashboardListener mListener) {
        this.mContext = mContext;
        this.mArrListing = mArrListing;
        this.mListener = mListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_dashboard_item, parent, false);
            vh = new ListingHolder(v);
        } else if (viewType == VIEW_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.showing_history_header_item, parent, false);
            vh = new HeaderHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false);
            vh = new ViewAllHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.mNumberListing.setText(String.valueOf(mArrListing.size() - 2));
            headerHolder.mLayoutHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.openAllShowing();
                }
            });
        } else if (holder instanceof ViewAllHolder) {
            ViewAllHolder viewAllHolder = (ViewAllHolder) holder;
            viewAllHolder.mLayoutViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.openAllShowing();
                }
            });
            viewAllHolder.mNumViewAll.setText(String.valueOf(mArrListing.size() - 2));
        } else {
            ConsumerListingFullStatus showing = (ConsumerListingFullStatus) mArrListing.get(position);
            ListingHolder listingHolder = (ListingHolder) holder;
            Glide.with(mContext.getApplicationContext()).load(showing.getListing().getThumb())
                    .asBitmap().fitCenter().placeholder(R.drawable.ic_placeholder_listing_consumer).into(listingHolder.mIvListing);
            listingHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //CurrentConsumerListing.getInstance().setListingData(((ConsumerListingFullStatus) mArrListing.get(position)));
                    EventBus.getDefault().post(new EventReviewSubmit(((ConsumerListingFullStatus) mArrListing.get(position)).getListing().getId()));
                }
            });
            listingHolder.mPrice.setText(Utils.getPrice(showing.getListing().getPrice()));
            listingHolder.mAddress1.setText(showing.getListing().getAdd1());
            listingHolder.mAddress2.setText(showing.getListing().getCity());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mArrListing.get(position) instanceof ConsumerListingFullStatus) {
            return VIEW_ITEM;
        } else if (mArrListing.get(position) instanceof HeaderRvData) {
            return VIEW_HEADER;
        } else if (mArrListing.get(position) instanceof ViewAllRvData) {
            return VIEW_ALL;
        } else {
            throw new RuntimeException("ItemViewType unknown");
        }
    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class ListingHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.ivListing)
        RoundedImageView mIvListing;
        @Bind(R.id.tvPrice)
        TextView mPrice;
        @Bind(R.id.tvAddress1)
        TextView mAddress1;
        @Bind(R.id.tvAddress2)
        TextView mAddress2;

        public ListingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvNumberListing)
        TextView mNumberListing;
        @Bind(R.id.layoutHeader)
        RelativeLayout mLayoutHeader;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

