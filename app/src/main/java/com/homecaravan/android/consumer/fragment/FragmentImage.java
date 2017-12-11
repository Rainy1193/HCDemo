package com.homecaravan.android.consumer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;


public class FragmentImage extends Fragment {
    private RoundedImageView mImageView;
    private String mImageUrl;

    public static FragmentImage newInstance(String imageUrl) {
        final FragmentImage f = new FragmentImage();
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);
        return f;
    }

    public FragmentImage() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail_consumer, null, false);
        mImageView = (RoundedImageView) view.findViewById(R.id.iv);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.getImageLoader().loadImage(mImageUrl,mImageView,null, Utils.getPlaceHolderListing(getActivity()),true);
        //Glide.with(getContext()).load(mImageUrl).asBitmap().centerCrop().into(mImageView);
    }
}
