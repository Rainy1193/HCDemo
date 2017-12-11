package com.homecaravan.android.consumer.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IDialogListener;
import com.homecaravan.android.consumer.model.TypeDialog;


public class ConsumerDialog {
    private Context mContext;
    private AlertDialog mAlertDialog;
    private Builder mBuilder;
    private boolean mHasShow = false;
    private boolean mCancel = true;
    private int mTitleResId;
    private CharSequence mTitle;
    private int mMessageResId;
    private CharSequence mMessage;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private IDialogListener mListener;
    private TypeDialog mType;
    private String mAction = "";


    /**
     * Instantiates a new Dh alert view.
     *
     * @param context the context
     */
    public ConsumerDialog(Context context) {
        this.mContext = context;
    }


    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    /**
     * Show.
     */
    public void show() {
        if (!mHasShow) {
            mBuilder = new Builder();
        } else {
            mAlertDialog.show();
        }
        mHasShow = true;
    }

    /**
     * Dismiss.
     */
    public void dismiss() {
        mAlertDialog.dismiss();
    }

    /**
     * Sets title.
     *
     * @param resId the res id
     * @return the title
     */
    public ConsumerDialog setTitle(int resId) {
        mTitleResId = resId;
        if (mBuilder != null) {
            mBuilder.setTitle(resId);
        }
        return this;
    }


    /**
     * Sets title.
     *
     * @param title the title
     * @return the title
     */
    public ConsumerDialog setTitle(CharSequence title) {
        mTitle = title;
        if (mBuilder != null) {
            mBuilder.setTitle(title);
        }
        return this;
    }

    public ConsumerDialog setType(TypeDialog type) {
        mType = type;
        if (mBuilder != null) {
            mBuilder.setTypeDialog(type);
        }
        return this;
    }


    /**
     * Sets message.
     *
     * @param resId the res id
     * @return the message
     */
    public ConsumerDialog setMessage(int resId) {
        mMessageResId = resId;
        if (mBuilder != null) {
            mBuilder.setMessage(resId);
        }
        return this;
    }


    /**
     * Sets message.
     *
     * @param message the message
     * @return the message
     */
    public ConsumerDialog setMessage(CharSequence message) {
        mMessage = message;
        if (mBuilder != null) {
            mBuilder.setMessage(message);
        }
        return this;
    }


    /**
     * Sets whether this dialog is canceled when touched outside the window's
     * bounds OR pressed the back key. If setting to true, the dialog is
     * set to be cancelable if not
     * already set.
     *
     * @param cancel Whether the dialog should be canceled when touched outside the window OR pressed the back key.
     * @return the canceled on touch outside
     */
    public ConsumerDialog setCanceledOnTouchOutside(boolean cancel) {
        this.mCancel = cancel;
        if (mBuilder != null) {
            mBuilder.setCanceledOnTouchOutside(mCancel);
        }
        return this;
    }

    /**
     * Sets on dismiss listener.
     *
     * @param onDismissListener the on dismiss listener
     * @return the on dismiss listener
     */
    public ConsumerDialog setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        return this;
    }

    /**
     * Sets   listener.
     *
     * @param listener the listener
     * @return the  listener
     */

    public ConsumerDialog setListener(IDialogListener listener) {
        this.mListener = listener;
        return this;
    }

    private class Builder {
        private TextView mTitleView;
        private TextView mMessageView;
        private LinearLayout mBackground;
        private Window mAlertDialogWindow;
        private RelativeLayout mLayoutMainDialog;
        private ImageView mImageView;
        private LinearLayout mLayoutYesNo;
        private TextView mTvNo;
        private TextView mTvYes;
        private TextView mTvAction;

        private Builder() {

            mAlertDialog = new AlertDialog.Builder(mContext).create();
            mAlertDialog.show();
            mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE);
            mAlertDialogWindow = mAlertDialog.getWindow();
            mAlertDialogWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_consumer, null);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);

            mAlertDialogWindow.setBackgroundDrawableResource(R.drawable.material_dialog_window);
            mAlertDialogWindow.setContentView(contentView);
            mBackground = (LinearLayout) mAlertDialogWindow.findViewById(R.id.materialBackground);
            mTitleView = (TextView) mAlertDialogWindow.findViewById(R.id.tvTitle);
            mMessageView = (TextView) mAlertDialogWindow.findViewById(R.id.tvMessage);
            mLayoutMainDialog = (RelativeLayout) mAlertDialogWindow.findViewById(R.id.layoutMainDialog);
            mImageView = (ImageView) mAlertDialogWindow.findViewById(R.id.iv);
            mLayoutYesNo = (LinearLayout) mAlertDialogWindow.findViewById(R.id.layoutYesNo);
            mTvNo = (TextView) mAlertDialogWindow.findViewById(R.id.tvNo);
            mTvYes = (TextView) mAlertDialogWindow.findViewById(R.id.tvYes);
            mTvAction = (TextView) mAlertDialogWindow.findViewById(R.id.tvAction);

            mTvNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.noAction(mAction);
                    }
                    dismiss();
                }
            });

            mTvYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.yesAction(mAction);
                    }
                    dismiss();
                }
            });

            mTvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.otherAction(mAction);
                    }
                    dismiss();
                }
            });
            mBackground.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dismiss();
                    return false;
                }
            });
            mLayoutMainDialog.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dismiss();
                    return false;
                }
            });

            mMessageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dismiss();
                    return false;
                }
            });
            if (mType != null) {
                setTypeDialog(mType);
            }
            if (mTitleResId != 0) {
                setTitle(mTitleResId);
            }
            if (mTitle != null) {
                setTitle(mTitle);
            }
            if (mMessageResId != 0) {
                setMessage(mMessageResId);
            }
            if (mMessage != null) {
                setMessage(mMessage);
            }

            mAlertDialog.setCanceledOnTouchOutside(mCancel);
            mAlertDialog.setCancelable(mCancel);
            if (mOnDismissListener != null) {
                mAlertDialog.setOnDismissListener(mOnDismissListener);
            } else {
            }
        }

        public void setTypeDialog(TypeDialog typeDialog) {
            if (typeDialog == TypeDialog.CONFIRM) {
                mImageView.setImageResource(R.drawable.ic_dialog_confirm);
                mLayoutYesNo.setVisibility(View.VISIBLE);
                mTvAction.setVisibility(View.GONE);
                setTitle(R.string.dialog_confirm);
            } else if (typeDialog == TypeDialog.MESSAGES) {
                mImageView.setImageResource(R.drawable.ic_dialog_messages);
                mLayoutYesNo.setVisibility(View.VISIBLE);
                mTvAction.setVisibility(View.GONE);
                setTitle(R.string.dialog_messages);
            } else if (typeDialog == TypeDialog.WARNING) {
                mImageView.setImageResource(R.drawable.ic_dialog_warning);
                mLayoutYesNo.setVisibility(View.GONE);
                mTvAction.setVisibility(View.VISIBLE);
                mTvAction.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_button_dialog_warning));
                setTitle(R.string.dialog_warning);
            } else if (typeDialog == TypeDialog.ERROR) {
                mImageView.setImageResource(R.drawable.ic_dialog_error);
                mLayoutYesNo.setVisibility(View.GONE);
                mTvAction.setVisibility(View.VISIBLE);
                mTvAction.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_button_dialog_error));
                setTitle(R.string.dialog_error);
            } else {
                mImageView.setImageResource(R.drawable.ic_dialog_success);
                mLayoutYesNo.setVisibility(View.GONE);
                mTvAction.setVisibility(View.VISIBLE);
                mTvAction.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_button_dialog_success));
                setTitle(R.string.dialog_success);
            }
        }

        /**
         * Sets title.
         *
         * @param resId the res id
         */
        public void setTitle(int resId) {
            mTitleView.setText(resId);
        }


        /**
         * Sets title.
         *
         * @param title the title
         */
        public void setTitle(CharSequence title) {
            mTitleView.setText(title);
        }


        /**
         * Sets message.
         *
         * @param resId the res id
         */
        public void setMessage(int resId) {
            if (mMessageView != null) {
                mMessageView.setText(resId);
            }
        }


        /**
         * Sets message.
         *
         * @param message the message
         */
        public void setMessage(CharSequence message) {
            if (mMessageView != null) {
                mMessageView.setText(message);
            }
        }


        /**
         * Sets canceled on touch outside.
         *
         * @param canceledOnTouchOutside the canceled on touch outside
         */
        public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            mAlertDialog.setCancelable(canceledOnTouchOutside);
        }
    }

    public boolean isHasShow() {
        return mHasShow;
    }
}
