package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.EditText;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumermvp.contactmvp.InviteContactPresenter;
import com.homecaravan.android.consumer.consumermvp.contactmvp.InviteContactView;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.handling.ValidateData;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;


public class SendEmailContactActivity extends BaseActivity implements InviteContactView {
    private InviteContactPresenter mInviteContactPresenter;
    @Bind(R.id.etToEmail)
    EditText mEtTo;
    @Bind(R.id.etMessage)
    EditText mEtMessage;
    @Bind(R.id.tvLink)
    TextView mTvLink;

    @OnClick(R.id.ivSendEmail)
    public void sendEmail() {
        ArrayList<String> arrEmail = new ArrayList<>();
        String totalEmail = mEtTo.getText().toString();
        String[] arr = totalEmail.split(",");
        String email = "";
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(arr));
        for (int i = 0; i < arrayList.size(); i++) {
            if (ValidateData.isEmailValid(arrayList.get(i))) {
                arrEmail.add(arrayList.get(i));
                email = email + arrayList.get(i);
            }
        }
        email = email.substring(0, email.length() - 1);
        if (!arrEmail.isEmpty()) {
            mInviteContactPresenter.inviteContact(email, true, mTvLink.getText().toString());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInviteContactPresenter = new InviteContactPresenter(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_send_email_contact;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

    @Override
    public void inviteSuccess() {
        hideLoading();
        showSnackBar(findViewById(R.id.layoutMain), TypeDialog.SUCCESS, "Send invite success", "createContact");
    }

    @Override
    public void inviteFail(String message) {
        hideLoading();
        showSnackBar(findViewById(R.id.layoutMain), TypeDialog.WARNING, message, "createContact");
    }

    @Override
    public void inviteFail(@StringRes int message) {
        hideLoading();
        showSnackBar(findViewById(R.id.layoutMain), TypeDialog.ERROR, message, "createContact");
    }

}
