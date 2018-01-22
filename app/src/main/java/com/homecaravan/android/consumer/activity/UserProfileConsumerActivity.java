package com.homecaravan.android.consumer.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.BuildConfig;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.accountsettingmvp.AccountSettingPresenter;
import com.homecaravan.android.consumer.consumermvp.accountsettingmvp.IAccountSettingView;
import com.homecaravan.android.consumer.consumermvp.updateuser.UpdateUserPresenter;
import com.homecaravan.android.consumer.consumermvp.updateuser.UpdateUserView;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.Companies;
import com.homecaravan.android.handling.ValidateData;
import com.homecaravan.android.models.Country;
import com.homecaravan.android.signup.PickCountry;
import com.homecaravan.android.ui.CircleImageView;
import com.kyleduo.switchbutton.SwitchButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserProfileConsumerActivity extends BaseActivity implements IAccountSettingView, UpdateUserView {


    private final String TAG = "DaoDiDem";
    private boolean mIsEditing;
    private boolean mIsChangePassword;
    private String notifications, newHomes, emailSmsNotifications;
    private AlertDialog alertDialog;
    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;
    private String currentPhotoPath;
    private boolean changePhoto = false;
    private Uri destinationUri;
    private File saveFile;
    private static final String IMAGE_USER = "imageUserEdit.jpg";
    private int mWidthPage;
    private final int PICK_COUNTRY = 3;
    private String mRegionCode;
    private String mRegionName;
    private String mCountryCode;
    private ArrayList<Country> mArrCountry;
    private ArrayList<Companies> mArrCompany = new ArrayList<>();
    private ArrayAdapter<Companies> mCompaniesAdapter;
    private Companies mCurrentCompanies;
    private UpdateUserPresenter mUpdateUserPresenter;
    private String mImageNameHasUpload = "";
    private Timer mTimer;

    @Bind(R.id.layoutMain)
    ScrollView mLayoutMain;
    @Bind(R.id.layoutProfile)
    LinearLayout mLayoutProfile;
    @Bind(R.id.layoutEdit)
    LinearLayout mLayoutEdit;
    @Bind(R.id.imgAvatar)
    RoundedImageView mImgAvatar;
    @Bind(R.id.imgUpload)
    ImageView mImgUpload;
    @Bind(R.id.tvName)
    TextView mTvName;
    @Bind(R.id.tvPhone)
    TextView mTvPhone;
    @Bind(R.id.tvMail)
    TextView mTvMail;
    @Bind(R.id.tvAddress)
    TextView mTvAddress;
    @Bind(R.id.sbNotifications)
    SwitchButton mSbNotifications;
    @Bind(R.id.sbNewHomeOffers)
    SwitchButton mSbNewHomeOffers;
    @Bind(R.id.sbEmailSMS)
    SwitchButton mSbEmailSMS;
    @Bind(R.id.imgAvatarAgent)
    CircleImageView mImgAvatarAgent;
    @Bind(R.id.tvNameAgent)
    TextView mTvNameAgent;
    @Bind(R.id.tvEmailAgent)
    TextView mTvEmailAgent;
    @Bind(R.id.tvPhoneAgent)
    TextView mTvPhoneAgent;
    @Bind(R.id.sbBuyer)
    SwitchButton mSbBuyer;
    @Bind(R.id.lnLogout)
    LinearLayout mLnLogout;
    @Bind(R.id.layoutAgent)
    LinearLayout mLayoutAgent;
    @Bind(R.id.tvTitle)
    TextView mTvTitle;
    @Bind(R.id.ivSetting)
    ImageView mIvSetting;

    @Bind(R.id.edtEditFirstName)
    EditText mEdtEditFirstName;
    @Bind(R.id.edtEditLastName)
    EditText mEdtEditLastName;
    @Bind(R.id.edtEditPhone)
    EditText mEdtEditPhone;
    @Bind(R.id.edtEditEmail)
    EditText mEdtEditEmail;
    @Bind(R.id.edtEditAddress)
    EditText mEdtEditAddress;
    @Bind(R.id.edtEditCompany)
    AppCompatAutoCompleteTextView mEdtEditCompany;
    @Bind(R.id.edtEditTitle)
    EditText mEdtEditTitle;
    @Bind(R.id.edtEditProfileUrl)
    EditText mEdtEditProfileUrl;
    @Bind(R.id.edtEditVideoUrl)
    EditText mEdtEditVideoUrl;
    @Bind(R.id.edtEditFacebookUrl)
    EditText mEdtEditFacebookUrl;
    @Bind(R.id.edtEditAboutMe)
    EditText mEdtEditAboutMe;
    @Bind(R.id.edtEditIntro)
    EditText mEdtEditIntro;

    @Bind(R.id.imgUpLoadAvatar)
    RoundedImageView mImgUploadAvatar;
    @Bind(R.id.layoutChangePassword)
    LinearLayout mLayoutChangePassword;
    @Bind(R.id.edtOldPassword)
    EditText mEdtOldPassword;
    @Bind(R.id.edtNewPassword)
    EditText mEdtNewPassword;
    @Bind(R.id.edtConfirmPassword)
    EditText mEdtConfirmPassword;
    @Bind(R.id.imgRemoveOldPassword)
    ImageView mImgRemoveOldPassword;
    @Bind(R.id.imgRemoveNewPassword)
    ImageView mImgRemoveNewPassword;
    @Bind(R.id.imgRemoveConfirmPassword)
    ImageView mImgRemoveConfirmPassword;
    @Bind(R.id.imgShowOldPassword)
    ImageView mImgShowOldPassword;
    @Bind(R.id.imgShowNewPassword)
    ImageView mImgShowNewPassword;
    @Bind(R.id.imgShowConfirmPassword)
    ImageView mImgShowConfirmPassword;
    @Bind(R.id.frmChangePasswordUpdate)
    FrameLayout mFrmChangePasswordUpdate;
    @Bind(R.id.layoutAddress)
    LinearLayout mLayoutAddress;
    @Bind(R.id.imgCountry)
    ImageView mImgCountry;
    @Bind(R.id.frmEditDone)
    FrameLayout mFrmEditDone;

    @Bind(R.id.tvAboutMe)
    TextView mTvAboutMe;
    @Bind(R.id.tvJobTitle)
    TextView mTvJobTitle;
    @Bind(R.id.imgLogoCompany)
    ImageView mImgLogoCompany;
    @Bind(R.id.pbUploadAvatar)
    ProgressBar mPbUploadAvatar;

    @OnClick(R.id.lnLogout)
    void logout() {
        showDialog(TypeDialog.CONFIRM, "Do you want sign out ?", "singOut");
    }

    @OnClick(R.id.ivBack)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.lnExtraProfile)
    void onExtraProfileClicked() {
        if (ConsumerUser.getInstance().getData().getProfileUrl() == null) {
            return;
        }
        if (Patterns.WEB_URL.matcher(ConsumerUser.getInstance().getData().getProfileUrl()).matches()) {
            Intent terms = new Intent(Intent.ACTION_VIEW);
            terms.setData(Uri.parse(ConsumerUser.getInstance().getData().getProfileUrl()));
            startActivity(terms);
        }
    }

    @OnClick(R.id.lnExtraVideo)
    void onExtraVideoClicked() {
        if (ConsumerUser.getInstance().getData().getVideoUrl() == null) {
            return;
        }
        if (Patterns.WEB_URL.matcher(ConsumerUser.getInstance().getData().getVideoUrl()).matches()) {
            Intent terms = new Intent(Intent.ACTION_VIEW);
            terms.setData(Uri.parse(ConsumerUser.getInstance().getData().getVideoUrl()));
            startActivity(terms);
        }
    }

    @OnClick(R.id.lnExtraFacebook)
    void onExtraFacebookClicked() {
        if (ConsumerUser.getInstance().getData().getFacebookUrl() == null) {
            return;
        }
        if (Patterns.WEB_URL.matcher(ConsumerUser.getInstance().getData().getFacebookUrl()).matches()) {
            Intent terms = new Intent(Intent.ACTION_VIEW);
            terms.setData(Uri.parse(ConsumerUser.getInstance().getData().getFacebookUrl()));
            startActivity(terms);
        }
    }

    @OnClick(R.id.frmEditDone)
    void onEditDoneClicked() {
        if (checkChangedSettings()) {
            showPopupConfirm();
        } else {
            onBackPressed();
        }
    }

    private boolean checkChangedSettings() {
        if(ConsumerUser.getInstance().getData().getCompany() == null){
            Companies companies = new Companies();
            ConsumerUser.getInstance().getData().setCompany(companies);
        }
        return !mEdtEditFirstName.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getFirstName()))
                || !mEdtEditLastName.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getLastName()))
                || !mEdtEditPhone.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getMobilePhone()))
                || !mEdtEditEmail.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getEmail()))
                || !mImageNameHasUpload.equals("")
                || !mEdtEditCompany.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getCompany().getName()))
                || !mEdtEditTitle.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getCompany().getJobTitle()))
                || !mEdtEditAddress.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getAddress()))
                || !mEdtEditProfileUrl.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getProfileUrl()))
                || !mEdtEditVideoUrl.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getVideoUrl()))
                || !mEdtEditFacebookUrl.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getFacebookUrl()))
                || !mEdtEditAboutMe.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getAboutMe()))
                || !mEdtEditIntro.getText().toString().equals(getUserDetailString(ConsumerUser.getInstance().getData().getIntro()));
    }

    private String getUserDetailString(String str) {
        if (str == null)
            return "";
        return str;
    }

    @OnClick(R.id.ivSetting)
    void settingAccount() {
        mIvSetting.setVisibility(View.INVISIBLE);
        mEdtEditFirstName.setText(ConsumerUser.getInstance().getData().getFirstName());
        mEdtEditLastName.setText(ConsumerUser.getInstance().getData().getLastName());
        mEdtEditPhone.setText(ConsumerUser.getInstance().getData().getMobilePhone());
        checkCountry(mCountryCode);
        mEdtEditEmail.setText(ConsumerUser.getInstance().getData().getEmail());
        if (ConsumerUser.getInstance().getData().getAddress() != null) {
            mEdtEditAddress.setText(ConsumerUser.getInstance().getData().getAddress());
        }
        if (mCurrentCompanies != null) {
            mEdtEditCompany.setText(mCurrentCompanies.getName());
            mEdtEditTitle.setText(mCurrentCompanies.getJobTitle());
        }

        mEdtEditProfileUrl.setText(ConsumerUser.getInstance().getData().getProfileUrl());
        mEdtEditVideoUrl.setText(ConsumerUser.getInstance().getData().getVideoUrl());
        mEdtEditFacebookUrl.setText(ConsumerUser.getInstance().getData().getFacebookUrl());
        mEdtEditAboutMe.setText(ConsumerUser.getInstance().getData().getAboutMe());
        mEdtEditIntro.setText(ConsumerUser.getInstance().getData().getIntro());
        mCompaniesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mArrCompany);
        mCompaniesAdapter.setNotifyOnChange(true);
        mEdtEditCompany.setAdapter(mCompaniesAdapter);
        mEdtEditCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Companies) {
                    Companies companies = (Companies) item;
                    mCurrentCompanies = companies;
                    mEdtEditCompany.setText(companies.getName());
                    mEdtEditTitle.setText(companies.getJobTitle());
                }
            }
        });

        mEdtEditCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTimer != null) {
                    mTimer.cancel();
                }

                mCompaniesAdapter = new ArrayAdapter<>(UserProfileConsumerActivity.this, android.R.layout.simple_dropdown_item_1line, mArrCompany);
                mEdtEditCompany.setAdapter(mCompaniesAdapter);
                mCompaniesAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String value = mEdtEditCompany.getText().toString();
                        mUpdateUserPresenter.getCompanies(value);
                    }
                }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask
            }
        });


        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLayoutEdit, "translationX", mWidthPage, 0).setDuration(200);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mLayoutEdit.setVisibility(View.VISIBLE);
                mLayoutProfile.setVisibility(View.GONE);
                mTvTitle.setText("Edit Profile");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsEditing = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    private void checkCountry(String code) {
        for (Country c : mArrCountry) {
            String mCountryCode = c.getDialCode().substring(1);
            if (code.equals(mCountryCode)) {
                InputStream inputStream = null;
                try {
                    inputStream = getAssets().open("country/" + c.getCode() + ".png");
                    mImgCountry.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.imgUpLoadAvatar)
    void onUpLoadAvatarClicked() {
        showDialogUpLoadAvatar();
    }

    @OnClick(R.id.imgUpload)
    void onUpLoadClicked() {
        showDialogUpLoadAvatar();
    }

    @OnClick(R.id.imgCountry)
    void onCountryClicked() {
        Intent pickCountry = new Intent(this, PickCountry.class);
        startActivityForResult(pickCountry, PICK_COUNTRY);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnClick(R.id.frmChangePassword)
    void openChangePassword() {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLayoutChangePassword, "translationX", mWidthPage, 0).setDuration(200);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mLayoutChangePassword.setVisibility(View.VISIBLE);
                mLayoutProfile.setVisibility(View.GONE);
                mIvSetting.setVisibility(View.INVISIBLE);
                mTvTitle.setText("Change Password");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsChangePassword = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    @OnClick(R.id.frmChangePasswordUpdate)
    void openChangePasswordUpdateClicked() {
        //validate data
        hideKeyboard();
        String oldPassword = mEdtOldPassword.getText().toString();
        String newPassword = mEdtNewPassword.getText().toString();
        String confirmPassword = mEdtConfirmPassword.getText().toString();

        if (oldPassword.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "validate");
            mEdtOldPassword.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "validate");
            mEdtNewPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "validate");
            mEdtConfirmPassword.requestFocus();
            return;
        }

        if (!ValidateData.isPassword2(newPassword)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_password_length_2, "validate");
            mEdtNewPassword.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_re_password, "validate");
            mEdtNewPassword.requestFocus();
            return;
        }

        if (newPassword.equals(oldPassword)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_same_password, "validate");
            mEdtNewPassword.requestFocus();
            return;
        }

        if (!isNetworkConnected()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.no_network, "no-internet");
            return;
        }

        mFrmChangePasswordUpdate.setClickable(false);
        showLoading();
        //call api
        AccountSettingPresenter accountSettingPresenter = new AccountSettingPresenter(this);
        accountSettingPresenter.changePassword(oldPassword, newPassword, confirmPassword);
    }

    @OnClick(R.id.frmChangePasswordCancel)
    void openChangePasswordCancelClicked() {
        onBackPressed();
    }

    @OnClick(R.id.imgShowOldPassword)
    void openShowOldPasswordClicked() {
        if (mEdtOldPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            mEdtOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_consumer_eye_hide_password_color).asBitmap().into(mImgShowOldPassword);
        } else {
            mEdtOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_consumer_eye_show_password_color).asBitmap().into(mImgShowOldPassword);
        }
    }

    @OnClick(R.id.imgShowNewPassword)
    void openShowNewPasswordClicked() {
        if (mEdtNewPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            mEdtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_consumer_eye_hide_password_color).asBitmap().into(mImgShowNewPassword);
        } else {
            mEdtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_consumer_eye_show_password_color).asBitmap().into(mImgShowNewPassword);
        }
    }

    @OnClick(R.id.imgShowConfirmPassword)
    void openShowConfirmPasswordClicked() {
        if (mEdtConfirmPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            mEdtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_consumer_eye_hide_password_color).asBitmap().into(mImgShowConfirmPassword);
        } else {
            mEdtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_consumer_eye_show_password_color).asBitmap().into(mImgShowConfirmPassword);
        }
    }

    @OnClick(R.id.imgRemoveOldPassword)
    void removeOldPassword() {
        mEdtOldPassword.setText(null);
    }

    @OnClick(R.id.imgRemoveNewPassword)
    void removeNewPassword() {
        mEdtNewPassword.setText(null);
    }

    @OnClick(R.id.imgRemoveConfirmPassword)
    void removeConfirmPassword() {
        mEdtConfirmPassword.setText(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Log.e(TAG, ConsumerUser.getInstance().getData().toString());
        mUpdateUserPresenter = new UpdateUserPresenter(this);
        Glide.with(this).load(ConsumerUser.getInstance().getData().getPhoto()).asBitmap().fitCenter().into(mImgAvatar);
        mTvName.setText(ConsumerUser.getInstance().getData().getFullName());
        mTvPhone.setText(ConsumerUser.getInstance().getData().getMobilePhone());
        mTvMail.setText(ConsumerUser.getInstance().getData().getEmail());
        mTvAboutMe.setText(ConsumerUser.getInstance().getData().getAboutMe());
        mCurrentCompanies = ConsumerUser.getInstance().getData().getCompany();
        if (ConsumerUser.getInstance().getData().getAddress() != null) {
            mTvAddress.setText(ConsumerUser.getInstance().getData().getAddress());
        } else {
            mLayoutAddress.setVisibility(View.GONE);
        }
        if (mCurrentCompanies != null) {
            Glide.with(this).load(mCurrentCompanies.getLogo()).asBitmap().fitCenter().into(mImgLogoCompany);
            mTvJobTitle.setText(mCurrentCompanies.getJobTitle());
        }

        if (ConsumerUser.getInstance().getData().getReceiveNotifications() != null) {
            if (ConsumerUser.getInstance().getData().getReceiveNotifications().equals("on")) {
                mSbNotifications.setChecked(true);
            }
        }

        if (ConsumerUser.getInstance().getData().getNewHomesNotifications() != null) {
            if (ConsumerUser.getInstance().getData().getNewHomesNotifications().equals("on")) {
                mSbNewHomeOffers.setChecked(true);
            }
        }

        if (ConsumerUser.getInstance().getData().getEmailSmsNotifications() != null) {
            if (ConsumerUser.getInstance().getData().getEmailSmsNotifications().equals("on")) {
                mSbEmailSMS.setChecked(true);
            }
        }

        if (ConsumerUser.getInstance().getData().getBusinessRole() != null) {
            if (ConsumerUser.getInstance().getData().getBusinessRole().equals("Buyer")) {
                mSbBuyer.setChecked(false);
            }
        }

        if (ConsumerUser.getInstance().getData().getHasAgent().equalsIgnoreCase("yes")) {
            mLayoutAgent.setVisibility(View.VISIBLE);
            Glide.with(this).load(ConsumerUser.getInstance().getData().getAgentPhoto()).asBitmap().fitCenter().into(mImgAvatarAgent);
            mTvNameAgent.setText(ConsumerUser.getInstance().getData().getAgentFullName());
            mTvEmailAgent.setText(ConsumerUser.getInstance().getData().getAgentEmail());
            mTvPhoneAgent.setText(ConsumerUser.getInstance().getData().getAgentMobilePhone());
        }

        destinationUri = Uri.fromFile(new File(getCacheDir().getPath(), IMAGE_USER));

        mLayoutMain.post(new Runnable() {
            @Override
            public void run() {
                mWidthPage = mLayoutMain.getWidth();
            }
        });

        addTextChangedListener();

        getCountryZipCode();
        mArrCountry = getCountry();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_account_settings;
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (mIsEditing) {
            mIvSetting.setVisibility(View.VISIBLE);
            final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLayoutEdit, "translationX", 0, mWidthPage).setDuration(200);
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mLayoutEdit.setVisibility(View.GONE);
                    mTvTitle.setText("User Profile");
                    mLayoutProfile.setVisibility(View.VISIBLE);
                    mImageNameHasUpload = "";
                    mRegionCode = null;
                    mRegionName = null;
                    mIsEditing = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            objectAnimator.start();
            return;
        }
        if (mIsChangePassword) {
            mIvSetting.setVisibility(View.VISIBLE);
            final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLayoutChangePassword, "translationX", 0, mWidthPage).setDuration(200);
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mLayoutProfile.setVisibility(View.VISIBLE);
                    mTvTitle.setText("User Profile");
                    mLayoutChangePassword.setVisibility(View.GONE);

                    mFrmChangePasswordUpdate.setClickable(true);
                    mEdtOldPassword.setText(null);
                    mEdtNewPassword.setText(null);
                    mEdtConfirmPassword.setText(null);

                    Glide.with(getApplicationContext()).load(R.drawable.ic_consumer_eye_show_password_color).asBitmap().into(mImgShowConfirmPassword);
                    Glide.with(getApplicationContext()).load(R.drawable.ic_consumer_eye_show_password_color).asBitmap().into(mImgShowNewPassword);
                    Glide.with(getApplicationContext()).load(R.drawable.ic_consumer_eye_show_password_color).asBitmap().into(mImgShowOldPassword);

                    mIsChangePassword = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            objectAnimator.start();
            return;
        }
        notifications = mSbNotifications.isChecked() ? "on" : "off";
        newHomes = mSbNewHomeOffers.isChecked() ? "on" : "off";
        emailSmsNotifications = mSbEmailSMS.isChecked() ? "on" : "off";
        if (!notifications.equals(ConsumerUser.getInstance().getData().getReceiveNotifications())
                || !newHomes.equals(ConsumerUser.getInstance().getData().getNewHomesNotifications())
                || !emailSmsNotifications.equals(ConsumerUser.getInstance().getData().getEmailSmsNotifications())) {
            showPopupConfirmSettings();
            return;
        }
        ConsumerUser.getInstance().getData().setCompany(mCurrentCompanies);
        hideKeyboard();
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

    @Override
    public void yesAction(String action) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void noAction(String action) {

    }

    private void showPopupConfirm() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(this);
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_confirm, null);
        TextView tvMessage = (TextView) view1.findViewById(R.id.tvMessage);
        tvMessage.setText("Are you sure you want to update?");
        FrameLayout frmButtonNo = (FrameLayout) view1.findViewById(R.id.frmButtonNo);
        FrameLayout frmButtonYes = (FrameLayout) view1.findViewById(R.id.frmButtonYes);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
                alertDialog.dismiss();
            }
        });

        frmButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void showPopupConfirmSettings() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(this);
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_confirm, null);
        TextView tvMessage = (TextView) view1.findViewById(R.id.tvMessage);
        tvMessage.setText("Are you sure you want to update notification settings?");
        FrameLayout frmButtonNo = (FrameLayout) view1.findViewById(R.id.frmButtonNo);
        FrameLayout frmButtonYes = (FrameLayout) view1.findViewById(R.id.frmButtonYes);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateUserPresenter.updateUserSettings(notifications, newHomes, emailSmsNotifications);
                showLoading();
                alertDialog.dismiss();
            }
        });

        frmButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
                overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
            }
        });
        alertDialog.show();
    }

    private void showDialogUpLoadAvatar() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(this);
        View view1 = layoutInflater1.inflate(R.layout.dialog_take_avatar, null);
        TextView tvGallery = (TextView) view1.findViewById(R.id.tvGallery);
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(UserProfileConsumerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    HomeCaravanApplication.askPermission(UserProfileConsumerActivity.this, UserProfileConsumerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
                    alertDialog.dismiss();
                    return;
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
                alertDialog.dismiss();
            }
        });
        TextView tvCamera = (TextView) view1.findViewById(R.id.tvCamera);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(UserProfileConsumerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    HomeCaravanApplication.askPermission(UserProfileConsumerActivity.this, UserProfileConsumerActivity.this, Manifest.permission.CAMERA, REQUEST_CAMERA);
                    alertDialog.dismiss();
                    return;
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(UserProfileConsumerActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                photoFile);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog = new AlertDialog.Builder(this).setView(view1).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPGE" + timeStamp + "_";
        File storageDir = new File(Constants.FOLDER);
//        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DCIM), "Screenshots");
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Log.e(TAG, "onActivityResult: User Profile");
                }
            }
            if (requestCode == REQUEST_CAMERA) {
                startCropActivity(Uri.parse(currentPhotoPath));
            }
            if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }

        if (resultCode == PICK_COUNTRY) {
            try {
                scrolling(mEdtEditPhone.getBottom());
                mEdtEditPhone.requestFocus();
                InputStream inputStream = getAssets().open(data.getExtras().getString("ensign"));
                mImgCountry.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                mRegionCode = data.getExtras().getString("code");
                mRegionName = data.getExtras().getString("region");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void scrolling(int position) {
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofInt(mLayoutMain, "scrollY", position);
        objectAnimatorY.setStartDelay(0);
        objectAnimatorY.setDuration(200);
        objectAnimatorY.start();
    }

    private void startCropActivity(@NonNull Uri uri) {
        UCrop uCrop = UCrop.of(uri, destinationUri);

        uCrop = advancedConfig(uCrop);
        uCrop.withAspectRatio(1, 1);
        uCrop.start(this);
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            try {
                savePhoto(resultUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showSnackBar(mLayoutMain, TypeDialog.SUCCESS, R.string.takePictureError, "handleCropError");
        }
    }

    private void savePhoto(Uri croppedFileUri) throws Exception {
        String filename = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), croppedFileUri.getLastPathSegment());
        saveFile = new File(Constants.FOLDER, filename);
        FileInputStream inStream = new FileInputStream(new File(croppedFileUri.getPath()));
        FileOutputStream outStream = new FileOutputStream(saveFile);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
        mPbUploadAvatar.setVisibility(View.VISIBLE);
        mImgUploadAvatar.setClickable(false);
        mImgUpload.setClickable(false);
        mUpdateUserPresenter.uploadAvatar(saveFile);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            cropError.printStackTrace();
        }
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, R.string.takePictureError, "handleCropError");
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setToolbarTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        return uCrop.withOptions(options);
    }

    @Override
    public void updateUserSuccess(String firstName, String lastName,
                                  String phone, String email, String avatar,
                                  String companyMappingId, String companyId,
                                  String companyName, String companyTitle,
                                  String extraAddress, String extraUrl,
                                  String extraVideo, String extraFacebook,
                                  String extraAboutMe, String extraIntro) {
        hideLoading();
        mFrmEditDone.setClickable(true);
        if (changePhoto) {
            Picasso.with(UserProfileConsumerActivity.this).load(saveFile).into(mImgAvatar);
        }

        ConsumerUser.getInstance().getData().setFirstName(firstName);
        ConsumerUser.getInstance().getData().setLastName(lastName);
        ConsumerUser.getInstance().getData().setFullName(firstName + " " + lastName);
        ConsumerUser.getInstance().getData().setPhoto(phone);
        ConsumerUser.getInstance().getData().setEmail(email);
        ConsumerUser.getInstance().getData().setPhoto("http://api.consumer.homecaravan.net/uploads/account_avatar/" + avatar);
        if (ConsumerUser.getInstance().getData().getCompany() != null) {
            ConsumerUser.getInstance().getData().getCompany().setId(companyId);
            ConsumerUser.getInstance().getData().getCompany().setName(companyName);
            ConsumerUser.getInstance().getData().getCompany().setJobTitle(companyTitle);
            ConsumerUser.getInstance().getData().getCompany().setMappingId(companyMappingId);
        } else {
            Companies companies = new Companies();
            companies.setId(companyId);
            companies.setName(companyName);
            companies.setJobTitle(companyTitle);
            companies.setMappingId(companyMappingId);
        }
        ConsumerUser.getInstance().getData().setAddress(extraAddress);
        ConsumerUser.getInstance().getData().setProfileUrl(extraUrl);
        ConsumerUser.getInstance().getData().setVideoUrl(extraVideo);
        ConsumerUser.getInstance().getData().setFacebookUrl(extraFacebook);
        ConsumerUser.getInstance().getData().setAboutMe(extraAboutMe);
        ConsumerUser.getInstance().getData().setIntro(extraIntro);

        onBackPressed();
    }

    @Override
    public void updateUserFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "updateUserFail");
        mFrmEditDone.setClickable(true);
    }

    @Override
    public void getCompaniesSuccess(ArrayList<Companies> mArrCompanies) {
        mArrCompany.clear();
        mArrCompany.addAll(mArrCompanies);
        mCompaniesAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCompaniesFail(String message) {
//        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "getCompaniesFail");
    }

    @Override
    public void uploadAvatarSuccess(String imageName) {
        mPbUploadAvatar.setVisibility(View.GONE);
        mImgUploadAvatar.setClickable(true);
        mImgUpload.setClickable(true);
        mImageNameHasUpload = imageName;
        Picasso.with(this).load(saveFile).into(mImgUploadAvatar);
        changePhoto = true;
    }

    @Override
    public void uploadAvatarFail(String message) {
        mPbUploadAvatar.setVisibility(View.GONE);
        mImgUploadAvatar.setClickable(true);
        mImgUpload.setClickable(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "uploadAvatarFail");
        changePhoto = false;
    }

    @Override
    public void updateUserSettingsSuccess(String notifications, String newHomes, String emailSmsNotifications) {
        hideLoading();
        ConsumerUser.getInstance().getData().setReceiveNotifications(notifications);
        ConsumerUser.getInstance().getData().setNewHomesNotifications(newHomes);
        ConsumerUser.getInstance().getData().setEmailSmsNotifications(emailSmsNotifications);
        finish();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

    @Override
    public void updateUserSettingsFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "updateUserSettingsFail");
    }

    @Override
    public void serverError(@StringRes int message) {
        hideLoading();
        mPbUploadAvatar.setVisibility(View.GONE);
        mFrmEditDone.setClickable(true);
        mImgUploadAvatar.setClickable(true);
        mImgUpload.setClickable(true);
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "serverError");
        changePhoto = false;
    }

    @Override
    public void changePasswordSuccess(String token) {
        hideLoading();
        ConsumerUser.getInstance().getData().setToken(token);
        showPopupSuccess("Password has been changed");
    }

    private void showPopupSuccess(String message) {
        LayoutInflater layoutInflater1 = LayoutInflater.from(this);
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_success, null);
        FrameLayout frmButtonOk = (FrameLayout) view1.findViewById(R.id.frmButtonOk);
        TextView tvMessage = (TextView) view1.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void changePasswordFail(String message) {
        hideLoading();
        mFrmChangePasswordUpdate.setClickable(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "change-password-fail");
    }

    @Override
    public void changePasswordFail(@StringRes int message) {
        hideLoading();
        mFrmChangePasswordUpdate.setClickable(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "change-password-fail");
    }

    private void updateUser() {
        String firstName, lastName, phone, email,
                companyMappingId = null, companyId = null, companyName, companyTitle,
                extraAddress, extraUrl, extraVideo, extraFacebook, extraAboutMe, extraIntro;
        boolean gotCompanies = false;
        companyName = mEdtEditCompany.getText().toString().trim();
        companyTitle = mEdtEditTitle.getText().toString().trim();
        if (ConsumerUser.getInstance().getData().getCompany() == null) {
            if (companyName.isEmpty()) {
                gotCompanies = false;
            } else {
                companyMappingId = "0";
                companyId = "0";
                for (Companies c : mArrCompany) {
                    if (companyName.equalsIgnoreCase(c.getName())) {
                        companyId = c.getId();
                        break;
                    }
                }
                gotCompanies = true;
            }
        } else if (mCurrentCompanies != null) {
            if (companyName.isEmpty()
                    || (companyName.equals(ConsumerUser.getInstance().getData().getCompany().getName())
                    && companyTitle.equals(ConsumerUser.getInstance().getData().getCompany().getJobTitle()))) {
                gotCompanies = false;
            } else {
                companyMappingId = ConsumerUser.getInstance().getData().getCompany().getMappingId();
                companyId = mCurrentCompanies.getId();
                gotCompanies = true;
            }
        }

        firstName = mEdtEditFirstName.getText().toString().trim();
        lastName = mEdtEditLastName.getText().toString().trim();
        phone = mEdtEditPhone.getText().toString().trim();
        email = mEdtEditEmail.getText().toString().trim();
        extraAddress = mEdtEditAddress.getText().toString().trim();
        extraUrl = mEdtEditProfileUrl.getText().toString().trim();
        extraVideo = mEdtEditVideoUrl.getText().toString().trim();
        extraFacebook = mEdtEditFacebookUrl.getText().toString().trim();
        extraAboutMe = mEdtEditAboutMe.getText().toString().trim();
        extraIntro = mEdtEditIntro.getText().toString().trim();

        if (firstName.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "validate");
            mEdtEditFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "validate");
            mEdtEditLastName.requestFocus();
            return;
        }

        if (!ValidateData.isName(firstName)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_first_name_correct, "validate");
            mEdtEditFirstName.requestFocus();
            return;
        }

        if (!ValidateData.isName(lastName)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_last_name_correct, "validate");
            mEdtEditLastName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "validate");
            mEdtEditPhone.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "validate");
            mEdtEditEmail.requestFocus();
            return;
        }

        if (!ValidateData.isEmailValid(email)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_email_not_correct, "validate");
            mEdtEditEmail.requestFocus();
            return;
        }

        if (!ValidateData.isPhone(phone)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_phone_not_correct, "validate");
            mEdtEditPhone.requestFocus();
            return;
        }

        if (!extraAddress.isEmpty() && extraAddress.length() > 70) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_long_text, "validate");
            mEdtEditAddress.requestFocus();
            return;
        }

        if (!extraUrl.isEmpty() && !Patterns.WEB_URL.matcher(extraUrl).matches()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_url_not_correct, "validate");
            mEdtEditProfileUrl.requestFocus();
            return;
        }

        if (!extraVideo.isEmpty() && !Patterns.WEB_URL.matcher(extraVideo).matches()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_url_not_correct, "validate");
            mEdtEditVideoUrl.requestFocus();
            return;
        }

        if (!extraFacebook.isEmpty() && !Patterns.WEB_URL.matcher(extraFacebook).matches()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_url_not_correct, "validate");
            mEdtEditFacebookUrl.requestFocus();
            return;
        }

        if (!isNetworkConnected()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.no_network, "no-internet");
            return;
        }

        mFrmEditDone.setClickable(false);
        showLoading();
        mUpdateUserPresenter.updateUser(firstName, lastName, handlerPhone(phone), email, mImageNameHasUpload,
                companyMappingId, companyId, companyName, companyTitle,
                extraAddress, extraUrl, extraVideo, extraFacebook, extraAboutMe, extraIntro, gotCompanies);
    }

    private String handlerPhone(String data) {
        if (mRegionCode != null) {
            if (ValidateData.isPhone(data)) {
                if (data.startsWith("0")) {
                    return mRegionCode + data.substring(1);
                }
                if (data.startsWith("+"))
                    return data;
                return "+" + mCountryCode + data;
            }
        } else {
            if (ValidateData.isPhone(data)) {
                if (data.startsWith("0")) {
                    return "+" + mCountryCode + data.substring(1);
                }
                if (data.startsWith("+"))
                    return data;
                return "+" + mCountryCode + data;
            }
        }
        return data;
    }

    private void getCountryZipCode() {
        String CountryZipCode = "";
        String CountryID = Locale.getDefault().getCountry();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (String aRl : rl) {
            String[] g = aRl.split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                CountryZipCode = g[0];
                break;
            }
        }
        mCountryCode = CountryZipCode;
        Log.e(TAG, "getCountryZipCode: " + mCountryCode);
    }

    private void addTextChangedListener() {
        mEdtOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEdtOldPassword.getText().length() > 0 && mImgRemoveOldPassword.getVisibility() == View.INVISIBLE) {
                    mImgRemoveOldPassword.setVisibility(View.VISIBLE);
                } else if (mEdtOldPassword.getText().length() == 0) {
                    mImgRemoveOldPassword.setVisibility(View.INVISIBLE);
                }

                if (mEdtOldPassword.getText().length() > 0 && mImgShowOldPassword.getVisibility() == View.INVISIBLE) {
                    mImgShowOldPassword.setVisibility(View.VISIBLE);
                } else if (mEdtOldPassword.getText().length() == 0) {
                    mImgShowOldPassword.setVisibility(View.INVISIBLE);
                }
            }
        });

        mEdtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEdtNewPassword.getText().length() > 0 && mImgRemoveNewPassword.getVisibility() == View.INVISIBLE) {
                    mImgRemoveNewPassword.setVisibility(View.VISIBLE);
                } else if (mEdtNewPassword.getText().length() == 0) {
                    mImgRemoveNewPassword.setVisibility(View.INVISIBLE);
                }

                if (mEdtNewPassword.getText().length() > 0 && mImgShowNewPassword.getVisibility() == View.INVISIBLE) {
                    mImgShowNewPassword.setVisibility(View.VISIBLE);
                } else if (mEdtNewPassword.getText().length() == 0) {
                    mImgShowNewPassword.setVisibility(View.INVISIBLE);
                }
            }
        });

        mEdtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEdtConfirmPassword.getText().length() > 0 && mImgRemoveConfirmPassword.getVisibility() == View.INVISIBLE) {
                    mImgRemoveConfirmPassword.setVisibility(View.VISIBLE);
                } else if (mEdtConfirmPassword.getText().length() == 0) {
                    mImgRemoveConfirmPassword.setVisibility(View.INVISIBLE);
                }

                if (mEdtConfirmPassword.getText().length() > 0 && mImgShowConfirmPassword.getVisibility() == View.INVISIBLE) {
                    mImgShowConfirmPassword.setVisibility(View.VISIBLE);
                } else if (mEdtConfirmPassword.getText().length() == 0) {
                    mImgShowConfirmPassword.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private ArrayList<Country> getCountry() {
        Country country;
        ArrayList<Country> arrCountry = new ArrayList<>();
        try {
            InputStream inputStream1 = getAssets().open("file/countries.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream1));
            String line;
            String name = null;
            String dialCode = null;
            String code = null;
            int count = 0;
            while ((line = reader.readLine()) != null) {

                if (line.contains("name")) {
                    name = line.substring(line.indexOf(":") + 3, line.length() - 2);
                    count++;
                }
                if (line.contains("dial_code")) {
                    dialCode = line.substring(line.indexOf(":") + 3, line.length() - 2);
                    count++;
                }
                if (line.contains("code") && !line.contains(",")) {
                    code = line.substring(line.indexOf(":") + 3, line.length() - 1);
                    count++;
                }
                if (count == 3) {
                    country = new Country(name, dialCode, code);
                    arrCountry.add(country);
                    count = 0;
                }
            }
            return arrCountry;
        } catch (Exception e) {
            return arrCountry;
        }
    }
}
