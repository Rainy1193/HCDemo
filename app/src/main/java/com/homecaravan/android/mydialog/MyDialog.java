package com.homecaravan.android.mydialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.homecaravan.android.R;

/**
 * Created by RAINY on 4/5/2016.
 */
public class MyDialog extends DialogFragment {
    private static int message_type = 0;
    private static TextView textViewMessage;
    private static String message;
    private static String subMessage;
    private static TextView title;
    private View view;
    private AlertDialog alertDialog;
    private boolean closeActivity = false;
    private click c;

    public void setC(click c) {
        this.c = c;
        this.closeActivity = true;
    }

    public interface click {
        void ckickOk();
    }

    public static MyDialog getInstance(String mess) {
        message = mess;
        return new MyDialog();
    }

    public static MyDialog getInstance(String mess, int type) {
        message = mess;
        if (mess.equalsIgnoreCase("Not connected network")) {
            message = "No active network connection available. Please try again later";
        }
        message_type = type;
        subMessage = null;
        return new MyDialog();
    }

    public static MyDialog getInstance(String mess, int type, String subMess) {
        message = mess;
        message_type = type;
        subMessage = subMess;
        return new MyDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        switch (message_type) {
            case 1:
                view = layoutInflater.inflate(R.layout.my_dialog_info, null);
                break;
            case 2:
                view = layoutInflater.inflate(R.layout.my_dialog_warning, null);
                break;
            case 3:
                view = layoutInflater.inflate(R.layout.my_dialog_success, null);
                break;
            case 4:
                view = layoutInflater.inflate(R.layout.my_dialog_error, null);
                break;
            default:
                view = layoutInflater.inflate(R.layout.my_dialog, null);
                break;
        }
        textViewMessage = (TextView) view.findViewById(R.id.message);
        title = (TextView) view.findViewById(R.id.tvTitle);
        if (message != null) {
            textViewMessage.setText(Html.fromHtml(message));
        }
        if (subMessage != null) {
            title.setText(Html.fromHtml(subMessage));
        }
        view.findViewById(R.id.tvOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        alertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        return alertDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (closeActivity) {
            c.ckickOk();
        }
    }
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {

        }
    }
}