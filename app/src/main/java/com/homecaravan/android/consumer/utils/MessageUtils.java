package com.homecaravan.android.consumer.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Anh Dao on 12/28/2017.
 */

public class MessageUtils {
    public static void checkAvatar(String avatarUrl, TextView mTvAvatar, String name, ImageView avatar, Picasso picasso) {
        if (avatarUrl != null) {
            String[] s = avatarUrl.split("/");
            //http://app.homecaravan.net/themes/theme20/img/avatar_default.jpg
            if (s[s.length - 1].equals("avatar_default.jpg")) {
                mTvAvatar.setText(TextUtils.getFirstCharacter(name));
                mTvAvatar.setVisibility(View.VISIBLE);
            } else {
                picasso.load(avatarUrl).fit().centerCrop().placeholder(R.drawable.avatar_default).into(avatar);
                mTvAvatar.setVisibility(View.GONE);
            }
        }
    }
}
