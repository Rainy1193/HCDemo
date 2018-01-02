package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IPickSchedule;
import com.homecaravan.android.consumer.model.EventListingDetail;
import com.homecaravan.android.consumer.model.listitem.ListingFullItem;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleListingItem extends AbstractItem<ScheduleListingItem, ScheduleListingItem.ScheduleListingItemHolder> {

    private Context mContext;
    private ListingFullItem mListing;
    private IPickSchedule mListener;

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setListing(ListingFullItem mListing) {
        this.mListing = mListing;
    }

    public void setListener(IPickSchedule mListener) {
        this.mListener = mListener;
    }

    @Override
    public void bindView(final ScheduleListingItemHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        final ListingFull listing = mListing.getListing();
        if (listing.getListingImages() != null) {
            Utils.getImageLoader().loadImage((listing.getListingImages().getImage()), holder.mIvListing, null,
                    Utils.getPlaceHolderListing(mContext), true);
        }
        holder.mPrice.setText(Utils.getPrice(listing.getPrice().getValue()));
        holder.mAddress1.setText(listing.getAddress().getAddress1());
        holder.mAddress2.setText(listing.getAddress().getCity());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventListingDetail(listing.getId()));
            }
        });
        if (mListing.isInQueue()) {
            holder.mAddSchedule.setVisibility(View.INVISIBLE);
        } else {
            holder.mAddSchedule.setVisibility(View.VISIBLE);
        }
        holder.mAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtils.scaleView(holder.mAddSchedule, 1f, 0f, 1f, 0f);
                mListing.setInQueue(true);
                mListener.pickSchedule(mListing);
            }
        });
        holder.mIvBookSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.bookSingle(mListing.getListing().getId());
            }
        });
    }

    @Override
    public ScheduleListingItemHolder getViewHolder(View v) {
        return new ScheduleListingItemHolder(v);
    }

    @Override
    public int getType() {
        return R.id.schedule_listing_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.schedule_tab_item;
    }

    public static class ScheduleListingItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivListing)
        ImageView mIvListing;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayout;
        @Bind(R.id.ivAddQueue)
        ImageView mAddSchedule;
        @Bind(R.id.ivBookSingle)
        ImageView mIvBookSingle;
        @Bind(R.id.tvPrice)
        TextView mPrice;
        @Bind(R.id.tvAddress1)
        TextView mAddress1;
        @Bind(R.id.tvAddress2)
        TextView mAddress2;

        public ScheduleListingItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
