package com.homecaravan.android.consumer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.consumerintro.FragmentIntroStepFour;
import com.homecaravan.android.consumer.consumerintro.FragmentIntroStepOne;
import com.homecaravan.android.consumer.consumerintro.FragmentIntroStepThree;
import com.homecaravan.android.consumer.consumerintro.FragmentIntroStepTwo;
import com.homecaravan.android.handling.DepthPageTransformer;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends AppCompatActivity implements FragmentIntroStepFour.IntroListener {
    private SharedPreferences mCheckLogin;
    private SharedPreferences mCheckLogin1;
    private SharedPreferences mCheckType;
    private SharedPreferences mCheckOpen;
    private GoogleMap mMap;
    private FragmentIntroStepOne mFragmentOne;
    private FragmentIntroStepTwo mFragmentTwo;
    private FragmentIntroStepThree mFragmentThree;
    private FragmentIntroStepFour mFragmentFour;
    @Bind(R.id.imgTop)
    RoundedImageView mIvTop;
    @Bind(R.id.imgBottom)
    RoundedImageView mIvBottom;
    @Bind(R.id.layoutSplash)
    RelativeLayout mLayoutSplash;
    @Bind(R.id.layoutIntro)
    RelativeLayout mLayoutIntro;
    @Bind(R.id.viewPagerIntro)
    ViewPager mViewPagerIntro;
    @Bind(R.id.viewStepOne)
    View mViewOne;
    @Bind(R.id.viewStepTwo)
    View mViewTwo;
    @Bind(R.id.viewStepThree)
    View mViewThree;
    @Bind(R.id.viewStepFour)
    View mViewFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mCheckType = getSharedPreferences("typeLogin", MODE_PRIVATE);
        mCheckLogin = getSharedPreferences("homecaravanConsumer", MODE_PRIVATE);
        mCheckLogin1 = getSharedPreferences("homecaravan", MODE_PRIVATE);
        mCheckOpen = getSharedPreferences("firstOpen", MODE_PRIVATE);
        if (mCheckLogin.getBoolean("firstSplash", false)) {
            mLayoutIntro.setVisibility(View.GONE);
            topSplashClick();
        } else {
            mLayoutIntro.setVisibility(View.VISIBLE);
        }
        SharedPreferences.Editor editor = mCheckLogin.edit();
        editor.putBoolean("firstSplash", true);
        editor.apply();

        mFragmentOne = new FragmentIntroStepOne();
        mFragmentTwo = new FragmentIntroStepTwo();
        mFragmentThree = new FragmentIntroStepThree();
        mFragmentFour = new FragmentIntroStepFour();
        mFragmentFour.setListener(this);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(mFragmentOne, "");
        viewPagerAdapter.addFragment(mFragmentTwo, "");
        viewPagerAdapter.addFragment(mFragmentThree, "");
        viewPagerAdapter.addFragment(mFragmentFour, "");
        mViewPagerIntro.setPageTransformer(true, new DepthPageTransformer());
        mViewPagerIntro.setAdapter(viewPagerAdapter);
        mViewPagerIntro.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mViewOne.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_show));
                    mViewTwo.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewThree.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewFour.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                }
                if (position == 1) {
                    mViewOne.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewTwo.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_show));
                    mViewThree.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewFour.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                }
                if (position == 2) {
                    mViewOne.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewTwo.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewThree.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_show));
                    mViewFour.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                }
                if (position == 3) {
                    mFragmentFour.showGetStart();
                    mViewOne.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewTwo.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewThree.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_hide));
                    mViewFour.setBackground(ContextCompat.getDrawable(SplashActivity.this, R.drawable.bg_step_intro_show));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.rlTopSplash)
    public void topSplashClick() {
        SharedPreferences.Editor editor = mCheckType.edit();
        editor.putString("type", "consumer");
        editor.apply();
        if (mCheckLogin.getString("mobileEmail", "").length() > 0) {
            Intent intent = new Intent(this, ConsumerSplashActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        finish();
    }

    @OnClick(R.id.rlBottomSplash)
    public void bottomSplashClick() {

    }


    @Override
    public void cancelIntro() {
        topSplashClick();
        mLayoutIntro.setVisibility(View.GONE);
    }
}