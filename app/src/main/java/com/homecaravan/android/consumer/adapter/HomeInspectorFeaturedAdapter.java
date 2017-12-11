package com.homecaravan.android.consumer.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.AgentProfileInfomationActivity;
import com.homecaravan.android.consumer.activity.AgentReviewActivity;
import com.homecaravan.android.consumer.consumerteam.FragmentTeamChild;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anh Dao on 7/20/2017.
 */

public class HomeInspectorFeaturedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private boolean mBorderAvatarImage;
    private FragmentTeamChild fragmentTeamChild;
    private ArrayList<ConsumerTeam> mArrTeam;

    public HomeInspectorFeaturedAdapter(Context mContext, boolean mBorderAvatarImage, FragmentTeamChild fragmentTeamChild, ArrayList<ConsumerTeam> mArrTeam) {
        this.mContext = mContext;
        this.mBorderAvatarImage = mBorderAvatarImage;
        this.fragmentTeamChild = fragmentTeamChild;
        this.mArrTeam = mArrTeam;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.realtor_item, parent, false);
        vh = new HomeInspectorHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ConsumerTeam homeInspector = mArrTeam.get(position);
        HomeInspectorHolder homeInspectorHolder = (HomeInspectorHolder) holder;

        homeInspectorHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater1 = LayoutInflater.from(mContext);
                View view1 = layoutInflater1.inflate(R.layout.dialog_item_consumer_myteam_realtor, null);
                TextView tvViewInfomation = (TextView) view1.findViewById(R.id.tvViewInfomation);
                TextView tvSetExclusive = (TextView) view1.findViewById(R.id.tvSetExclusive);
                TextView tvAgentReview = (TextView) view1.findViewById(R.id.tvAgentReview);
                TextView tvCancel = (TextView) view1.findViewById(R.id.tvCancel);

                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).setView(view1).create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
                wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                wmlp.y = 200;   //y position

                tvViewInfomation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, AgentProfileInfomationActivity.class));
                        alertDialog.dismiss();
                    }
                });

                tvSetExclusive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentTeamChild.showSelectedHomeInspector(homeInspector);
                        alertDialog.dismiss();
                    }
                });

                tvAgentReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, AgentReviewActivity.class));
                        alertDialog.dismiss();
                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        if (!mBorderAvatarImage) {
            homeInspectorHolder.vImgAvatarRealtor.setBorderWidth(R.dimen.non_border_width);
        }


        Glide.with(mContext.getApplicationContext()).load(homeInspector.getPhoto()).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(homeInspectorHolder.vImgAvatarRealtor);

        Glide.with(mContext.getApplicationContext()).load(homeInspector.getLogo()).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(homeInspectorHolder.mImgLogoCompany);

        homeInspectorHolder.mTvName.setText(homeInspector.getFirstName() + " " + homeInspector.getLastName());

        homeInspectorHolder.mTvJobs.setText(homeInspector.getCompany());

        if(homeInspector.getStatus().equals("active")){
            homeInspectorHolder.mVStatus.setBackgroundResource(R.drawable.dot_green_online);
        } else{
            homeInspectorHolder.mVStatus.setBackgroundResource(R.drawable.dot_gray_offline);
        }
    }

    @Override
    public int getItemCount() {
        return mArrTeam.size();
    }

    public class HomeInspectorHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.tvJobs)
        TextView mTvJobs;
        @Bind(R.id.vStatus)
        View mVStatus;
        @Bind(R.id.imgAvatarRealtor)
        RoundedImageView vImgAvatarRealtor;
        @Bind(R.id.imgLogoCompany)
        ImageView mImgLogoCompany;


        public HomeInspectorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}