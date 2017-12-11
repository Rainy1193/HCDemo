package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IVoteListing;
import com.homecaravan.android.consumer.model.EventListingDetail;
import com.homecaravan.android.consumer.model.listitem.ListingItem;
import com.homecaravan.android.consumer.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchMapItem extends AbstractItem<SearchMapItem, SearchMapItem.SearchMapMiniItemHolder> {

    private ListingItem mListing;
    private Context mContext;
    private IVoteListing mListener;
    private int mPosition;

    public void setListener(IVoteListing mListener) {
        this.mListener = mListener;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public int getPosition() {
        return mPosition;
    }


    public void setListing(ListingItem mListing) {
        this.mListing = mListing;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public ListingItem getListing() {
        return mListing;
    }

    @Override
    public void bindView(final SearchMapMiniItemHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        if (mListing.getListing().getThumbnail() != null) {
            Glide.with(mContext.getApplicationContext()).load(mListing.getListing().getThumbnail()).asBitmap().fitCenter()
                    .placeholder(R.drawable.ic_placeholder_listing_consumer).into(holder.mListingImage);
        }

        if (mListing.isSelected()) {
            holder.mView.setVisibility(View.GONE);
        } else {
            holder.mView.setVisibility(View.VISIBLE);
        }
        if (mListing.getListing().getAddress2() == null) {
            holder.mAdd2.setVisibility(View.GONE);
        } else {
            if (mListing.getListing().getAddress2().length() != 0) {
                holder.mAdd2.setVisibility(View.VISIBLE);
            } else {
                holder.mAdd2.setVisibility(View.GONE);
            }
        }
        holder.mAdd1.setText(mListing.getListing().getAddress1());
        holder.mAdd2.setText(mListing.getListing().getAddress2());
        holder.mPrice.setText(Utils.getPrice(mListing.getListing().getPrice()));
        holder.mBath.setText(mListing.getListing().getBaths());
        holder.mBed.setText(mListing.getListing().getBeds());
        holder.mLiving.setText(mListing.getListing().getLivingSquare() + " sf");
        holder.mLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventListingDetail(mListing.getListing().getId()));
            }
        });
        holder.mLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventListingDetail(mListing.getListing().getId()));
            }
        });


        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isFavorite()) {
                    holder.mFavorite.setImageResource(R.drawable.ic_favorite_consumer_1);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "map", "favoriteMap");
                } else {
                    holder.mFavorite.setImageResource(R.drawable.ic_favorited_consumer);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "map", "favoriteMap");
                }
            }
        });

        holder.mIvAddQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isQueue()) {
                    holder.mIvAddQueue.setVisibility(View.VISIBLE);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "map", "scheduleMap");
                } else {
                    holder.mIvAddQueue.setVisibility(View.GONE);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "map", "scheduleMap");
                }
            }
        });

        if (mListing.isQueue()) {
            holder.mIvAddQueue.setVisibility(View.GONE);
        } else {
            holder.mIvAddQueue.setVisibility(View.VISIBLE);
        }
        if (mListing.isFavorite()) {
            holder.mFavorite.setImageResource(R.drawable.ic_favorited_consumer);
        } else {
            holder.mFavorite.setImageResource(R.drawable.ic_favorite_consumer_1);
        }
    }

    @Override
    public int getType() {
        return R.id.search_map_mini_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.listing_map_item;
    }

    @Override
    public SearchMapMiniItemHolder getViewHolder(View v) {
        return new SearchMapMiniItemHolder(v);
    }


    public static class SearchMapMiniItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutDiscoverMap)
        RelativeLayout mLayoutMain;
        @Bind(R.id.layoutContent)
        RelativeLayout mLayoutContent;
        @Bind(R.id.ivListingMapDiscover)
        RoundedImageView mListingImage;
        @Bind(R.id.tvAddress1)
        TextView mAdd1;
        @Bind(R.id.tvAddress2)
        TextView mAdd2;
        @Bind(R.id.ivFavorite)
        ImageView mFavorite;
        @Bind(R.id.ivAddQueue)
        ImageView mIvAddQueue;
        @Bind(R.id.ivBookSingle)
        ImageView mIvBookSingle;
        @Bind(R.id.tvPrice)
        TextView mPrice;
        @Bind(R.id.tvBath)
        TextView mBath;
        @Bind(R.id.tvBed)
        TextView mBed;
        @Bind(R.id.tvLiving)
        TextView mLiving;
        @Bind(R.id.viewBg)
        View mView;


        public SearchMapMiniItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
