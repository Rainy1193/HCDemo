package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AgentProfileInfomationActivity extends AppCompatActivity {

    @Bind(R.id.imgAvatarRealtor)
    RoundedImageView mImgAvatarRealtor;
    @Bind(R.id.imgLogoCompany)
    ImageView mImgLogoCompany;
    @Bind(R.id.tvAgentName)
    TextView mTvAgentName;
    @Bind(R.id.tvAgentCompany)
    TextView mTvAgentCompany;
    @Bind(R.id.tvAgentPhone)
    TextView mTvAgentPhone;
    @Bind(R.id.tvAgentEmail)
    TextView mTvAgentEmail;
    @Bind(R.id.tvAgentAddress)
    TextView mTvAgentAddress;
    @Bind(R.id.frmMessage)
    FrameLayout mFrmMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile_infomation);
        ButterKnife.bind(this);

        initImage();
    }

    private void initImage(){
        Glide.with(this).load(R.drawable.ic_nayeon).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(mImgAvatarRealtor);
        Glide.with(this).load(R.drawable.ic_consumer_agent_profile_logo_corp).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(mImgLogoCompany);
    }
}
