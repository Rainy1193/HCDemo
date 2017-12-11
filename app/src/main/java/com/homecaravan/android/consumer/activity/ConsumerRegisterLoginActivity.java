package com.homecaravan.android.consumer.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.adapter.SearchAgentAdapter;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.broadcast.SMSReceiver;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.contactmvp.SearchContactPresenter;
import com.homecaravan.android.consumer.consumermvp.contactmvp.SearchContactView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.ForgotPasswordPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.ForgotPasswordView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.LoginPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.LoginView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterFacebookPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterFacebookView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterLinkedinPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterLinkedinView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.RegisterView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.VerifyAccountPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.VerifyAccountView;
import com.homecaravan.android.consumer.fragment.FragmentEnterCode;
import com.homecaravan.android.consumer.fragment.FragmentQrCode;
import com.homecaravan.android.consumer.listener.IScanOrCode;
import com.homecaravan.android.consumer.listener.SMSListener;
import com.homecaravan.android.consumer.model.FacebookData;
import com.homecaravan.android.consumer.model.LinkedInData;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.consumer.model.responseapi.ResponseRegister;
import com.homecaravan.android.consumer.model.responseapi.ResponseUser;
import com.homecaravan.android.consumer.model.responseapi.User;
import com.homecaravan.android.consumer.unlockagent.ScanOrCodePresenter;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.widget.EasyFlipView;
import com.homecaravan.android.handling.ValidateData;
import com.homecaravan.android.ui.CircleImageView;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;


public class ConsumerRegisterLoginActivity extends BaseActivity implements LoginView,
        RegisterView,
        RegisterFacebookView,
        RegisterLinkedinView,
        IScanOrCode,
        VerifyAccountView,
        ForgotPasswordView,
        SearchContactView,
        SearchAgentAdapter.SetAgentView {
    private boolean mShowLogin = true;
    private LoginPresenter mLoginPresenter;
    private RegisterPresenter mRegisterPresenter;
    private RegisterFacebookPresenter mRegisterFbPresenter;
    private RegisterLinkedinPresenter mRegisterLiPresenter;
    private int mWidthPage;
    private SharedPreferences mPrefs;
    private CallbackManager mCallbackManager;
    private String mAccessToken;
    private String mCountryCode;
    private LinkedInData mLiData;
    private FacebookData mFbData;
    private String mHost = "api.linkedin.com";
    private String mTopCardUrl = "https://" + mHost + "/v1/people/~:" +
            "(id,email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";

    private FragmentEnterCode mFragmentEnterCode;
    private ScanOrCodePresenter scanOrCodePresenter;
    private User agentInfo;

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e("onSuccess", "onSuccess");
            mAccessToken = loginResult.getAccessToken().getToken();
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.e("onCompleted", "onCompleted");
                            LoginManager.getInstance().logOut();
                            mFbData = new FacebookData();
                            try {
                                String id = "", firstName = "", lastName = "", email = "", avatar = "";
                                if (object.getString("id") != null) {
                                    id = object.getString("id");
                                    avatar = "https://graph.facebook.com/" + id + "/picture?type=large";
                                    Log.e("id", id);
                                }
                                if (object.getString("first_name") != null) {
                                    firstName = object.getString("first_name");
                                    Log.e("email", firstName);
                                }
                                if (object.getString("last_name") != null) {
                                    lastName = object.getString("last_name");
                                    Log.e("email", lastName);
                                }
                                if (object.has("email")) {
                                    if (object.getString("email") != null) {
                                        email = object.getString("email");
                                        Log.e("email", email);
                                    }
                                }
                                mFbData.setAvatar(avatar);
                                mFbData.setFirstName(firstName);
                                mFbData.setLastName(lastName);
                                mFbData.setId(id);
                                mFbData.setEmail(email);
                                showLoading();
                                if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !id.isEmpty()) {
                                    mRegisterFbPresenter.register(firstName + " " + lastName, email, avatar, id, Constants.DEVICE_TOKEN);
                                }
                            } catch (JSONException e) {
                                LoginManager.getInstance().logOut();
                                Log.e("Error", e.toString());
                            }
                        }
                    }
            );
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name,last_name,email");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {
            Log.e("e", e.toString());
            LoginManager.getInstance().logOut();
        }
    };

    @Bind(R.id.flipView)
    EasyFlipView mFlipView;
    @Bind(R.id.tvRegister)
    TextView mTvRegister;
    @Bind(R.id.tvLogin)
    TextView mTvLogin;
    @Bind(R.id.emailPhoneLogin)
    EditText mEtEmailPhoneLogin;
    @Bind(R.id.passwordLogin)
    EditText mEtPasswordLogin;
    @Bind(R.id.btnEmailLogin)
    RelativeLayout mBtLogin;
    @Bind(R.id.layoutLoginRegister)
    LinearLayout mLayoutLoginRegister;
    @Bind(R.id.layoutUnlockAgent)
    RelativeLayout mLayoutUnlockAgent;
    @Bind(R.id.btFacebook)
    LoginButton mLoginFacebook;
    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;
    @Bind(R.id.layoutUnlockStepOne)
    LinearLayout mLayoutUnlockStepOne;
    @Bind(R.id.layoutUnlockStepTwo)
    LinearLayout mLayoutUnlockStepTwo;
    @Bind(R.id.layoutUnlockStepThree)
    LinearLayout mLayoutUnlockStepThree;
    @Bind(R.id.layoutScanOrCode)
    LinearLayout mLayoutScanOrCode;
    @Bind(R.id.layoutUnlockStepSearch)
    LinearLayout mLayoutUnlockStepSearch;

    @Bind(R.id.ivBgAgent)
    ImageView mIvBgAgent;
    @Bind(R.id.ivAgent)
    CircleImageView mIvAgent;
    @Bind(R.id.tvName)
    TextView mTvName;
    @Bind(R.id.tvAddress)
    TextView mTvAddress;
    @Bind(R.id.tvZip)
    TextView mTvZip;

    @Bind(R.id.edtFirstName)
    EditText mFirstName;
    @Bind(R.id.edtLastName)
    EditText mLastName;
    @Bind(R.id.actvEmail)
    EditText mEmail;
    @Bind(R.id.edtPassword)
    EditText mPassword;
    @Bind(R.id.edtConfirmPassword)
    EditText mConfirmPassword;
    @Bind(R.id.btnRegister)
    RelativeLayout mLayoutRegister;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.vpScanOrCode)
    ViewPager mViewPager;

    //Verify
    @Bind(R.id.layoutVerify)
    LinearLayout mLayoutVerify;
    @Bind(R.id.edtVerifyEmailOrPhone)
    EditText mEdtVerifyEmailOrPhone;
    @Bind(R.id.edtVerifyEnterCode)
    EditText mEdtVerifyEnterCode;
    @Bind(R.id.rlVerify)
    RelativeLayout mRlVerify;
    @Bind(R.id.rlResendCode)
    RelativeLayout mRlResendCode;
    @Bind(R.id.tvResendCode)
    TextView mTvResendCode;
    private String userId = "";
    private String mMailOrPhone;
    private final int READ_SMS_PERMISSION = 90;
    private final int RECEIVER_SMS_PERMISSION = 91;
    private VerifyAccountPresenter mVerifyAccountPresenter;

    @Bind(R.id.layoutForgotPassword)
    LinearLayout mLayoutForgotPassword;
    @Bind(R.id.edtForgotPasswordEmailOrPhone)
    EditText mEdtForgotPasswordEmailOrPhone;
    @Bind(R.id.rlForgotPassword)
    RelativeLayout mRlForgotPassword;

    //Search agent
    @Bind(R.id.rvSearchAgent)
    RecyclerView mRvSearchAgent;
    @Bind(R.id.layoutSearchEmpty)
    LinearLayout mLayoutSearchEmpty;
    @Bind(R.id.edtSearch)
    EditText mEdtSearch;
    @Bind(R.id.imgCloseSearch)
    ImageView mImgCloseSearch;
    @Bind(R.id.imgSearch)
    ImageView mImgSearch;

    private boolean mFirstOpenSearchAgent = true;
    private Timer mTimer;
    private SearchAgentAdapter mSearchAgentAdapter;
    private SearchContactPresenter mSearchContactPresenter;
    private ArrayList<ContactData> mArrContactData;

    @OnClick(R.id.ivBack)
    void onBackClicked() {
        mLayoutUnlockAgent.setVisibility(View.VISIBLE);
        mLayoutUnlockStepTwo.setVisibility(View.VISIBLE);
        AnimUtils.slideRightToLeft(mLayoutScanOrCode, 0, mWidthPage, true);
    }

    @OnClick(R.id.rlFacebook)
    void loginFacebook() {
        mLoginFacebook.performClick();
    }

    @OnClick(R.id.rlLinkedin)
    void loginLinkedin() {
        LISessionManager.getInstance(getApplicationContext()).init(ConsumerRegisterLoginActivity.this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
                apiHelper.getRequest(ConsumerRegisterLoginActivity.this, mTopCardUrl, new ApiListener() {
                    @Override
                    public void onApiSuccess(ApiResponse result) {
                        try {
                            JSONObject jsonObject = result.getResponseDataAsJson();
                            LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken();
                            Log.e("jsonObject", jsonObject.toString());
                            mLiData = new LinkedInData();
                            String firstName = "", lastName = "", id = "", email = "", avatar = "";
                            if (jsonObject.getString("emailAddress") != null) {
                                email = jsonObject.getString("emailAddress");
                                Log.e("email", email);
                            }
                            if (jsonObject.getString("id") != null) {
                                id = jsonObject.getString("id");
                            }

                            if (jsonObject.optJSONObject("pictureUrls") == null) {
                                avatar = jsonObject.getString("pictureUrls");
                            }
                            if (jsonObject.getString("formattedName") != null) {
                                String formattedName = jsonObject.getString("formattedName");
                                if (formattedName.contains(" ")) {
                                    firstName = formattedName.substring(0, formattedName.lastIndexOf(" "));
                                    lastName = formattedName.substring(formattedName.lastIndexOf(" ") + 1);
                                } else {
                                    firstName = "";
                                    lastName = formattedName;
                                }
                            }
                            mLiData.setAvatar(avatar);
                            mLiData.setFirstName(lastName);
                            mLiData.setLastName(firstName);
                            mLiData.setId(id);
                            mLiData.setEmail(email);
                            showLoading();
                            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !id.isEmpty()) {
                                mRegisterLiPresenter.register(firstName, lastName, email, id, avatar, Constants.DEVICE_TOKEN);
                            }

                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                        }

                    }

                    @Override
                    public void onApiError(LIApiError error) {
                        Log.e("error", error.toString());
                    }
                });
            }

            @Override
            public void onAuthError(LIAuthError error) {
                Log.e("error", error.toString());
            }
        }, true);
    }

    @OnClick(R.id.layoutAlreadyAgent)
    void onLayoutAlreadyAgent() {
        mLayoutUnlockStepOne.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutUnlockStepTwo, mWidthPage, 0, true);
    }

    @OnClick(R.id.layoutNotReady)
    void onLayoutNotReady() {
        mLayoutUnlockAgent.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutLoginRegister, mWidthPage, 0, true);
    }

    @OnClick(R.id.layoutScanQRCode)
    void onLayoutScanQRCode() {
        mLayoutUnlockAgent.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutScanOrCode, mWidthPage, 0, true);
    }

    @OnClick(R.id.imgClose)
    void onSearchBackClicked() {
        mLayoutUnlockAgent.setVisibility(View.VISIBLE);
        mLayoutUnlockStepTwo.setVisibility(View.VISIBLE);
        AnimUtils.slideRightToLeft(mLayoutUnlockStepSearch, 0, mWidthPage, true);
    }

    @OnClick(R.id.imgCloseSearch)
    void onCloseSearchClicked() {
        mEdtSearch.setText(null);
        mImgCloseSearch.setVisibility(View.GONE);
        mRvSearchAgent.setVisibility(View.GONE);
        mImgSearch.setVisibility(View.VISIBLE);
        mLayoutSearchEmpty.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layoutSearchForAgentName)
    void onLayoutSearchForAgentName() {
        if (mFirstOpenSearchAgent) {
            mSearchContactPresenter = new SearchContactPresenter(this);
            mArrContactData = new ArrayList<>();
            mSearchAgentAdapter = new SearchAgentAdapter(this, mArrContactData, this);
            mRvSearchAgent.setAdapter(mSearchAgentAdapter);
            mFirstOpenSearchAgent = false;
        }
        mLayoutUnlockStepTwo.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutUnlockStepSearch, mWidthPage, 0, true);
    }

    @OnClick(R.id.btnStart)
    void onButtonStart() {
        mLayoutUnlockAgent.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutLoginRegister, mWidthPage, 0, true);
    }

    @OnClick(R.id.layoutMain)
    void onLayoutMain() {
        hideKeyboard();
    }

    @OnClick(R.id.tvRegister)
    void showLayoutRegister(View view) {
        if (!mShowLogin) {
            return;
        }
        onLayoutChange();
        hideKeyboard();
        mTvLogin.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTvLogin.setClickable(true);
            }
        }, 400);
        mFlipView.flipTheView(true);
        mTvRegister.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mTvLogin.setTextColor(ContextCompat.getColor(this, R.color.colorLogin1));
        mShowLogin = false;
    }

    @OnClick(R.id.tvRegisterBottom)
    void onTvRegisterBottom() {
        if (!mShowLogin) {
            return;
        }
        onLayoutChange();
        hideKeyboard();
        mFlipView.flipTheView(true);
        mTvRegister.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mTvLogin.setTextColor(ContextCompat.getColor(this, R.color.colorLogin1));
        mShowLogin = false;
    }

    @OnClick(R.id.tvLogin)
    void showLayoutLogin(View view) {
        if (mShowLogin) {
            return;
        }
        onLayoutChange();
        hideKeyboard();
        mTvRegister.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTvRegister.setClickable(true);
            }
        }, 400);
        mFlipView.flipTheView(true);
        mTvRegister.setTextColor(ContextCompat.getColor(this, R.color.colorLogin1));
        mTvLogin.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mShowLogin = true;
    }

    @OnClick(R.id.tvLoginBottom)
    void onTvLoginBottom() {
        if (mShowLogin) {
            return;
        }
        onLayoutChange();
        hideKeyboard();
        mFlipView.flipTheView(true);
        mTvRegister.setTextColor(ContextCompat.getColor(this, R.color.colorLogin1));
        mTvLogin.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mShowLogin = true;
    }

    @OnClick(R.id.btnEmailLogin)
    void onButtonLogin() {
        hideKeyboard();
        String emailOrPhone = mEtEmailPhoneLogin.getText().toString().trim();
        String password = mEtPasswordLogin.getText().toString();

        if (emailOrPhone.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "register-login");
            mEtEmailPhoneLogin.requestFocus();
            return;
        }

        if (!ValidateData.isPhone(emailOrPhone) && !ValidateData.isEmailValid(emailOrPhone)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_email_phone_not_correct, "register-login");
            mEtEmailPhoneLogin.requestFocus();
            return;
        }

        if (!ValidateData.isPassword(password)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_password_length, "register-login");
            mEtPasswordLogin.requestFocus();
            return;
        }

        if (!isNetworkConnected()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.no_network, "register-login");
            return;
        }

        mBtLogin.setClickable(false);
        showLoading();
        mLoginPresenter.login(handlerPhone(emailOrPhone), password);
    }

    @OnClick(R.id.btnRegister)
    void onButtonRegister() {
        hideKeyboard();
        String emailOrPhone = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString();
        String firstName = mFirstName.getText().toString().trim();
        String lastName = mLastName.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        if (firstName.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "register-login");
            mFirstName.requestFocus();
            return;
        }

        if (!ValidateData.isName(firstName)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_first_name_correct, "register-login");
            mFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "register-login");
            mLastName.requestFocus();
            return;
        }

        if (!ValidateData.isName(lastName)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_last_name_correct, "register-login");
            mLastName.requestFocus();
            return;
        }

        if (emailOrPhone.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "register-login");
            mEtEmailPhoneLogin.requestFocus();
            return;
        }

        if (!ValidateData.isPhone(emailOrPhone) && !ValidateData.isEmailValid(emailOrPhone)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_email_phone_not_correct, "register-login");
            mEtEmailPhoneLogin.requestFocus();
            return;
        }

        if (!ValidateData.isPassword(password)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_password_length, "register-login");
            mEtPasswordLogin.requestFocus();
            return;
        }

        if (!confirmPassword.equalsIgnoreCase(password)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_re_password, "register-login");
            mConfirmPassword.requestFocus();
            return;
        }

        if (!isNetworkConnected()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.no_network, "register-login");
            return;
        }

        mLayoutRegister.setClickable(false);
        mRegisterPresenter.register(firstName, lastName, handlerPhone(emailOrPhone), password);
        showLoading();
        mMailOrPhone = emailOrPhone;
    }

    @OnClick(R.id.tvForgotPassword)
    void onForgotPassword() {
        hideKeyboard();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCallbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("notification")) {
                Intent intent = new Intent(this, ConsumerSplashActivity.class);
                intent.putExtra("notification", "notification");
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
                finish();
            }
        }
        getCountryZipCode();
        mPrefs = getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, MODE_PRIVATE);
        mLoginFacebook.setReadPermissions(Collections.singletonList("public_profile, email"));
        mLoginFacebook.registerCallback(mCallbackManager, mCallback);
        if (!mPrefs.getBoolean("firstLogin", false)) {
            mLayoutUnlockAgent.setVisibility(View.VISIBLE);
            mLayoutLoginRegister.setVisibility(View.GONE);
        }
        mLoginPresenter = new LoginPresenter(this);
        mRegisterPresenter = new RegisterPresenter(this);
        mRegisterFbPresenter = new RegisterFacebookPresenter(this);
        mRegisterLiPresenter = new RegisterLinkedinPresenter(this);
        mEtPasswordLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                onButtonLogin();
                hideKeyboard();
                return false;
            }
        });

        mConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                onButtonRegister();
                hideKeyboard();
                return false;
            }
        });

        mLayoutMain.post(new Runnable() {
            @Override
            public void run() {
                mWidthPage = mLayoutMain.getWidth();
            }
        });

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        FragmentQrCode mFragmentQrCode = new FragmentQrCode();
        mFragmentEnterCode = new FragmentEnterCode();
        mViewPagerAdapter.addFragment(mFragmentQrCode, "Scan QR Code");
        mViewPagerAdapter.addFragment(mFragmentEnterCode, "Enter Your Code");
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mFragmentQrCode.setIScanOrCodeListener(this);
        mFragmentEnterCode.setIScanOrCodeListener(this);
        scanOrCodePresenter = new ScanOrCodePresenter();
        mVerifyAccountPresenter = new VerifyAccountPresenter(this);

        mEdtVerifyEnterCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                onVerifyClicked();
                hideKeyboard();
                return false;
            }
        });


        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTimer != null) {
                    mTimer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTimer = new Timer();
                if (s.length() != 0) {
                    mImgSearch.setVisibility(View.GONE);
                    mImgCloseSearch.setVisibility(View.VISIBLE);
                } else {
                    mImgSearch.setVisibility(View.VISIBLE);
                    mImgCloseSearch.setVisibility(View.GONE);
                }
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String value = mEdtSearch.getText().toString();
                        mSearchContactPresenter.searchContact(value);
                    }
                }, 800); // 800ms delay before the timer executes the „run“ method from TimerTask
            }
        });
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register_login_consumer;
    }

    @Override
    public void loginSuccess(ResponseUser responseUser) {
        hideLoading();
        setDataUser(responseUser.getUserData(), "Normal", false);
        if ("no".equals(ConsumerUser.getInstance().getData().getHasAgent()) && agentInfo != null) {
            //set agent
            scanOrCodePresenter.setAgent(ConsumerUser.getInstance().getData().getId(), agentInfo);
        }
        Intent intent = new Intent(this, MainActivityConsumer.class);
        startActivity(intent);
        mBtLogin.setClickable(true);
        finish();
        mEtPasswordLogin.setText(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void loginFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "register-login");
        mEtPasswordLogin.setText(null);
        mBtLogin.setClickable(true);
    }

    @Override
    public void accountNotYetActivated(@StringRes int message, String emailOrPhone) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "register-login");
        mEtPasswordLogin.setText(null);
        mBtLogin.setClickable(true);
        mLayoutLoginRegister.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutVerify, mWidthPage, 0, true);
        mEdtVerifyEmailOrPhone.setText(emailOrPhone);
        if (ValidateData.isPhone(mMailOrPhone)) {
            if (ActivityCompat.checkSelfPermission(ConsumerRegisterLoginActivity.this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                HomeCaravanApplication.askPermission(ConsumerRegisterLoginActivity.this, ConsumerRegisterLoginActivity.this, android.Manifest.permission.READ_SMS, READ_SMS_PERMISSION);
            }
            if (ActivityCompat.checkSelfPermission(ConsumerRegisterLoginActivity.this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                HomeCaravanApplication.askPermission(ConsumerRegisterLoginActivity.this, ConsumerRegisterLoginActivity.this, android.Manifest.permission.RECEIVE_SMS, RECEIVER_SMS_PERMISSION);
            }
            messageListener();
        }
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (mLoginPresenter.getCall() != null) {
            mLoginPresenter.cancelCall();
            hideLoading();
            return;
        }
        if (mVerifyAccountPresenter.getCall() != null) {
            mVerifyAccountPresenter.cancelCall();
            hideLoading();
            return;
        }
        if (mLayoutVerify.getVisibility() == View.VISIBLE) {
            mLayoutLoginRegister.setVisibility(View.VISIBLE);
            AnimUtils.slideRightToLeft(mLayoutVerify, 0, mWidthPage, true);
            return;
        }
        if (mLayoutForgotPassword.getVisibility() == View.VISIBLE) {
            mLayoutLoginRegister.setVisibility(View.VISIBLE);
            AnimUtils.slideRightToLeft(mLayoutForgotPassword, 0, mWidthPage, true);
            return;
        }
        if (mLayoutScanOrCode.getVisibility() == View.VISIBLE) {
            mLayoutLoginRegister.setVisibility(View.VISIBLE);
            mLayoutUnlockAgent.setVisibility(View.GONE);
            AnimUtils.slideRightToLeft(mLayoutScanOrCode, 0, mWidthPage, true);
            return;
        }
        if (mLayoutUnlockStepTwo.getVisibility() == View.VISIBLE) {
            mLayoutUnlockStepOne.setVisibility(View.VISIBLE);
            AnimUtils.slideRightToLeft(mLayoutUnlockStepTwo, 0, mWidthPage, true);
            return;
        }
        if (mLayoutUnlockStepSearch.getVisibility() == View.VISIBLE) {
            mLayoutUnlockStepTwo.setVisibility(View.VISIBLE);
            AnimUtils.slideRightToLeft(mLayoutUnlockStepSearch, 0, mWidthPage, true);
            return;
        }
        if (mLayoutUnlockStepThree.getVisibility() == View.VISIBLE) {
            mLayoutUnlockStepTwo.setVisibility(View.VISIBLE);
            AnimUtils.slideRightToLeft(mLayoutUnlockStepThree, 0, mWidthPage, true);
            return;
        }
        if (mLayoutUnlockStepOne.getVisibility() == View.VISIBLE && mLayoutUnlockAgent.getVisibility() == View.VISIBLE) {
//            mLayoutLoginRegister.setVisibility(View.VISIBLE);
//            AnimUtils.slideRightToLeft(mLayoutUnlockStepOne, 0, mWidthPage, true);
            super.onBackPressed();
            return;
        }
        if (mLayoutVerify.getVisibility() == View.VISIBLE) {
            mLayoutLoginRegister.setVisibility(View.VISIBLE);
            AnimUtils.slideRightToLeft(mLayoutVerify, 0, mWidthPage, true);
            return;
        }
        if (!mShowLogin && mLayoutLoginRegister.getVisibility() == View.VISIBLE) {
            mFlipView.flipTheView(true);
            mTvRegister.setTextColor(ContextCompat.getColor(this, R.color.colorLogin1));
            mTvLogin.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            mShowLogin = true;
            return;
        }
        super.onBackPressed();
    }

    public void onLayoutChange() {
        mTvRegister.setEnabled(false);
        mTvLogin.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTvRegister.setEnabled(true);
                mTvLogin.setEnabled(true);
            }
        }, 400);
    }

    public String handlerPhone(String data) {
        if (ValidateData.isPhone(data)) {
            if (data.startsWith("0")) {
                return "+" + mCountryCode + data.substring(1);
            }
            return "+" + mCountryCode + data;
        }
        return data;
    }

    public void setDataUser(User dataUser, String typeLogin, boolean isVerify) {

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
            if (typeLogin.equalsIgnoreCase("Normal")) {
                saveDataLogin(isVerify);
            }
            if (typeLogin.equalsIgnoreCase("Facebook")) {
                saveDataLoginFacebook();
            }
            if (typeLogin.equalsIgnoreCase("Linkedin")) {
                saveDataLoginLinkedin();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDataLogin(boolean isVerify) {
        SharedPreferences.Editor edit = mPrefs.edit();
        if (isVerify) {
            edit.putString("mobileEmail", mEdtVerifyEmailOrPhone.getText().toString().trim());
        } else {
            edit.putString("mobileEmail", mEtEmailPhoneLogin.getText().toString().trim());
        }
        edit.putString("driverToken", Constants.DEVICE_TOKEN);
        edit.putString("password", mEtPasswordLogin.getText().toString());
        edit.putString("idFacebook", null);
        edit.putBoolean("firstLogin", true);
        edit.putString("idLinkedIn", null);
        edit.apply();
    }

    public void saveDataLoginFacebook() {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString("mobileEmail", mFbData.getEmail());
        edit.putString("driverToken", Constants.DEVICE_TOKEN);
        edit.putString("password", "");
        edit.putString("idFacebook", mFbData.getId());
        edit.putBoolean("firstLogin", true);
        edit.putString("idLinkedIn", null);
        edit.putString("firstName", mFbData.getFirstName());
        edit.putString("lastName", mFbData.getLastName());
        edit.apply();
    }

    public void saveDataLoginLinkedin() {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString("mobileEmail", mLiData.getEmail());
        edit.putString("driverToken", Constants.DEVICE_TOKEN);
        edit.putString("password", "");
        edit.putString("idFacebook", null);
        edit.putBoolean("firstLogin", true);
        edit.putString("idLinkedIn", mLiData.getId());
        edit.putString("firstName", mLiData.getFirstName());
        edit.putString("lastName", mLiData.getLastName());
        edit.apply();
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    public void getCountryZipCode() {
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
    }

    @OnClick(R.id.rlVerify)
    public void onVerifyClicked() {

        hideKeyboard();

        String emailOrPhone = mEdtVerifyEmailOrPhone.getText().toString().trim();
        String code = mEdtVerifyEnterCode.getText().toString().trim();
        if (emailOrPhone.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "register-login");
            mEdtVerifyEmailOrPhone.requestFocus();
            return;
        }
        if (code.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "register-login");
            mEdtVerifyEnterCode.requestFocus();
            return;
        }

        if (!ValidateData.isPhone(emailOrPhone) && !ValidateData.isEmailValid(emailOrPhone)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_email_phone_not_correct, "register-login");
            mEdtVerifyEmailOrPhone.requestFocus();
            return;
        }

        if (!ValidateData.isCode(code)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_code_not_correct, "register-login");
            mEdtVerifyEnterCode.requestFocus();
            return;
        }

        if (!isNetworkConnected()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.no_network, "register-login");
            return;
        }

        mRlVerify.setClickable(false);
        showLoading();
        mVerifyAccountPresenter.verifyAccount(userId, emailOrPhone, code);
    }

    @OnClick(R.id.rlResendCode)
    public void onResendCodeClicked() {
        hideKeyboard();
        String emailOrPhone = mEdtVerifyEmailOrPhone.getText().toString().trim();
        if (emailOrPhone.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "register-login");
            mEdtVerifyEmailOrPhone.requestFocus();
            return;
        }
        if (!ValidateData.isPhone(emailOrPhone) && !ValidateData.isEmailValid(emailOrPhone)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_email_phone_not_correct, "register-login");
            mEdtVerifyEmailOrPhone.requestFocus();
            return;
        }
        if (!isNetworkConnected()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.no_network, "register-login");
            return;
        }
        mEdtVerifyEnterCode.setText(null);
        mRlResendCode.setClickable(false);
        mVerifyAccountPresenter.resendCode(emailOrPhone);
        showLoading();
    }

    @Override
    public void verifySuccess(User data) {
        hideLoading();
        hideKeyboard();
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, "Verify successfully", "register-login");
//        mLayoutVerify.setVisibility(View.GONE);
//        AnimUtils.slideRightToLeft(mLayoutLoginRegister, mWidthPage, 0, true);
//        mFlipView.flipTheView(false);
//        mRlVerify.setClickable(true);
//        if(mMailOrPhone != null){
//            mEtEmailPhoneLogin.setText(mMailOrPhone);
//        }
        setDataUser(data, "Normal", true);
        if ("no".equals(ConsumerUser.getInstance().getData().getHasAgent()) && agentInfo != null) {
            scanOrCodePresenter.setAgent(ConsumerUser.getInstance().getData().getId(), agentInfo);
        }
        Intent intent = new Intent(this, MainActivityConsumer.class);
        startActivity(intent);
        mBtLogin.setClickable(true);
        finish();
        mEtPasswordLogin.setText(null);
    }

    @Override
    public void verifyFail(String message) {
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "register-login");
        hideLoading();
        mRlVerify.setClickable(true);
    }

    @Override
    public void serverError(@StringRes int message) {
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "register-login");
        hideLoading();
        mRlVerify.setClickable(true);
        mRlResendCode.setClickable(true);
        mRlForgotPassword.setClickable(true);
        mEtPasswordLogin.setText(null);
        mBtLogin.setClickable(true);
        mLayoutRegister.setClickable(true);
    }

    @Override
    public void accountActivated(@StringRes int message, String emailOrPhone) {
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "register-login");
        hideLoading();
        mLayoutVerify.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutLoginRegister, mWidthPage, 0, true);
        mFlipView.flipTheView(false);
        mRlVerify.setClickable(true);
        mRlResendCode.setClickable(true);
        mEtEmailPhoneLogin.setText(emailOrPhone);
    }

    @Override
    public void resendCodeSuccess(String message) {
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, message, "register-login");
        hideLoading();
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvResendCode.setText("(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                mTvResendCode.setText(getString(R.string.resend_code));
                mRlResendCode.setClickable(true);
            }
        }.start();
    }

    @Override
    public void resendCodeFail(String message) {
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "register-login");
        hideLoading();
        mRlResendCode.setClickable(true);
    }

    @Override
    public void registerSuccess(@StringRes int message, ResponseRegister responseRegister) {
        hideLoading();

        showPopupSuccess("Registered successfully");

        userId = responseRegister.getData().getId();
        mLayoutRegister.setClickable(true);
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
                mLayoutLoginRegister.setVisibility(View.GONE);
                AnimUtils.slideRightToLeft(mLayoutVerify, mWidthPage, 0, true);
                if (mMailOrPhone != null) {
                    mEdtVerifyEmailOrPhone.setText(mMailOrPhone);
                }
                if (ValidateData.isPhone(mMailOrPhone)) {
                    if (ActivityCompat.checkSelfPermission(ConsumerRegisterLoginActivity.this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                        HomeCaravanApplication.askPermission(ConsumerRegisterLoginActivity.this, ConsumerRegisterLoginActivity.this, android.Manifest.permission.READ_SMS, READ_SMS_PERMISSION);
                    }
                    if (ActivityCompat.checkSelfPermission(ConsumerRegisterLoginActivity.this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                        HomeCaravanApplication.askPermission(ConsumerRegisterLoginActivity.this, ConsumerRegisterLoginActivity.this, android.Manifest.permission.RECEIVE_SMS, RECEIVER_SMS_PERMISSION);
                    }
                    messageListener();
                }
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void messageListener() {
        SMSReceiver.setListener(new SMSListener() {
            @Override
            public void messageReceived(String code) {
                Log.e("DaoDiDem", "SMSReceiver: " + code);
                if (mLayoutVerify.getVisibility() == View.VISIBLE) {
                    mEdtVerifyEnterCode.setText(code);
                }
            }
        });
    }

    @Override
    public void registerFail(String message) {
        mLayoutRegister.setClickable(true);
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "register-login");
    }

    @Override
    public void registerFacebookSuccess(ResponseUser responseUser) {
        hideLoading();
        setDataUser(responseUser.getUserData(), "Facebook", false);
        if ("no".equals(ConsumerUser.getInstance().getData().getHasAgent()) && agentInfo != null) {
            scanOrCodePresenter.setAgent(ConsumerUser.getInstance().getData().getId(), agentInfo);
        }
        Intent intent = new Intent(this, MainActivityConsumer.class);
        startActivity(intent);
        finish();
        mEtPasswordLogin.setText(null);
    }

    @Override
    public void registerFacebookFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "register-login");
    }

    @Override
    public void registerLinkedinSuccess(ResponseUser responseUser) {
        hideLoading();
        setDataUser(responseUser.getUserData(), "Linkedin", false);
        if ("no".equals(ConsumerUser.getInstance().getData().getHasAgent()) && agentInfo != null) {
            scanOrCodePresenter.setAgent(ConsumerUser.getInstance().getData().getId(), agentInfo);
        }
        Intent intent = new Intent(this, MainActivityConsumer.class);
        startActivity(intent);
        finish();
        mEtPasswordLogin.setText(null);
    }

    @Override
    public void registerLinkedinFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "register-login");
    }

    @Override
    public void openUnlockStepThree(User data) {
        agentInfo = data;
        mLayoutUnlockStepOne.setVisibility(View.GONE);
        mLayoutUnlockStepTwo.setVisibility(View.GONE);
        mLayoutUnlockStepThree.setVisibility(View.VISIBLE);
        mLayoutUnlockAgent.setVisibility(View.VISIBLE);
        mLayoutScanOrCode.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutUnlockAgent, mWidthPage, 0, true);
        Glide.with(this).load(data.getAvatar()).asBitmap().fitCenter().dontAnimate().into(mIvAgent);
        mTvName.setText(data.getFullName());
        mTvAddress.setText(data.getEmail());
        mTvZip.setText(data.getPhone());
        AnimUtils.animUnlockAgent(mIvAgent, mIvBgAgent, mTvName, mTvAddress, mTvZip);
    }

    @Override
    public void sendCodeToEnterCodeFragment(String code) {
        mViewPager.setCurrentItem(1, true);
        mFragmentEnterCode.setCodeFromScanFragment(code);
    }

    @Override
    public void switchFragment(int position) {
        mViewPager.setCurrentItem(position, true);
    }

    @OnClick(R.id.tvForgotPassword)
    public void openForgotPassword() {
        hideKeyboard();
        mLayoutLoginRegister.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutForgotPassword, mWidthPage, 0, true);
    }

    @OnClick(R.id.rlForgotPassword)
    void onForgotPasswordClicked() {
        String emailOrPhone = mEdtForgotPasswordEmailOrPhone.getText().toString().trim();
        hideKeyboard();
        if (emailOrPhone.isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_empty, "register-login");
            mEdtForgotPasswordEmailOrPhone.requestFocus();
            return;
        }
        if (!ValidateData.isPhone(emailOrPhone) && !ValidateData.isEmailValid(emailOrPhone)) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_email_phone_not_correct, "register-login");
            mEdtForgotPasswordEmailOrPhone.requestFocus();
            return;
        }
        if (!isNetworkConnected()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.no_network, "register-login");
            return;
        }

        mRlForgotPassword.setClickable(false);
        showLoading();
        ForgotPasswordPresenter forgotPasswordPresenter = new ForgotPasswordPresenter(this);
        forgotPasswordPresenter.forgotPassword(emailOrPhone);
    }

    @Override
    public void forgotPasswordSuccess(String message, String emailOrPhone) {
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, message, "register-login");
        hideLoading();
        mRlForgotPassword.setClickable(true);
        mLayoutForgotPassword.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutLoginRegister, mWidthPage, 0, true);
        mEtEmailPhoneLogin.setText(emailOrPhone);
    }

    @Override
    public void forgotPasswordFail(String message) {
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "register-login");
        hideLoading();
        mRlForgotPassword.setClickable(true);
    }

    @Override
    public void searchContactSuccess(ArrayList<ContactData> data) {
        if(data.size() != 0){
            mArrContactData.clear();
            mArrContactData.addAll(data);
            mSearchAgentAdapter.notifyDataSetChanged();
            mLayoutSearchEmpty.setVisibility(View.GONE);
            mRvSearchAgent.setVisibility(View.VISIBLE);
        }else{
            mLayoutSearchEmpty.setVisibility(View.VISIBLE);
            mRvSearchAgent.setVisibility(View.GONE);
        }
    }

    @Override
    public void searchContactFail(@StringRes int message) {
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "searchContactFail");
        mLayoutSearchEmpty.setVisibility(View.VISIBLE);
        mRvSearchAgent.setVisibility(View.GONE);
    }

    @Override
    public void searchContactFail(String message) {
//        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "searchContactFail");
        mLayoutSearchEmpty.setVisibility(View.VISIBLE);
        mRvSearchAgent.setVisibility(View.GONE);
    }

    @Override
    public void onSetAgent(ContactData data) {
        agentInfo = new User();
        agentInfo.getAgent().setId(data.getUser());
        agentInfo.getAgent().setFullName(data.getName());
        agentInfo.getAgent().setAvatar(data.getAvatar());

        mLayoutUnlockStepSearch.setVisibility(View.GONE);
        AnimUtils.slideRightToLeft(mLayoutUnlockStepThree, mWidthPage, 0, true);
        Glide.with(this).load(data.getAvatar()).asBitmap().fitCenter().dontAnimate().into(mIvAgent);
        mTvName.setText(data.getName());
        mTvAddress.setText(data.getEmail());
        mTvZip.setText(data.getPhone());
        AnimUtils.animUnlockAgent(mIvAgent, mIvBgAgent, mTvName, mTvAddress, mTvZip);
    }
}
