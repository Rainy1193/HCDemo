package com.homecaravan.android.ui;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by RAINY on 3/30/2016.
 */
public class FontManager {
    public static final String PATH = "fonts/fontawesome.ttf";

    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), PATH);
    }
}
