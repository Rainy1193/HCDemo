package com.homecaravan.android.consumer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.PreferenceUtils;
import com.homecaravan.android.ui.FontManager;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppSettingsActivity extends BaseActivity {

    private SharedPreferences mPrefs;

    @Bind(R.id.ivBack)
    ImageView mIvBack;
    @Bind(R.id.tvRate)
    TextView textViewRate;
    @Bind(R.id.tvFacebook)
    TextView textViewFacebook;
    @Bind(R.id.tvTwitter)
    TextView textViewTwitter;
    @Bind(R.id.tvPrivacy)
    TextView textViewPricacy;
    @Bind(R.id.tvTerm)
    TextView textViewTerm;
    @Bind(R.id.tvHelp)
    TextView textViewHelp;
    @Bind(R.id.tvThank)
    TextView textViewThank;
    @Bind(R.id.layoutTimeUpdateLocation)
    LinearLayout mLayoutTimeUpdateLocation;
    @Bind(R.id.radioTimeUpdateLocation)
    RadioGroup mRadioTimeUpdateLocation;
    @Bind(R.id.radio1)
    RadioButton mRadio1;
    @Bind(R.id.radio2)
    RadioButton mRadio2;
    @Bind(R.id.radio3)
    RadioButton mRadio3;
    @Bind(R.id.radio4)
    RadioButton mRadio4;
    @Bind(R.id.radio5)
    RadioButton mRadio5;
    @Bind(R.id.radio6)
    RadioButton mRadio6;
    @Bind(R.id.sbNotificationMessage)
    SwitchButton mSbNotificationMessage;

    @OnClick(R.id.tvFacebook)
    void onFacebookClicked() {
        Intent terms = new Intent(Intent.ACTION_VIEW);
        terms.setData(Uri.parse("https://facebook.com/HomeCaravan"));
        startActivity(terms);
    }

    @OnClick(R.id.layoutRate)
    void onGooglePlayClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://play.google.com"));
        startActivity(intent);
    }

    @OnClick(R.id.tvTwitter)
    public void onTwitterClicked() {
        Intent terms = new Intent(Intent.ACTION_VIEW);
        terms.setData(Uri.parse("https://twitter.com/HomeCaravan"));
        startActivity(terms);
    }

    @OnClick(R.id.rlTimeUpdateLocation)
    public void onTimeUpdateLocationClicked() {
        if (mLayoutTimeUpdateLocation.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutTimeUpdateLocation, null);
        } else {
            mLayoutTimeUpdateLocation.setVisibility(View.VISIBLE);
            AnimUtils.expandView(mLayoutTimeUpdateLocation);
        }
    }

    @OnClick(R.id.ivBack)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.sbNotificationMessage)
    public void onNotificationMessageClicked() {

    }

    @OnClick(R.id.ivSettingSave)
    void onSettingSaveClicked(){
        int selectedId = mRadioTimeUpdateLocation.getCheckedRadioButtonId();
        SharedPreferences.Editor editor = mPrefs.edit();
        // TODO: 12/28/2017 chuyển CIA_FATEST_INTERVAL, CIA_UPDATE_INTERVAL vào PreUlti
        switch (selectedId) {
            case R.id.radio1:
                editor.putInt(Constants.getInstance().CIA_UPDATE_INTERVAL, 605000);
                editor.putInt(Constants.getInstance().CIA_FATEST_INTERVAL, 600000);
                break;
            case R.id.radio2:
                editor.putInt(Constants.getInstance().CIA_UPDATE_INTERVAL, 305000);
                editor.putInt(Constants.getInstance().CIA_FATEST_INTERVAL, 300000);
                break;
            case R.id.radio3:
                editor.putInt(Constants.getInstance().CIA_UPDATE_INTERVAL, 65000);
                editor.putInt(Constants.getInstance().CIA_FATEST_INTERVAL, 60000);
                break;
            case R.id.radio4:
                editor.putInt(Constants.getInstance().CIA_UPDATE_INTERVAL, 35000);
                editor.putInt(Constants.getInstance().CIA_FATEST_INTERVAL, 30000);
                break;
            case R.id.radio5:
                editor.putInt(Constants.getInstance().CIA_UPDATE_INTERVAL, 20000);
                editor.putInt(Constants.getInstance().CIA_FATEST_INTERVAL, 15000);
                break;
            case R.id.radio6:
                editor.putInt(Constants.getInstance().CIA_UPDATE_INTERVAL, 10000);
                editor.putInt(Constants.getInstance().CIA_FATEST_INTERVAL, 5000);
                break;
        }
        editor.apply();
        HomeCaravanApplication.mReceiverMessageNotification = mSbNotificationMessage.isChecked();
        PreferenceUtils.setSettingsReceiverNewMessageNoti(this, HomeCaravanApplication.mReceiverMessageNotification);

        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        checkUserSetting();

        textViewPricacy.setTypeface(FontManager.getTypeface(this));
        textViewTerm.setTypeface(FontManager.getTypeface(this));
        textViewHelp.setTypeface(FontManager.getTypeface(this));
        textViewThank.setTypeface(FontManager.getTypeface(this));
        textViewRate.setTypeface(FontManager.getTypeface(this));
        textViewFacebook.setTypeface(FontManager.getTypeface(this));
        textViewTwitter.setTypeface(FontManager.getTypeface(this));

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_consumer_settings;
    }

    private void checkUserSetting() {
        mPrefs = getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, Context.MODE_PRIVATE);
        if (mPrefs == null) {
            return;
        }
        int fastestInterval = mPrefs.getInt(Constants.getInstance().CIA_FATEST_INTERVAL, 60000);
        switch (fastestInterval) {
            case 600000:
                mRadio1.setChecked(true);
                break;
            case 300000:
                mRadio2.setChecked(true);
                break;
            case 60000:
                mRadio3.setChecked(true);
                break;
            case 30000:
                mRadio4.setChecked(true);
                break;
            case 15000:
                mRadio5.setChecked(true);
                break;
            case 5000:
                mRadio6.setChecked(true);
                break;
            default:
                mRadio3.setChecked(true);
                break;
        }

        mSbNotificationMessage.setChecked(PreferenceUtils.getSettingsReceiverNewMessageNoti(this));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

}
