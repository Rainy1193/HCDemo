package com.homecaravan.android.consumer.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.loginmvp.LoginPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.LoginView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterFacebookPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterFacebookView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterLinkedinPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterLinkedinView;
import com.homecaravan.android.consumer.model.responseapi.ResponseUser;
import com.homecaravan.android.consumer.model.responseapi.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConsumerSplashActivity extends BaseActivity implements LoginView, RegisterFacebookView, RegisterLinkedinView {
    private LoginPresenter mLoginPresenter;
    private String mEmailOrPhone;
    private String mPassword;
    private RegisterFacebookPresenter mRegisterFbPresenter;
    private RegisterLinkedinPresenter mRegisterLiPresenter;
    @Bind(R.id.logo)
    ImageView mLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_consumer);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
        mRegisterFbPresenter = new RegisterFacebookPresenter(this);
        mRegisterLiPresenter = new RegisterLinkedinPresenter(this);
        initAnimation();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash_consumer;
    }

    public void checkLogin() {
        SharedPreferences prefs = getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        if (prefs.contains("production")) {
            String production = prefs.getString("production", "");
            if (production.equalsIgnoreCase("com")) {
                Constants.getInstance().setURL_BASE("http://api.homecaravan.com/");
            } else {
                Constants.getInstance().setURL_BASE("http://api.homecaravan.net/");
            }
        } else {
            edit.putString("production", "net");
            Constants.getInstance().setURL_BASE("http://api.homecaravan.net/");
            edit.apply();
        }
        if (prefs.getString("idFacebook", "").length() > 0) {
            if (isNetworkConnected()) {
                String email = prefs.getString("mobileEmail", "");
                String firstName = prefs.getString("firstName", "");
                String lastName = prefs.getString("lastName", "");
                String id = prefs.getString("idFacebook", "");
                mRegisterFbPresenter.register(firstName + " " + lastName, email, "", id, Constants.DEVICE_TOKEN);
            } else {
                finish();
                Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
            }
        } else if (prefs.getString("idLinkedIn", "").length() > 0) {
            if (isNetworkConnected()) {
                String email = prefs.getString("mobileEmail", "");
                String firstName = prefs.getString("firstName", "");
                String lastName = prefs.getString("lastName", "");
                String id = prefs.getString("idLinkedIn", "");
                mRegisterLiPresenter.register(firstName, lastName, email, id, "", Constants.DEVICE_TOKEN);
            } else {
                finish();
                Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
            }
        } else {
            if (prefs.getString("mobileEmail", "").length() > 0) {
                mEmailOrPhone = prefs.getString("mobileEmail", "");
                mPassword = prefs.getString("password", "");
                if (isNetworkConnected()) {
                    mLoginPresenter.login(mEmailOrPhone, mPassword);
                } else {
                    finish();
                    Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
                }
            }
        }
    }

    public void initAnimation() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(this.mLogo, "scaleX", 5.0f, 1.0f);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1000);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(this.mLogo, "scaleY", 5.0f, 1.0f);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1000);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(this.mLogo, "alpha", 0.0f, 1.0f);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(0);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                checkLogin();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void loginSuccess(ResponseUser responseUser) {
        setDataUser(responseUser.getUserData());
    }

    @Override
    public void loginFail(String message) {
        finish();
        Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void serverError(@StringRes int message) {
        finish();
        Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void accountNotYetActivated(@StringRes int message, String emailOrPhone) {
        finish();
        Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }


    public void setDataUser(User dataUser) {
        try {
            ConsumerUser.getInstance().getData().setId(dataUser.getId());
            ConsumerUser.getInstance().getData().setEmail(dataUser.getEmail());
            ConsumerUser.getInstance().getData().setFirstName(dataUser.getFirstName());
            ConsumerUser.getInstance().getData().setLastName(dataUser.getLastName());
            ConsumerUser.getInstance().getData().setFullName(dataUser.getFullName());
            ConsumerUser.getInstance().getData().setMobilePhone(dataUser.getPhone());
            ConsumerUser.getInstance().getData().setPhoto(dataUser.getAvatar());
            ConsumerUser.getInstance().getData().setToken(dataUser.getToken());
            ConsumerUser.getInstance().getData().setRegionCode(dataUser.getRegionCode());
            ConsumerUser.getInstance().getData().setPnUID(dataUser.getPnUid());
            ConsumerUser.getInstance().getData().setProfileUrl(dataUser.getProfileUrl());
            ConsumerUser.getInstance().getData().setVideoUrl(dataUser.getVideoUrl());
            ConsumerUser.getInstance().getData().setIntro(dataUser.getIntro());
            ConsumerUser.getInstance().getData().setAboutMe(dataUser.getAboutMe());
            ConsumerUser.getInstance().getData().setIntro(dataUser.getIntro());
            ConsumerUser.getInstance().getData().setAddress(dataUser.getAddress());
            ConsumerUser.getInstance().getData().setBusinessRole(dataUser.getBusinessRole());
            ConsumerUser.getInstance().getData().setReceiveNotifications(dataUser.getReceiveNotifications());
            ConsumerUser.getInstance().getData().setNewHomesNotifications(dataUser.getNewHomesNotifications());
            ConsumerUser.getInstance().getData().setEmailSmsNotifications(dataUser.getEmailSmsNotifications());

            if (dataUser.getCompany().size() != 0) {
                ConsumerUser.getInstance().getData().setCompanyId(dataUser.getCompany().get(0).getId());
                ConsumerUser.getInstance().getData().setCompanyTitle("");
                ConsumerUser.getInstance().getData().setCompanyAdd1(dataUser.getCompany().get(0).getAddress().getAddress1());
                ConsumerUser.getInstance().getData().setCompanyAdd2(dataUser.getCompany().get(0).getAddress().getAddress2());
                ConsumerUser.getInstance().getData().setCompanyCity(dataUser.getCompany().get(0).getAddress().getCity());
                ConsumerUser.getInstance().getData().setCompanyState(dataUser.getCompany().get(0).getAddress().getState());
                ConsumerUser.getInstance().getData().setCompanyZip(dataUser.getCompany().get(0).getAddress().getZip());
                ConsumerUser.getInstance().getData().setCompanyPhone(dataUser.getCompany().get(0).getPhone());
                ConsumerUser.getInstance().getData().setCompanyLogo(dataUser.getCompany().get(0).getLogo());
            }
            if (dataUser.getAgent() != null) {
                ConsumerUser.getInstance().getData().setHasAgent("yes");
                ConsumerUser.getInstance().getData().setAgentId(dataUser.getAgent().getId());
                ConsumerUser.getInstance().getData().setAgentEmail(dataUser.getAgent().getEmail());
                ConsumerUser.getInstance().getData().setAgentFirstName(dataUser.getAgent().getFirstName());
                ConsumerUser.getInstance().getData().setAgentLastName(dataUser.getAgent().getLastName());
                ConsumerUser.getInstance().getData().setAgentFullName(dataUser.getAgent().getFullName());
                ConsumerUser.getInstance().getData().setAgentMobilePhone(dataUser.getAgent().getPhone());
                ConsumerUser.getInstance().getData().setAgentPhoto(dataUser.getAgent().getAvatar());
                if (dataUser.getAgent().getCompany().size() != 0)
                    ConsumerUser.getInstance().getData().setAgentCompany(dataUser.getAgent().getCompany().get(0));
                ConsumerUser.getInstance().getData().setAgentPnUid(dataUser.getAgent().getPnUid());
            } else {
                ConsumerUser.getInstance().getData().setHasAgent("no");
            }

            SharedPreferences prefs = getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("firstLogin", true);
            edit.apply();
            finish();
            Intent intent = new Intent(this, MainActivityConsumer.class);
            boolean mIsStartFromMain = getIntent().getBooleanExtra("START_FROM_MAIN_ACTIVITY", false);
            if (mIsStartFromMain) {
                intent.putExtra("START_FROM_SPLASH_ACTIVITY", true);
                intent.putExtra("CARAVAN_IN_ACTION", HomeCaravanApplication.mIsCaravanInAction);
                intent.putExtra("CARAVAN_IN_ACTION_ID", HomeCaravanApplication.mCaravanID);
            }
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
            Log.e("Error", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void registerFacebookSuccess(ResponseUser responseUser) {
        setDataUser(responseUser.getUserData());
    }

    @Override
    public void registerFacebookFail(String message) {
        finish();
        Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void registerLinkedinSuccess(ResponseUser responseUser) {
        setDataUser(responseUser.getUserData());
    }

    @Override
    public void registerLinkedinFail(String message) {
        finish();
        Intent intent = new Intent(this, ConsumerRegisterLoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("DaoDiDem", "Splash onNewIntent: ");
        HomeCaravanApplication.mIsCaravanInAction = intent.getBooleanExtra("CARAVAN_IN_ACTION", false);
        HomeCaravanApplication.mCaravanID = intent.getStringExtra("CARAVAN_IN_ACTION_ID");
        Log.d("DaoDiDem", "onNewIntent - mIsCaravanInAction: " + HomeCaravanApplication.mIsCaravanInAction
                + " mCaravanID: " + HomeCaravanApplication.mCaravanID);
    }
}
