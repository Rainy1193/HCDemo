package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;
import com.homecaravan.android.consumer.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DiscoverMapAdapter extends RecyclerView.Adapter<DiscoverMapAdapter.DiscoverMap> {
    private ArrayList<ConsumerListingFullStatus> mArrListing;
    private Context mContext;
    private int mHeight;
    private int mWidth;

    public DiscoverMapAdapter(ArrayList<ConsumerListingFullStatus> mArrListing, Context mContext, int mHeight, int mWidth) {
        this.mArrListing = mArrListing;
        this.mContext = mContext;
        this.mHeight = mHeight;
        this.mWidth = mWidth;
    }

    @Override
    public DiscoverMap onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listing_map_item, null, false);
        return new DiscoverMap(view);
    }

    @Override
    public void onBindViewHolder(final DiscoverMap holder, int position) {
        final ConsumerListingFullStatus listing = mArrListing.get(position);
        Glide.with(mContext.getApplicationContext()).load(listing.getListing().getThumb()).asBitmap().fitCenter().placeholder(R.drawable.no_image_b).dontAnimate().into(holder.mListingImage);
        //holder.mLayoutMain.setLayoutParams(getLayoutParams());
        if (listing.isSelected()) {
            holder.mView.setVisibility(View.GONE);
        } else {
            holder.mView.setVisibility(View.VISIBLE);
        }
        holder.mAdd1.setText(listing.getListing().getAdd1());
        holder.mAdd2.setText(listing.getListing().getCity());
        holder.mPrice.setText(Utils.getPrice(listing.getListing().getPrice()));
        holder.mBath.setText(listing.getListing().getBathRooms());
        holder.mBed.setText(listing.getListing().getBedRooms());
        holder.mLiving.setText(listing.getListing().getLivingSquareFeet());
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
                if (listing.isFavorite()) {
                    listing.setFavorite(false);
                    holder.mFavorite.setImageResource(R.drawable.ic_listing_map_favorite);

                } else {
                    listing.setFavorite(true);
                    holder.mFavorite.setImageResource(R.drawable.ic_favorited_consumer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class DiscoverMap extends RecyclerView.ViewHolder {

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

        public DiscoverMap(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public LinearLayout.LayoutParams getLayoutParams() {
        return new LinearLayout.LayoutParams(mWidth / 2, mHeight / 4);
    }

}
