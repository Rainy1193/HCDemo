package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anh Dao on 7/20/2017.
 */

public class TitleInsuranceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public TitleInsuranceAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_insurance_item, parent, false);
        vh = new TitleInsuranceHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TitleInsuranceHolder titleInsuranceHolder = (TitleInsuranceHolder) holder;

        titleInsuranceHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (position % 2 == 1) {
            Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_consumer_my_team_corp_logo_5).asBitmap().fitCenter()
                    .placeholder(R.drawable.no_image_b)
                    .dontAnimate()
                    .into(titleInsuranceHolder.mImgLogoTitleInsurance);

            titleInsuranceHolder.mTvCompanyName.setText("Fidelity National Title Agency");
        } else {
            Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_consumer_my_team_corp_logo_4).asBitmap().fitCenter()
                    .placeholder(R.drawable.no_image_b)
                    .dontAnimate()
                    .into(titleInsuranceHolder.mImgLogoTitleInsurance);

            titleInsuranceHolder.mTvCompanyName.setText("First National Title Insurance");
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class TitleInsuranceHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.tvCompanyName)
        TextView mTvCompanyName;
        @Bind(R.id.imgLogoTitleInsurance)
        RoundedImageView mImgLogoTitleInsurance;


        public TitleInsuranceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}