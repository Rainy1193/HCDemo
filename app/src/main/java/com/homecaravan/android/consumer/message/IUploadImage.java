package com.homecaravan.android.consumer.message;

import com.homecaravan.android.consumer.model.message.UploadObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by Anh Dao on 9/20/2017.
 */

public interface IUploadImage {
    @Multipart
    @POST("/uploads")
    Call<ArrayList<UploadObject>> uploadFile(@PartMap Map<String, RequestBody> bodyMap);
}
