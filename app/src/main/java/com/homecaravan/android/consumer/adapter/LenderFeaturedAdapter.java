package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.consumerteam.FragmentTeamChild;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anh Dao on 7/20/2017.
 */

public class LenderFeaturedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private boolean mBorderAvatarImage;
    private FragmentTeamChild fragmentTeamChild;
    private ArrayList<ConsumerTeam> mArrTeam;

    public LenderFeaturedAdapter(Context mContext, boolean mBorderAvatarImage, FragmentTeamChild fragmentTeamChild, ArrayList<ConsumerTeam> mArrTeam) {
        this.mContext = mContext;
        this.mBorderAvatarImage = mBorderAvatarImage;
        this.fragmentTeamChild = fragmentTeamChild;
        this.mArrTeam = mArrTeam;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.realtor_item, parent, false);
        vh = new LenderHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ConsumerTeam lender = mArrTeam.get(position);
        LenderHolder lenderHolder = (LenderHolder) holder;

        if (!mBorderAvatarImage) {
            lenderHolder.vImgAvatarRealtor.setBorderWidth(R.dimen.non_border_width);
        }


        Glide.with(mContext.getApplicationContext()).load(lender.getPhoto()).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(lenderHolder.vImgAvatarRealtor);

        Glide.with(mContext.getApplicationContext()).load(lender.getLogo()).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(lenderHolder.mImgLogoCompany);

        lenderHolder.mTvName.setText(lender.getFirstName() + " " + lender.getLastName());
        lenderHolder.mTvJobs.setText(lender.getCompany());
        if(lender.getStatus().equals("active")){
            lenderHolder.mVStatus.setBackgroundResource(R.drawable.dot_green_online);
        } else{
            lenderHolder.mVStatus.setBackgroundResource(R.drawable.dot_gray_offline);
        }
    }

    @Override
    public int getItemCount() {
        return mArrTeam.size();
    }

    public class LenderHolder extends RecyclerView.ViewHolder {

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


        public LenderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}