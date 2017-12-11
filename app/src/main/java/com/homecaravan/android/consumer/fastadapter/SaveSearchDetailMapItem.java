package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.CaravanQueue;
import com.homecaravan.android.consumer.model.EventQueue;
import com.homecaravan.android.consumer.model.listitem.ListingFullItem;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SaveSearchDetailMapItem extends AbstractItem<SaveSearchDetailMapItem, SaveSearchDetailMapItem.SaveSearchMapItemHolder> {
    private ListingFullItem mListing;
    private Context mContext;

    public void setListing(ListingFullItem mListing) {
        this.mListing = mListing;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void bindView(final SaveSearchMapItemHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        if (mListing.getListing().getListingImages() != null) {
            Glide.with(mContext.getApplicationContext()).load(mListing.getListing().getListingImages().getImage()).asBitmap().fitCenter().placeholder(R.drawable.no_image_b).dontAnimate().into(holder.mListingImage);
        }//holder.mLayoutMain.setLayoutParams(getLayoutParams());
        if (mListing.isSelected()) {
            holder.mView.setVisibility(View.GONE);
        } else {
            holder.mView.setVisibility(View.VISIBLE);
        }
        if (mListing.getListing().getAddress().getAddress2() == null) {
            holder.mAdd2.setVisibility(View.GONE);
        } else {
            if (mListing.getListing().getAddress().getAddress2().length() != 0) {
                holder.mAdd2.setVisibility(View.VISIBLE);
            } else {
                holder.mAdd2.setVisibility(View.GONE);
            }
        }
        holder.mAdd1.setText(mListing.getListing().getAddress().getAddress1());
        holder.mAdd2.setText(mListing.getListing().getAddress().getAddress2());
        holder.mPrice.setText(mListing.getListing().getPrice().getText());
        holder.mBath.setText(mListing.getListing().getBathrooms());
        holder.mBed.setText(mListing.getListing().getBedrooms());
        holder.mLiving.setText(mListing.getListing().getLivingSquare().getText());
        holder.mLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.mLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isFavorite()) {
                    mListing.setFavorite(false);
                    holder.mFavorite.setImageResource(R.drawable.ic_listing_map_favorite);

                } else {
                    mListing.setFavorite(true);
                    holder.mFavorite.setImageResource(R.drawable.ic_favorited_consumer);
                }
            }
        });

        holder.mIvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListing.setInQueue(true);
                if (mListing.isInQueue()) {
                    EventBus.getDefault().post(new EventQueue(false, mListing.getListing().getId(), null, mListing.getListing(), null));
                    mListing.setInQueue(false);
                    holder.mIvBook.setVisibility(View.VISIBLE);
                } else {
                    EventBus.getDefault().post(new EventQueue(true, mListing.getListing().getId(), null, mListing.getListing(), null));
                    mListing.setInQueue(true);
                    holder.mIvBook.setVisibility(View.GONE);
                    CaravanQueue.getInstance().setIdRemove(mListing.getListing().getId());
                }
            }
        });

        if (mListing.isInQueue()) {
            holder.mIvBook.setVisibility(View.GONE);
        } else {
            holder.mIvBook.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public SaveSearchMapItemHolder getViewHolder(View v) {
        return new SaveSearchMapItemHolder(v);
    }

    @Override
    public int getType() {
        return R.id.search_map_mini_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.listing_map_item;
    }


    public static class SaveSearchMapItemHolder extends RecyclerView.ViewHolder {

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
        @Bind(R.id.ivBook)
        ImageView mIvBook;

        public SaveSearchMapItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
