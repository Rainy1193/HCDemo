package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ConsumerTeam> mArrTeam;

    public HistoryAdapter(Context context, ArrayList<ConsumerTeam> mArrTeam) {
        this.mContext = context;
        this.mArrTeam = mArrTeam;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_item, null, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ConsumerTeam history = mArrTeam.get(position);
        HistoryHolder historyHolder = (HistoryHolder) holder;

        Glide.with(mContext.getApplicationContext()).load(history.getPhoto()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(historyHolder.mImgAvatar);

        if(position % 2 != 1){
            Glide.with(mContext.getApplicationContext()).load("https://cdn.houseplans.com/product/2f50bfq27ig6qogbkms91mud82/w1024.jpg").asBitmap().fitCenter()
                    .placeholder(R.drawable.no_image_b)
                    .dontAnimate()
                    .into(historyHolder.mImgNotification);

            historyHolder.mTvTimeAgo.setText("1:15 pm 5/5");
            historyHolder.mTvNotification.setText(Html.fromHtml(mContext.getString(R.string.text_history_my_team)));
            historyHolder.mImgNotification.setVisibility(View.VISIBLE);
            historyHolder.mFrmBackGroupIcon.setVisibility(View.INVISIBLE);
        }else{
            Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_consumer_myteam_heart).asBitmap().fitCenter()
                    .placeholder(R.drawable.no_image_listing)
                    .dontAnimate()
                    .into(historyHolder.mImgNotificationIcon);

            historyHolder.mTvTimeAgo.setText("15 minutes ago");
            historyHolder.mTvNotification.setText("Rin Le contacted with you for listing 2130 Main Street");
            historyHolder.mFrmBackGroupIcon.setBackgroundResource(R.drawable.bg_red_e2);
            historyHolder.mFrmBackGroupIcon.setVisibility(View.VISIBLE);
            historyHolder.mImgNotification.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mArrTeam.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.lnHistoryItem)
        LinearLayout mLnHistoryItem;
        @Bind(R.id.imgAvatar)
        CircleImageView mImgAvatar;
        @Bind(R.id.tvTimeAgo)
        TextView mTvTimeAgo;
        @Bind(R.id.tvNotification)
        TextView mTvNotification;
        @Bind(R.id.imgNotification)
        ImageView mImgNotification;
        @Bind(R.id.imgNotificationIcon)
        ImageView mImgNotificationIcon;
        @Bind(R.id.frmBackGroupIcon)
        FrameLayout mFrmBackGroupIcon;

        public HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
