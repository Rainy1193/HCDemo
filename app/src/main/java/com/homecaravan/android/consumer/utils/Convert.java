package com.homecaravan.android.consumer.utils;

import android.content.Context;
import android.util.DisplayMetrics;


/**
 * The type Convert.
 * @author Dau Hung
 */
public class Convert {

    /**
     * Dp to px int.
     *
     * @param dp      the dp
     * @param context the context
     * @return the int
     */
    public static int dpToPx(int dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    /**
     * Px to dp int.
     *
     * @param px      the px
     * @param context the context
     * @return the int
     */
    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


}
