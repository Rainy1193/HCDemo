package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Anh Dao on 11/10/2017.
 */

public class ViewUserProfileConsumerActivity extends BaseActivity {

    @Bind(R.id.imgAvatar)
    RoundedImageView imgAvatar;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.imgLogoCompany)
    ImageView imgLogoCompany;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.layoutAddress)
    LinearLayout layoutAddress;
    @Bind(R.id.tvAboutMe)
    TextView tvAboutMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_user_profile_consumer;
    }

    @OnClick({R.id.ivBack, R.id.frmSendMessage, R.id.lnExtraProfile, R.id.lnExtraVideo, R.id.lnExtraFacebook, R.id.frmAddToContact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.frmSendMessage:
                break;
            case R.id.lnExtraProfile:
                break;
            case R.id.lnExtraVideo:
                break;
            case R.id.lnExtraFacebook:
                break;
            case R.id.frmAddToContact:
                break;
        }
    }
}
