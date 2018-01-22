package com.homecaravan.android.consumer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.listener.IScanOrCode;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.User;
import com.homecaravan.android.consumer.unlockagent.IUnlockApi;
import com.homecaravan.android.consumer.unlockagent.ScanOrCodePresenter;
import com.homecaravan.android.consumer.utils.Utils;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentEnterCode extends BaseFragment implements IUnlockApi {

    private final String TAG = "DaoDiDem";
    private IScanOrCode mIScanOrCode;
    private ScanOrCodePresenter scanOrCodePresenter;

    @Bind(R.id.layoutUnlockStepTwo)
    ScrollView mLayoutUnlockStepTwo;
    @Bind(R.id.ivGlass)
    ImageView mIvGlass;
    @Bind(R.id.tvLinkHide)
    TextView mTvLinkHide;
    @Bind(R.id.tvLink)
    TextView mTvLink;
    @Bind(R.id.edtCode1)
    EditText mCode1;
    @Bind(R.id.edtCode2)
    EditText mCode2;
    @Bind(R.id.edtCode3)
    EditText mCode3;
    @Bind(R.id.edtCode4)
    EditText mCode4;
    @Bind(R.id.edtCode5)
    EditText mCode5;
    @Bind(R.id.edtCode6)
    EditText mCode6;

    @OnClick(R.id.btnSubmit)
    public void onSubmit() {
        StringBuilder codeSB = new StringBuilder();
        codeSB.append(mCode1.getText().toString().trim());
        codeSB.append(mCode2.getText().toString().trim());
        codeSB.append(mCode3.getText().toString().trim());
        codeSB.append(mCode4.getText().toString().trim());
        codeSB.append(mCode5.getText().toString().trim());
        codeSB.append(mCode6.getText().toString().trim());

        if (codeSB.length() == 6) {
            scanOrCodePresenter.findAgent(codeSB.toString());
        }
        Log.e(TAG, "onSubmit: code: " + codeSB);
    }

    public void setIScanOrCodeListener(IScanOrCode mIScanOrCode) {
        this.mIScanOrCode = mIScanOrCode;
    }

    public void setCodeFromScanFragment(String code) {
        showCodeAgent(code);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scanOrCodePresenter = new ScanOrCodePresenter(this);
        mCode1.requestFocus();
        mTvLink.setText(Utils.getLinkAgentUnlock("http://www.hc.com/agent/FR234L", getActivity()));
        addTextChangedListener();
    }

    private void addTextChangedListener() {
        mCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCode1.length() == 1) {
                    mCode1.clearFocus();
                    mCode2.requestFocus();
                    mCode2.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCode2.length() == 1) {
                    mCode2.clearFocus();
                    mCode3.requestFocus();
                    mCode3.setCursorVisible(true);
                } else if (mCode2.length() == 0) {
                    mCode2.clearFocus();
                    mCode1.requestFocus();
                    mCode1.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCode3.length() == 1) {
                    mCode3.clearFocus();
                    mCode4.requestFocus();
                    mCode4.setCursorVisible(true);
                } else if (mCode3.length() == 0) {
                    mCode3.clearFocus();
                    mCode2.requestFocus();
                    mCode2.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCode4.length() == 1) {
                    mCode4.clearFocus();
                    mCode5.requestFocus();
                    mCode5.setCursorVisible(true);
                } else if (mCode4.length() == 0) {
                    mCode4.clearFocus();
                    mCode3.requestFocus();
                    mCode3.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCode5.length() == 1) {
                    mCode5.clearFocus();
                    mCode6.requestFocus();
                    mCode6.setCursorVisible(true);
                } else if (mCode5.length() == 0) {
                    mCode5.clearFocus();
                    mCode4.requestFocus();
                    mCode4.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCode6.length() == 0) {
                    mCode6.clearFocus();
                    mCode5.requestFocus();
                    mCode5.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_enter_code;
    }

    public void showCodeAgent(final String code) {
        if (code.length() == 6) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mIvGlass.getLayoutParams();
                    lp.setMargins(mTvLinkHide.getWidth(), 0, 0, 0);
                    mIvGlass.setLayoutParams(lp);
                    mCode1.setText(String.valueOf(code.charAt(0)));
                    mCode2.setText(String.valueOf(code.charAt(1)));
                    mCode3.setText(String.valueOf(code.charAt(2)));
                    mCode4.setText(String.valueOf(code.charAt(3)));
                    mCode5.setText(String.valueOf(code.charAt(4)));
                    mCode6.setText(String.valueOf(code.charAt(5)));
                }
            });
            mTvLink.setText(Utils.getLinkAgentUnlock("http://www.hc.com/agent/" + code, getActivity()));
        } else {
            mCode1.setText(null);
            mCode2.setText(null);
            mCode3.setText(null);
            mCode4.setText(null);
            mCode5.setText(null);
            mCode6.setText(null);
        }
    }

    @Override
    public void findAgentSuccess(User data) {
        Log.e(TAG, "findAgentSuccess: id: " + data.getId() + " name: " + data.getFullName());
        ConsumerUser.getInstance().getData().setAgentId(data.getId());
        mIScanOrCode.openUnlockStepThree(data);
    }

    @Override
    public void findAgentFail(String message) {
        showSnackBar(mLayoutUnlockStepTwo, TypeDialog.WARNING, message, "findAgentFail");
    }

    @Override
    public void serverError(@StringRes int message) {
        showSnackBar(mLayoutUnlockStepTwo, TypeDialog.ERROR, message, "serverError");
    }
}
