package com.homecaravan.android.consumer.consumermvp.loginmvp;

import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;

import java.util.ArrayList;

public interface GetFeaturedView {
    void getFeaturedSuccess(ArrayList<ResponseFeatured.Featured> arrFeatured);

}
