package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IPickSchedule;
import com.homecaravan.android.consumer.model.listitem.ListingFullItem;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleTabAdapter extends RecyclerView.Adapter<ScheduleTabAdapter.ScheduleTab> {

    private Context mContext;
    private ArrayList<ListingFullItem> mListing;
    private IPickSchedule mListener;

    public ScheduleTabAdapter(Context context, ArrayList<ListingFullItem> mListing, IPickSchedule mListener) {
        this.mContext = context;
        this.mListing = mListing;
        this.mListener = mListener;
    }

    @Override
    public ScheduleTab onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_tab_item, null, false);
        return new ScheduleTab(view);
    }

    @Override
    public void onBindViewHolder(final ScheduleTab holder, final int position) {
        final ListingFull listing = mListing.get(position).getListing();
        if(listing.getListingImages()!=null) {
            Glide.with(mContext.getApplicationContext()).load(listing.getListingImages().getImage()).asBitmap().fitCenter()
                    .placeholder(R.drawable.no_image_b).dontAnimate().into(holder.mIvListing);
        }holder.mPrice.setText(Utils.getPrice(listing.getPrice().getValue()));
        holder.mAddress1.setText(listing.getAddress().getAddress1());
        holder.mAddress2.setText(listing.getAddress().getAddress2());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (mListing.get(position).isInQueue()) {
            holder.mAddQueue.setVisibility(View.GONE);
        } else {
            holder.mAddQueue.setVisibility(View.VISIBLE);
        }
        holder.mAddQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtils.scaleView(holder.mAddQueue, 1f, 0f, 1f, 0f);
                mListing.get(position).setInQueue(true);
                mListener.pickSchedule(mListing.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListing.size();
    }

    public class ScheduleTab extends RecyclerView.ViewHolder {

        @Bind(R.id.ivListing)
        ImageView mIvListing;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayout;
        @Bind(R.id.ivAddQueue)
        ImageView mAddQueue;
        @Bind(R.id.tvPrice)
        TextView mPrice;
        @Bind(R.id.tvAddress1)
        TextView mAddress1;
        @Bind(R.id.tvAddress2)
        TextView mAddress2;

        public ScheduleTab(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
