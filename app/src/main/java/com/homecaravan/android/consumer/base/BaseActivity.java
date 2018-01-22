package com.homecaravan.android.consumer.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IDialogListener;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.widget.ConsumerDialog;
import com.homecaravan.android.mydialog.DialogLoading;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements BaseView, IDialogListener {

    private DialogLoading mDialogLoading;
    private ConsumerDialog mConsumerDialog;
    private TSnackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        mDialogLoading = new DialogLoading();
        mConsumerDialog = new ConsumerDialog(this);
        mConsumerDialog.setListener(this);
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutResourceId();


    @Override
    public void showLoading() {
        if (mDialogLoading != null) {
            mDialogLoading.show(getSupportFragmentManager(), "Loading");
        }
    }

    @Override
    public void hideLoading() {
        if (mDialogLoading != null && mDialogLoading.isShow()) {
            mDialogLoading.dismiss();
        }
    }

    @Override
    public void onError(@StringRes int resId) {

    }

    @Override
    public void onError(String message) {

    }


    @Override
    public void showDialog(TypeDialog type, String message, String action) {

        mConsumerDialog.setType(type);
        mConsumerDialog.setMessage(message);
        mConsumerDialog.show();
    }

    @Override
    public void showDialog(TypeDialog type, @StringRes int resId, String action) {
        mConsumerDialog.setType(type);
        mConsumerDialog.setMessage(resId);
        mConsumerDialog.show();
    }

    @Override
    public void showSnackBar(View view, TypeDialog type, String message, final String action) {
        getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorSnackBar));
        snackbar = TSnackbar.make(view, message, 3000);
        snackbar.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }

        });
        snackbar.setCallback(new TSnackbar.Callback() {
            @Override
            public void onDismissed(TSnackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (!action.equalsIgnoreCase("register-login")) {
                    getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorDashboardStatusBar));
                } else {
                    getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorStatusBarLogin));
                }
            }

            @Override
            public void onShown(TSnackbar snackbar) {
                super.onShown(snackbar);

            }
        });
        View snackBarView = snackbar.getView();
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setMaxLines(4);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        snackbar.show();
    }

    @Override
    public void showSnackBar(View view, TypeDialog type, @StringRes int message, final String action) {
        getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorSnackBar));
        snackbar = TSnackbar.make(view, message, 3000);
        snackbar.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.setCallback(new TSnackbar.Callback() {
            @Override
            public void onDismissed(TSnackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (!action.equalsIgnoreCase("register-login")) {
                    getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorDashboardStatusBar));
                } else {
                    getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.colorStatusBarLogin));
                }
            }

            @Override
            public void onShown(TSnackbar snackbar) {
                super.onShown(snackbar);

            }
        });
        View snackBarView = snackbar.getView();
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setMaxLines(4);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        snackbar.show();

    }

    @Override
    public void dismissSnackBar() {
        if (snackbar != null) {
            if (snackbar.isShown()) {
                snackbar.dismiss();
            }
        }
    }

    @Override
    public void showMessage(String message) {
        mConsumerDialog.setMessage(message);
    }

    @Override
    public void showMessage(@StringRes int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return HomeCaravanApplication.isNetAvailable(this);
    }

    @Override
    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            HomeCaravanApplication.getInstance().getInput().hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void yesAction(String action) {

    }

    @Override
    public void noAction(String action) {

    }

    @Override
    public void otherAction(String action) {

    }

    public void openNewActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }
}
