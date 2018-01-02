package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.EventFavorite;
import com.homecaravan.android.consumer.model.EventListingDetail;
import com.homecaravan.android.consumer.model.EventQueue;
import com.homecaravan.android.consumer.model.responseapi.Listing;
import com.homecaravan.android.consumer.utils.Utils;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteItem extends AbstractItem<FavoriteItem, FavoriteItem.FavoriteHolder> {
    private Context mContext;
    private Listing mListing;
    private boolean mIsFavorite;
    private boolean mIsQueue;
    private int mPosition;
    private FavoriteListener mListener;

    public void setListener(FavoriteListener mListener) {
        this.mListener = mListener;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setListing(Listing mListing) {
        this.mListing = mListing;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean mIsFavorite) {
        this.mIsFavorite = mIsFavorite;
    }

    public boolean isQueue() {
        return mIsQueue;
    }

    public void setQueue(boolean mIsQueue) {
        this.mIsQueue = mIsQueue;
    }

    @Override
    public FavoriteHolder getViewHolder(View view) {
        return new FavoriteHolder(view);
    }

    @Override
    public void bindView(final FavoriteHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        if (mListing.getListingImages().getImage() != null) {
            Glide.with(mContext.getApplicationContext())
                    .load(mListing.getListingImages().getImage())
                    .asBitmap()
                    .fitCenter()
                    .placeholder(R.drawable.ic_placeholder_listing_consumer)
                    .into(holder.mIvListing);
        }
        holder.mIvListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.openListingDetail(mListing.getId());

            }
        });
        holder.mIvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventFavorite(mListing.getId(), false));
                mListener.removeFavorite(mPosition);
            }
        });
        holder.mIvQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventQueue(true, mListing.getId(), mListing, null, null));
                holder.mIvQueue.setVisibility(View.INVISIBLE);
            }
        });
        if (mIsQueue) {
            holder.mIvQueue.setVisibility(View.INVISIBLE);
        } else {
            holder.mIvQueue.setVisibility(View.VISIBLE);
        }
        holder.mAddress1.setText(mListing.getAddress().getAddress1());
        holder.mAddress2.setText(mListing.getAddress().getAddress2());
        holder.mPrice.setText(Utils.getPrice(mListing.getPrice().getValue()));
        holder.mBath.setText(mListing.getBathrooms());
        holder.mBed.setText(mListing.getBedrooms());
        holder.mLiving.setText(mListing.getLivingSquare().getValue() + " ft");
    }

    @Override
    public int getType() {
        return R.id.favorite_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.favorite_item;
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layout)
        RelativeLayout mLayout;
        @Bind(R.id.ivListing)
        ImageView mIvListing;
        @Bind(R.id.tvAddress1)
        TextView mAddress1;
        @Bind(R.id.tvAddress2)
        TextView mAddress2;
        @Bind(R.id.tvPrice)
        TextView mPrice;

        @Bind(R.id.tvBed)
        TextView mBed;
        @Bind(R.id.tvBath)
        TextView mBath;
        @Bind(R.id.tvLiving)
        TextView mLiving;
        @Bind(R.id.ivAddQueue)
        ImageView mIvQueue;
        @Bind(R.id.ivStatus)
        ImageView mIvStatus;

        public FavoriteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface FavoriteListener {
        void openListingDetail(String id);

        void removeFavorite(int position);
    }
}
