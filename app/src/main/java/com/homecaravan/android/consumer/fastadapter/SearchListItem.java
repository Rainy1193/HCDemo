package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IVoteListing;
import com.homecaravan.android.consumer.model.EventListingDetail;
import com.homecaravan.android.consumer.model.listitem.ListingItem;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.Extension;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchListItem extends AbstractItem<SearchListItem, SearchListItem.SearchListItemHolder> {

    private ListingItem mListing;
    private Context mContext;
    private IVoteListing mListener;
    private int mPosition;

    public ListingItem getListing() {
        return mListing;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public void setListener(IVoteListing mListener) {
        this.mListener = mListener;
    }

    public void setListing(ListingItem mListing) {
        this.mListing = mListing;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void bindView(final SearchListItemHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        if (mListing.getListing().getThumbnail() != null) {
            Glide.with(mContext.getApplicationContext())
                    .load(mListing.getListing().getThumbnail())
                    .asBitmap()
                    .fitCenter()
                    .placeholder(R.drawable.ic_placeholder_listing_consumer)
                    .into(holder.mIvListing);
        }
        holder.mIvListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventListingDetail(mListing.getListing().getId()));
            }
        });
        if (mListing.isFavorite() || mListing.isSuperVote() || mListing.isShare() || mListing.isQueue()) {
            if (mListing.isSuperVote()) {
                holder.mLayoutBgStatus.setVisibility(View.VISIBLE);
                holder.mIvStatus.setImageResource(R.drawable.ic_super_vote_color);
                //holder.mTvStatus.setText("Super vote");
            } else {
                holder.mLayoutBgStatus.setVisibility(View.VISIBLE);
                holder.mIvStatus.setImageResource(R.drawable.ic_like_color);
                //holder.mTvStatus.setText("I like it");

            }
        } else if (mListing.isPrice() || mListing.isLocation() || mListing.isCondition() || mListing.isSize()) {
            holder.mIvStatus.setImageResource(R.drawable.ic_dislike_color);
            holder.mLayoutBgStatus.setVisibility(View.VISIBLE);
            //holder.mTvStatus.setText("Don\'t like it");
        } else {
            holder.mLayoutBgStatus.setVisibility(View.INVISIBLE);
            holder.mIvStatus.setImageDrawable(null);
            //holder.mTvStatus.setText("");
        }

        if (mListing.isQueue()) {
            holder.mLayoutQueue.setVisibility(View.INVISIBLE);
            //holder.mIvQueue.setImageResource(R.drawable.ic_added_discover);
            holder.mLayoutSchedule.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_like_bg));
            holder.mIvSchedule.setColorFilter(ContextCompat.getColor(mContext, R.color.color_like));
            holder.mTvSchedule.setTextColor(ContextCompat.getColor(mContext, R.color.color_like));
        } else {
            holder.mLayoutQueue.setVisibility(View.VISIBLE);
            //holder.mIvQueue.setImageResource(R.drawable.ic_add_to_queue);
            holder.mLayoutSchedule.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            holder.mIvSchedule.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextMain));
            holder.mTvSchedule.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }

        if (mListing.isShare()) {
            holder.mLayoutShare.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_like_bg));
            holder.mTvShare.setTextColor(ContextCompat.getColor(mContext, R.color.color_like));
            holder.mIvShare.setColorFilter(ContextCompat.getColor(mContext, R.color.color_like));
        } else {
            holder.mLayoutShare.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvShare.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            holder.mIvShare.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }

        if (mListing.isSuperVote()) {
            holder.mLayoutSuperVote.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_like_bg));
            holder.mTvSuperVote.setTextColor(ContextCompat.getColor(mContext, R.color.color_like));
            holder.mIvSuperVote.setColorFilter(ContextCompat.getColor(mContext, R.color.color_like));
        } else {
            holder.mLayoutSuperVote.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvSuperVote.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            holder.mIvSuperVote.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }

        if (mListing.isFavorite()) {
            holder.mLayoutBgStatus.setVisibility(View.VISIBLE);
            holder.mIvStatus.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorited_consumer));
            holder.mLayoutFavoriteLike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_like_bg));
            holder.mTvFavoriteLike.setTextColor(ContextCompat.getColor(mContext, R.color.color_like));
            holder.mIvFavoriteLike.setColorFilter(ContextCompat.getColor(mContext, R.color.color_like));
        } else {
            holder.mIvStatus.setImageDrawable(null);
            holder.mLayoutBgStatus.setVisibility(View.INVISIBLE);
            holder.mLayoutFavoriteLike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvFavoriteLike.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            holder.mIvFavoriteLike.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }

        if (mListing.isPrice()) {
            holder.mLayoutPrice.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_dislike_bg));
            holder.mTvDislikePrice.setTextColor(ContextCompat.getColor(mContext, R.color.color_dislike));
            holder.mIvDislikePrice.setColorFilter(ContextCompat.getColor(mContext, R.color.color_dislike));
        } else {
            holder.mLayoutPrice.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvDislikePrice.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            holder.mIvDislikePrice.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }
        if (mListing.isCondition()) {
            holder.mLayoutCondition.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_dislike_bg));
            holder.mTvCondition.setTextColor(ContextCompat.getColor(mContext, R.color.color_dislike));
            holder.mIvCondition.setColorFilter(ContextCompat.getColor(mContext, R.color.color_dislike));
        } else {
            holder.mLayoutCondition.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvCondition.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            holder.mIvCondition.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }

        if (mListing.isLocation()) {
            holder.mLayoutLocation.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_dislike_bg));
            holder.mTvLocation.setTextColor(ContextCompat.getColor(mContext, R.color.color_dislike));
            holder.mIvLocation.setColorFilter(ContextCompat.getColor(mContext, R.color.color_dislike));
        } else {
            holder.mLayoutLocation.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvLocation.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            holder.mIvLocation.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }

        if (mListing.isSize()) {
            holder.mLayoutSize.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_dislike_bg));
            holder.mTvSize.setTextColor(ContextCompat.getColor(mContext, R.color.color_dislike));
            holder.mIvSize.setColorFilter(ContextCompat.getColor(mContext, R.color.color_dislike));
        } else {
            holder.mLayoutSize.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvSize.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextMain));
            holder.mIvSize.setColorFilter(ContextCompat.getColor(mContext, R.color.colorTextMain));
        }

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//
//        holder.mLayoutStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        holder.mLayoutQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isQueue()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "schedule");
                } else {
                    holder.mLayoutQueue.setVisibility(View.INVISIBLE);
                    mListing.setPrice(false);
                    mListing.setCondition(false);
                    mListing.setSize(false);
                    mListing.setLocation(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "schedule");
                }
            }
        });

//        holder.mLayoutFavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mListing.isFavorite()) {
//                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "favorite");
//                } else {
//                    mListing.setPrice(false);
//                    mListing.setCondition(false);
//                    mListing.setSize(false);
//                    mListing.setLocation(false);
//                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "favorite");
//                }
//            }
//        });


        holder.mLayoutFavoriteLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isFavorite()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "favorite");
                } else {
                    mListing.setPrice(false);
                    mListing.setCondition(false);
                    mListing.setSize(false);
                    mListing.setLocation(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "favorite");
                }
            }
        });

        holder.mLayoutSuperVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isSuperVote()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "super_vote");
                } else {
                    mListing.setPrice(false);
                    mListing.setCondition(false);
                    mListing.setSize(false);
                    mListing.setLocation(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "super_vote");
                }
            }
        });

        holder.mLayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isShare()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "share");
                } else {
                    mListing.setPrice(false);
                    mListing.setCondition(false);
                    mListing.setSize(false);
                    mListing.setLocation(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "share");
                }
            }
        });

        holder.mLayoutSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isQueue()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "schedule");
                } else {
                    mListing.setPrice(false);
                    mListing.setCondition(false);
                    mListing.setSize(false);
                    mListing.setLocation(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "up", "schedule");
                }
            }
        });

        holder.mLayoutCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isCondition()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "down", "condition");
                } else {
                    mListing.setSuperVote(false);
                    mListing.setQueue(false);
                    mListing.setShare(false);
                    mListing.setFavorite(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "down", "condition");
                }
            }
        });

        holder.mLayoutPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isPrice()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "down", "price");
                } else {
                    mListing.setSuperVote(false);
                    mListing.setQueue(false);
                    mListing.setShare(false);
                    mListing.setFavorite(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "down", "price");
                }
            }
        });

        holder.mLayoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isLocation()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "down", "location");
                } else {
                    mListing.setSuperVote(false);
                    mListing.setQueue(false);
                    mListing.setShare(false);
                    mListing.setFavorite(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "down", "location");
                }
            }
        });

        holder.mLayoutSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListing.isSize()) {
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "down", "size");
                } else {
                    mListing.setSuperVote(false);
                    mListing.setQueue(false);
                    mListing.setShare(false);
                    mListing.setFavorite(false);
                    mListener.voteListing(mPosition, mListing.getListing().getId(), "down", "size");
                }
            }
        });

        holder.mAddress1.setText(mListing.getListing().getAddress1());
        holder.mAddress2.setText(mListing.getListing().getAddress2());
        holder.mPrice.setText(Utils.getPrice(mListing.getListing().getPrice()));
        holder.mBath.setText(mListing.getListing().getBaths());
        holder.mBed.setText(mListing.getListing().getBeds());
        holder.mLiving.setText(mListing.getListing().getLivingSquare() + " ft");

    }

    @Override
    public SearchListItemHolder getViewHolder(View v) {
        return new ItemSwipeWithActionWidthNoSpringViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.search_map_list_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.search_list_item;
    }


    /**
     * The type Item swipe with action width view holder.
     */
    class ItemSwipeWithActionWidthViewHolder extends SearchListItemHolder implements Extension {

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

    public static class SearchListItemHolder extends RecyclerView.ViewHolder {

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

        //        @Bind(R.id.ivFavorite)
//        ImageView mIvFavorite;
//        @Bind(R.id.tvFavorite)
//        TextView mTvFavorite;
//        @Bind(R.id.layoutFavorite)
//        LinearLayout mLayoutFavorite;
//        @Bind(R.id.ivQueue)
//        ImageView mIvQueue;
//        @Bind(R.id.tvQueue)
//        TextView mTvQueue;
        @Bind(R.id.layoutQueue)
        LinearLayout mLayoutQueue;
        @Bind(R.id.layoutStatus)
        LinearLayout mLayoutStatus;

        @Bind(R.id.ivStatus)
        ImageView mIvStatus;
        //        @Bind(R.id.tvStatus)
//        TextView mTvStatus;
        @Bind(R.id.mActionContainer)
        public RelativeLayout mActionContainer;
        @Bind(R.id.layoutContent)
        public LinearLayout mViewContent;
        @Bind(R.id.layoutLike)
        public LinearLayout mLayoutLike;
        @Bind(R.id.layoutDislike)
        public LinearLayout mLayoutDislike;
        @Bind(R.id.layoutBgStatus)
        RelativeLayout mLayoutBgStatus;
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
        @Bind(R.id.layoutInfo)
        RelativeLayout mLayoutInfo;

        public SearchListItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
