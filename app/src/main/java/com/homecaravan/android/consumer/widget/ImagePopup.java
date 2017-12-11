package com.homecaravan.android.consumer.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.homecaravan.android.R;
import com.homecaravan.android.R.id;

/**
 * Created by Anh Dao on 9/22/2017.
 */

public class ImagePopup extends android.support.v7.widget.AppCompatImageView {
    private Context context;
    private PopupWindow popupWindow;
    private int windowHeight = 0;
    private int windowWidth = 0;
    private boolean imageOnClickClose;
    private boolean hideCloseIcon;
    private int backgroundColor = Color.parseColor("#131414");

    public ImagePopup(Context context) {
        super(context);
        this.context = context;
    }

    public ImagePopup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getWindowHeight() {
        return this.windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return this.windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setImageOnClickClose(boolean imageOnClickClose) {
        this.imageOnClickClose = imageOnClickClose;
    }

    public boolean isImageOnClickClose() {
        return this.imageOnClickClose;
    }

    public boolean isHideCloseIcon() {
        return this.hideCloseIcon;
    }

    public void setHideCloseIcon(boolean hideCloseIcon) {
        this.hideCloseIcon = hideCloseIcon;
    }

    public void initiatePopup(Drawable drawable) {
        try {
            LayoutInflater e = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = e.inflate(R.layout.image_popup, (ViewGroup)this.findViewById(id.popup));
            layout.setBackgroundColor(this.getBackgroundColor());
            ImageView imageView = (ImageView)layout.findViewById(id.imageView);
            imageView.setImageDrawable(drawable);
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            this.popupWindow = new PopupWindow(layout, width, height, true);
            this.popupWindow.setAnimationStyle(R.style.AnimationPopup);
            this.popupWindow.showAtLocation(layout, 17, 0, 0);
            ImageView closeIcon = (ImageView)layout.findViewById(id.closeBtn);
            if(this.isHideCloseIcon()) {
                closeIcon.setVisibility(GONE);
            }

            closeIcon.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ImagePopup.this.popupWindow.dismiss();
                }
            });
            if(this.isImageOnClickClose()) {
                imageView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ImagePopup.this.popupWindow.dismiss();
                    }
                });
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }
}
