package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.ResponseNotification;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ResponseNotification.Notifications> mArrNotifications;
    private Context mContext;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_ITEM = 0;
    private OnLoadMoreListener mListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private int page = 0;

    public void setOnLoadMoreListener(OnLoadMoreListener mListener) {
        this.mListener = mListener;
    }

    public void setLoaded() {
        isLoading = !isLoading;
    }

    public void resetPage(){
        page = 0;
    }

    public NotificationsAdapter(RecyclerView recyclerView, Context mContext, final ArrayList<ResponseNotification.Notifications> mArrNotifications) {
        this.mContext = mContext;
        this.mArrNotifications = mArrNotifications;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mListener != null) {
                        page++;
                        mListener.onLoadMore(mArrNotifications.get(0).getFilterType(), page);
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.notifications_item, parent, false);
            return new NotificationsHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.notifications_load_more_item, parent, false);
            return new NotificationsLoadMoreHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NotificationsHolder) {
            ResponseNotification.Notifications notification = mArrNotifications.get(position);
            NotificationsHolder myHolder = (NotificationsHolder) holder;

            myHolder.mTvNotificationName.setText(notification.getMessage());
            myHolder.mTvNotificationTime.setText(notification.getCreatedDatetime());

            Glide.with(mContext.getApplicationContext()).load(R.drawable.sana).asBitmap().fitCenter()
                    .placeholder(R.drawable.avatar_default)
                    .dontAnimate()
                    .into(myHolder.mImgAvatar);

            myHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if (holder instanceof NotificationsLoadMoreHolder) {
            NotificationsLoadMoreHolder myHolder = (NotificationsLoadMoreHolder) holder;
            myHolder.mPbLoadMore.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mArrNotifications.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mArrNotifications == null ? 0 : mArrNotifications.size();
    }

    public class NotificationsHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.imgAvatar)
        CircleImageView mImgAvatar;
        @Bind(R.id.tvNotificationName)
        TextView mTvNotificationName;
        @Bind(R.id.tvNotificationTime)
        TextView mTvNotificationTime;

        public NotificationsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class NotificationsLoadMoreHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.pbLoadMore)
        ProgressBar mPbLoadMore;

        public NotificationsLoadMoreHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(String type, int page);
    }
}
