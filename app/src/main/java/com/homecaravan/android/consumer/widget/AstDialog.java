package com.homecaravan.android.consumer.widget;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.homecaravan.android.R;

public class AstDialog extends DialogFragment {

    private AlertDialog mAlertDialog;
    private static String mType;
    private AstListener mListener;
    private TextView mTitle;
    private TextView mMessage;
    private TextView mRight;
    private TextView mLeft;

    public AstDialog setListener(AstListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public static AstDialog getInstance(String type) {
        mType = type;
        return new AstDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.dialog_ast, null, false);
        mTitle = (TextView) view.findViewById(R.id.tvTitle);
        mMessage = (TextView) view.findViewById(R.id.tvMessage);
        mRight = (TextView) view.findViewById(R.id.tvRight);
        mLeft = (TextView) view.findViewById(R.id.tvLeft);
        if (mType.equalsIgnoreCase("past")) {
            mTitle.setText("Date unavailable");
            mMessage.setText("The seller has made this time unavailable, but if you really need this time, you can ask if they will consider accommodating you.Request alternate day/time");
            mRight.setText("OK");
            mLeft.setText("Cancel");
        } else {
            mTitle.setText("Availability Manager Help");
            mMessage.setText(mType.substring(0, mType.length() - 3) + " is available for booking without negotiation, please click continue to take the fast track.");
            mRight.setText("Continue");
            mLeft.setText("No");
        }
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRightClick();
                dismiss();
            }
        });
        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onLeftClick();
                dismiss();
            }
        });
        mAlertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mAlertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        return mAlertDialog;
    }

    public interface AstListener {
        void onRightClick();

        void onLeftClick();
    }
}