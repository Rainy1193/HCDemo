package com.homecaravan.android.consumer.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IDialogListener;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.widget.ConsumerDialog;
import com.homecaravan.android.mydialog.DialogLoading;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements BaseView, IDialogListener {
    private BaseActivity mActivity;
    private DialogLoading mDialogLoading;
    private ConsumerDialog mConsumerDialog;
    private TSnackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getLayoutResourceId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConsumerDialog = new ConsumerDialog(getActivity());
        mConsumerDialog.setListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        mDialogLoading = new DialogLoading();
        mDialogLoading.show(getChildFragmentManager(), "Loading");
    }

    @Override
    public void hideLoading() {
        if (mDialogLoading != null && mDialogLoading.isShow()) {
            mDialogLoading.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }


    @Override
    public void showDialog(TypeDialog type, String message, String action) {
        if (mActivity != null) {
            mActivity.showDialog(type, message, action);
        } else {
            mConsumerDialog.setType(type);
            mConsumerDialog.setMessage(message);
            mConsumerDialog.show();
        }
    }

    @Override
    public void showDialog(TypeDialog type, @StringRes int resId, String action) {
        if (mActivity != null) {
            mActivity.showDialog(type, resId, action);
        } else {
            mConsumerDialog.setType(type);
            mConsumerDialog.setMessage(resId);
            mConsumerDialog.show();
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        } else {
            mConsumerDialog.setMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        } else {
            mConsumerDialog.setMessage(resId);
        }
    }

    @Override
    public void showSnackBar(View view, TypeDialog type, String message, String action) {
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorSnackBar));
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
                if(getActivity() != null)
                    getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorDashboardStatusBar));
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
    public void showSnackBar(View view, TypeDialog type, @StringRes int message, String action) {
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorSnackBar));
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
                getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorDashboardStatusBar));
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
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        } else {
            return HomeCaravanApplication.isNetAvailable(getActivity());
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }


    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        } else {
            if (getActivity().getCurrentFocus() != null) {
                HomeCaravanApplication.getInstance().getInput().hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
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
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }
}
