package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.Extension;
import com.homecaravan.android.consumer.widget.ItemTouchHelperExtension;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DiscoverListListingAdapter extends RecyclerView.Adapter<DiscoverListListingAdapter.ListingHolder> {

    private Context mContext;
    private ArrayList<ConsumerListingFullStatus> mArrListing;
    private ItemTouchHelperExtension mTouchHelper;

    public DiscoverListListingAdapter(Context mContext, ArrayList<ConsumerListingFullStatus> mArrListing, ItemTouchHelperExtension mTouchHelper) {
        this.mContext = mContext;
        this.mArrListing = mArrListing;
        this.mTouchHelper = mTouchHelper;
    }

    @Override
    public ListingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.discover_list_listing_item, null, false);
        return new DiscoverListListingAdapter.ItemSwipeWithActionWidthNoSpringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListingHolder holder, int position) {
        final ConsumerListingFullStatus listing = mArrListing.get(position);

        Glide.with(mContext.getApplicationContext())
                .load(listing.getListing().getThumb())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.no_image_b)
                .dontAnimate()
                .into(holder.mIvListing);
        holder.mAddress1.setText(listing.getListing().getAdd1());
        holder.mAddress2.setText(listing.getListing().getCity());
        holder.mPrice.setText(Utils.getPrice(listing.getListing().getPrice()));
        holder.mBath.setText(listing.getListing().getBathRooms());
        holder.mBed.setText(listing.getListing().getBedRooms());
        holder.mLiving.setText(listing.getListing().getLivingSquareFeet());
        holder.mTvBuilt.setText(listing.getListing().getYearBuilt());
        holder.mTvSquare.setText(listing.getListing().getLotSize());
        holder.mTvPool.setText(listing.getListing().getFollowupType());
        if (listing.isFavorite() || listing.isSuperVote() || listing.isShare() || listing.isSchedule()) {
            holder.mLayoutBook.setVisibility(View.GONE);
            holder.mTvQueue.setVisibility(View.VISIBLE);
            holder.mIvQueue.setVisibility(View.VISIBLE);
            holder.mTvFavorite.setVisibility(View.VISIBLE);
            holder.mIvFavorite.setVisibility(View.VISIBLE);
            holder.mTvStatus.setVisibility(View.VISIBLE);
            holder.mIvStatus.setVisibility(View.VISIBLE);
            holder.mTvQueue.setText("Book");
            holder.mTvFavorite.setText("Favorite");
            holder.mIvFavorite.setImageResource(R.drawable.ic_favorite_discover);
            holder.mIvQueue.setImageResource(R.drawable.ic_add_to_queue);
            if (listing.isSuperVote()) {
                holder.mIvStatus.setImageResource(R.drawable.ic_super_vote_color);
                holder.mTvStatus.setText("Super vote");
            } else {
                holder.mIvStatus.setImageResource(R.drawable.ic_like_color);
                holder.mTvStatus.setText("I like it");
            }
            if (listing.isInQueue()) {
                holder.mIvQueue.setImageResource(R.drawable.ic_added_discover);
                holder.mTvQueue.setTextColor(ContextCompat.getColor(mContext, R.color.colorMenuConsumer));

            } else {
                holder.mIvQueue.setImageResource(R.drawable.ic_add_to_queue);
                holder.mTvQueue.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            }

            if (listing.isFavorite1()) {
                holder.mIvFavorite.setImageResource(R.drawable.ic_favorited_consumer);
                holder.mTvFavorite.setTextColor(ContextCompat.getColor(mContext, R.color.colorRedE1));
            } else {
                holder.mIvFavorite.setImageResource(R.drawable.ic_favorite_discover);
                holder.mTvFavorite.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            }
            holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_like));
        } else if (listing.isPrice() || listing.isLocation() || listing.isCondition() || listing.isSize()) {
            holder.mLayoutBook.setVisibility(View.GONE);
            holder.mTvQueue.setVisibility(View.INVISIBLE);
            holder.mIvQueue.setVisibility(View.INVISIBLE);
            holder.mTvFavorite.setVisibility(View.INVISIBLE);
            holder.mIvFavorite.setVisibility(View.INVISIBLE);
            holder.mTvStatus.setVisibility(View.VISIBLE);
            holder.mIvStatus.setVisibility(View.VISIBLE);
            holder.mTvStatus.setText("Don\'t like it");
            holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_vote_down));
            holder.mIvStatus.setImageResource(R.drawable.ic_dislike_color);
        } else {
            holder.mLayoutBook.setVisibility(View.VISIBLE);
            holder.mTvQueue.setVisibility(View.INVISIBLE);
            holder.mIvQueue.setVisibility(View.INVISIBLE);
            holder.mTvFavorite.setVisibility(View.INVISIBLE);
            holder.mIvFavorite.setVisibility(View.INVISIBLE);
            holder.mTvStatus.setVisibility(View.INVISIBLE);
            holder.mIvStatus.setVisibility(View.INVISIBLE);
        }

        holder.mLayoutBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.mLayoutFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isFavorite1()) {
                    listing.setFavorite1(false);
                    holder.mIvFavorite.setImageResource(R.drawable.ic_favorite_discover);
                    holder.mTvFavorite.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
                } else {
                    listing.setFavorite1(true);
                    holder.mIvFavorite.setImageResource(R.drawable.ic_favorited_consumer);
                    holder.mTvFavorite.setTextColor(ContextCompat.getColor(mContext, R.color.colorRedE1));
                }
            }
        });
        holder.mLayoutStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.mLayoutQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isInQueue()) {
                    listing.setInQueue(false);
                    holder.mIvQueue.setImageResource(R.drawable.ic_add_to_queue);
                    holder.mTvQueue.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
                } else {
                    listing.setInQueue(true);
                    holder.mIvQueue.setImageResource(R.drawable.ic_added_discover);
                    holder.mTvQueue.setTextColor(ContextCompat.getColor(mContext, R.color.colorMenuConsumer));
                }
            }
        });
        holder.mLayoutBackDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.mLayoutBackLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.mLayoutFavoriteLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isFavorite()) {
                    listing.setFavorite(false);
                    listing.setFavorite1(false);
                    changeColorLayout(holder.mLayoutFavoriteLike, holder.mTvFavoriteLike, holder.mIvFavoriteLike, false, true);
                } else {
                    listing.setFavorite(true);
                    listing.setFavorite1(true);
                    changeColorLayout(holder.mLayoutFavoriteLike, holder.mTvFavoriteLike, holder.mIvFavoriteLike, true, true);
                }
                changeLayout(true, holder, listing);
            }
        });
        holder.mLayoutSuperVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isSuperVote()) {
                    listing.setSuperVote(false);
                    changeColorLayout(holder.mLayoutSuperVote, holder.mTvSuperVote, holder.mIvSuperVote, false, true);
                } else {
                    listing.setSuperVote(true);
                    changeColorLayout(holder.mLayoutSuperVote, holder.mTvSuperVote, holder.mIvSuperVote, true, true);
                }
                changeLayout(true, holder, listing);
            }
        });
        holder.mLayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listing.isShare()) {
                    listing.setShare(false);
                    changeColorLayout(holder.mLayoutShare, holder.mTvShare, holder.mIvShare, false, true);
                } else {
                    listing.setShare(true);
                    changeColorLayout(holder.mLayoutShare, holder.mTvShare, holder.mIvShare, true, true);
                }
                changeLayout(true, holder, listing);
            }
        });
        holder.mLayoutSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isSchedule()) {
                    listing.setSchedule(false);
                    changeColorLayout(holder.mLayoutSchedule, holder.mTvSchedule, holder.mIvSchedule, false, true);
                } else {
                    listing.setSchedule(true);
                    changeColorLayout(holder.mLayoutSchedule, holder.mTvSchedule, holder.mIvSchedule, true, true);
                }
                changeLayout(true, holder, listing);
            }
        });
        holder.mLayoutPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isPrice()) {
                    listing.setPrice(false);
                    changeColorLayout(holder.mLayoutPrice, holder.mTvDislikePrice, holder.mIvDislikePrice, false, false);
                } else {
                    listing.setPrice(true);
                    changeColorLayout(holder.mLayoutPrice, holder.mTvDislikePrice, holder.mIvDislikePrice, true, false);
                }
                changeLayout(false, holder, listing);
            }
        });
        holder.mLayoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isLocation()) {
                    listing.setLocation(false);
                    changeColorLayout(holder.mLayoutLocation, holder.mTvLocation, holder.mIvLocation, false, false);
                } else {
                    listing.setLocation(true);
                    changeColorLayout(holder.mLayoutLocation, holder.mTvLocation, holder.mIvLocation, true, false);
                }
                changeLayout(false, holder, listing);
            }
        });
        holder.mLayoutCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isCondition()) {
                    listing.setCondition(false);
                    changeColorLayout(holder.mLayoutCondition, holder.mTvCondition, holder.mIvCondition, false, false);
                } else {
                    listing.setCondition(true);
                    changeColorLayout(holder.mLayoutCondition, holder.mTvCondition, holder.mIvCondition, true, false);
                }
                changeLayout(false, holder, listing);
            }
        });
        holder.mLayoutSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listing.isSize()) {
                    listing.setSize(false);
                    changeColorLayout(holder.mLayoutSize, holder.mTvSize, holder.mIvSize, false, false);
                } else {
                    listing.setSize(true);
                    changeColorLayout(holder.mLayoutSize, holder.mTvSize, holder.mIvSize, true, false);
                }
                changeLayout(false, holder, listing);
            }
        });

    }

    public void changeLayout(boolean b, DiscoverListListingAdapter.ListingHolder holder, ConsumerListingFullStatus listing) {
        if(mTouchHelper!=null) {
            mTouchHelper.closeOpenedPreItem();
        }
        if (b) {
            int fColor;
            int tColor;
            int fBgColor;
            int tBgColor;
            fColor = R.color.color_dislike;
            fBgColor = R.color.color_dislike_bg;

            tColor = R.color.colorTextMain;
            tBgColor = R.color.colorWhite;
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, holder.mLayoutPrice);
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, holder.mLayoutLocation);
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, holder.mLayoutSize);
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, holder.mLayoutCondition);
            AnimUtils.changeTextColor(mContext, fColor, tColor, holder.mTvDislikePrice);
            AnimUtils.changeTextColor(mContext, fColor, tColor, holder.mTvLocation);
            AnimUtils.changeTextColor(mContext, fColor, tColor, holder.mTvSize);
            AnimUtils.changeTextColor(mContext, fColor, tColor, holder.mTvCondition);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, holder.mIvDislikePrice);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, holder.mIvLocation);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, holder.mIvSize);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, holder.mIvCondition);
            listing.setPrice(false);
            listing.setLocation(false);
            listing.setCondition(false);
            listing.setSize(false);

        } else {
            int fColor;
            int tColor;
            int fBgColor;
            int tBgColor;
            fColor = R.color.color_like;
            fBgColor = R.color.color_like_bg;

            tColor = R.color.colorTextMain;
            tBgColor = R.color.colorWhite;
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, holder.mLayoutFavoriteLike);
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, holder.mLayoutSuperVote);
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, holder.mLayoutShare);
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, holder.mLayoutSchedule);
            AnimUtils.changeTextColor(mContext, fColor, tColor, holder.mTvFavoriteLike);
            AnimUtils.changeTextColor(mContext, fColor, tColor, holder.mTvSuperVote);
            AnimUtils.changeTextColor(mContext, fColor, tColor, holder.mTvShare);
            AnimUtils.changeTextColor(mContext, fColor, tColor, holder.mTvSchedule);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, holder.mIvFavoriteLike);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, holder.mIvSuperVote);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, holder.mIvShare);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, holder.mIvSchedule);
            listing.setInQueue(false);
            listing.setSuperVote(false);
            listing.setSchedule(false);
            listing.setShare(false);
            listing.setFavorite1(false);
            listing.setFavorite(false);
        }

        if (listing.isFavorite() || listing.isSuperVote() || listing.isShare() || listing.isSchedule()) {
            holder.mLayoutBook.setVisibility(View.GONE);
            holder.mTvQueue.setVisibility(View.VISIBLE);
            holder.mIvQueue.setVisibility(View.VISIBLE);
            holder.mTvFavorite.setVisibility(View.VISIBLE);
            holder.mIvFavorite.setVisibility(View.VISIBLE);
            holder.mTvStatus.setVisibility(View.VISIBLE);
            holder.mIvStatus.setVisibility(View.VISIBLE);
            holder.mTvQueue.setText("Book");
            holder.mTvFavorite.setText("Favorite");
            holder.mIvFavorite.setImageResource(R.drawable.ic_favorite_discover);
            holder.mIvQueue.setImageResource(R.drawable.ic_add_to_queue);
            if (listing.isSuperVote()) {
                holder.mIvStatus.setImageResource(R.drawable.ic_super_vote_color);
                holder.mTvStatus.setText("Super vote");
            } else {
                holder.mIvStatus.setImageResource(R.drawable.ic_like_color);
                holder.mTvStatus.setText("I like it");
            }
            if (listing.isInQueue()) {
                holder.mIvQueue.setImageResource(R.drawable.ic_added_discover);
                holder.mTvQueue.setTextColor(ContextCompat.getColor(mContext, R.color.colorMenuConsumer));

            } else {
                holder.mIvQueue.setImageResource(R.drawable.ic_add_to_queue);
                holder.mTvQueue.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            }
            if (listing.isFavorite1()) {
                holder.mIvFavorite.setImageResource(R.drawable.ic_favorited_consumer);
                holder.mTvFavorite.setTextColor(ContextCompat.getColor(mContext, R.color.colorRedE1));
            } else {
                holder.mIvFavorite.setImageResource(R.drawable.ic_favorite_discover);
                holder.mTvFavorite.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            }
            holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_like));
        } else if (listing.isPrice() || listing.isLocation() || listing.isCondition() || listing.isSize()) {
            holder.mLayoutBook.setVisibility(View.GONE);
            holder.mTvQueue.setVisibility(View.INVISIBLE);
            holder.mIvQueue.setVisibility(View.INVISIBLE);
            holder.mTvFavorite.setVisibility(View.INVISIBLE);
            holder.mIvFavorite.setVisibility(View.INVISIBLE);
            holder.mTvStatus.setVisibility(View.VISIBLE);
            holder.mIvStatus.setVisibility(View.VISIBLE);
            holder.mTvStatus.setText("Don\'t like it");
            holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_vote_down));
            holder.mIvStatus.setImageResource(R.drawable.ic_dislike_color);
        } else {
            holder.mLayoutBook.setVisibility(View.VISIBLE);
            holder.mTvQueue.setVisibility(View.INVISIBLE);
            holder.mIvQueue.setVisibility(View.INVISIBLE);
            holder.mTvFavorite.setVisibility(View.INVISIBLE);
            holder.mIvFavorite.setVisibility(View.INVISIBLE);
            holder.mTvStatus.setVisibility(View.INVISIBLE);
            holder.mIvStatus.setVisibility(View.INVISIBLE);
        }
    }

    public void changeColorLayout(View layout, TextView textView, ImageView imageView, boolean b, boolean b1) {
        int fColor;
        int tColor;
        int fBgColor;
        int tBgColor;
        if (b1) {
            fColor = R.color.color_like;
            fBgColor = R.color.color_like_bg;
        } else {
            fColor = R.color.color_dislike;
            fBgColor = R.color.color_dislike_bg;
        }
        tColor = R.color.colorTextMain;
        tBgColor = R.color.colorWhite;
        if (b) {
            AnimUtils.changeBackgroundColor(mContext, tBgColor, fBgColor, layout);
            AnimUtils.changeTextColor(mContext, tColor, fColor, textView);
            AnimUtils.changeColorFilter(mContext, tColor, fColor, imageView);
        } else {
            AnimUtils.changeBackgroundColor(mContext, fBgColor, tBgColor, layout);
            AnimUtils.changeTextColor(mContext, fColor, tColor, textView);
            AnimUtils.changeColorFilter(mContext, fColor, tColor, imageView);
        }

    }

    @Override
    public int getItemCount() {
        return mArrListing.size();
    }

    public class ListingHolder extends RecyclerView.ViewHolder {

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

        @Bind(R.id.ivFavorite)
        ImageView mIvFavorite;
        @Bind(R.id.tvFavorite)
        TextView mTvFavorite;
        @Bind(R.id.layoutFavorite)
        LinearLayout mLayoutFavorite;
        @Bind(R.id.ivQueue)
        ImageView mIvQueue;
        @Bind(R.id.tvQueue)
        TextView mTvQueue;
        @Bind(R.id.layoutQueue)
        LinearLayout mLayoutQueue;
        @Bind(R.id.layoutStatus)
        LinearLayout mLayoutStatus;
        @Bind(R.id.layoutItem)
        FrameLayout mLayoutItem;
        @Bind(R.id.ivStatus)
        ImageView mIvStatus;
        @Bind(R.id.tvStatus)
        TextView mTvStatus;
        @Bind(R.id.mActionContainer)
        public RelativeLayout mActionContainer;
        @Bind(R.id.layoutContent)
        public LinearLayout mViewContent;
        @Bind(R.id.layoutLike)
        public LinearLayout mLayoutLike;
        @Bind(R.id.layoutDislike)
        public LinearLayout mLayoutDislike;

        @Bind(R.id.layoutBackLike)
        RelativeLayout mLayoutBackLike;
        @Bind(R.id.layoutBackDislike)
        RelativeLayout mLayoutBackDislike;

        @Bind(R.id.layoutFavoriteLike)
        RelativeLayout mLayoutFavoriteLike;
        @Bind(R.id.ivFavoriteLike)
        ImageView mIvFavoriteLike;
        @Bind(R.id.tvFavoriteLike)
        TextView mTvFavoriteLike;

        @Bind(R.id.layoutSuperVote)
        RelativeLayout mLayoutSuperVote;
        @Bind(R.id.ivSuperVote)
        ImageView mIvSuperVote;
        @Bind(R.id.tvSuperVote)
        TextView mTvSuperVote;

        @Bind(R.id.layoutShare)
        RelativeLayout mLayoutShare;
        @Bind(R.id.ivShare)
        ImageView mIvShare;
        @Bind(R.id.tvShare)
        TextView mTvShare;

        @Bind(R.id.layoutSchedule)
        RelativeLayout mLayoutSchedule;
        @Bind(R.id.ivSchedule)
        ImageView mIvSchedule;
        @Bind(R.id.tvSchedule)
        TextView mTvSchedule;

        @Bind(R.id.layoutPrice)
        RelativeLayout mLayoutPrice;
        @Bind(R.id.ivDislikePrice)
        ImageView mIvDislikePrice;
        @Bind(R.id.tvDislikePrice)
        TextView mTvDislikePrice;


        @Bind(R.id.layoutCondition)
        RelativeLayout mLayoutCondition;
        @Bind(R.id.ivCondition)
        ImageView mIvCondition;
        @Bind(R.id.tvCondition)
        TextView mTvCondition;

        @Bind(R.id.layoutLocation)
        RelativeLayout mLayoutLocation;
        @Bind(R.id.ivLocation)
        ImageView mIvLocation;
        @Bind(R.id.tvLocation)
        TextView mTvLocation;

        @Bind(R.id.layoutSize)
        RelativeLayout mLayoutSize;
        @Bind(R.id.ivSize)
        ImageView mIvSize;
        @Bind(R.id.tvSize)
        TextView mTvSize;

        @Bind(R.id.tvBuilt)
        TextView mTvBuilt;

        @Bind(R.id.tvSquare)
        TextView mTvSquare;

        @Bind(R.id.tvPool)
        TextView mTvPool;

        @Bind(R.id.layoutShow)
        RelativeLayout mLayoutShow;

        @Bind(R.id.layoutBook)
        LinearLayout mLayoutBook;


        public ListingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * The type Item swipe with action width view holder.
     */
    class ItemSwipeWithActionWidthViewHolder extends ListingHolder implements Extension {

        /**
         * Instantiates a new Item swipe with action width view holder.
         *
         * @param itemView the item view
         */
        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }

    /**
     * The type Item swipe with action width no spring view holder.
     */
    public class ItemSwipeWithActionWidthNoSpringViewHolder extends ItemSwipeWithActionWidthViewHolder implements Extension {

        /**
         * Instantiates a new Item swipe with action width no spring view holder.
         *
         * @param itemView the item view
         */
        public ItemSwipeWithActionWidthNoSpringViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }
}
