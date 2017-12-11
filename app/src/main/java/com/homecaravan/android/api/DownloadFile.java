package com.homecaravan.android.api;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by RAINY on 4/5/2016.
 */
public class DownloadFile {
    private String url;

    public DownloadFile(String url) {
        this.url = url;
    }

    public InputStream downloadFile() {
        InputStream data = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(this.url).build();
            okhttp3.Response response = client.newCall(request).execute();
            data = response.body().byteStream();
            return data;
        } catch (Exception e) {
            return data;
        }
    }
}