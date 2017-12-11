package com.homecaravan.android.api;


import com.google.gson.GsonBuilder;
import com.homecaravan.android.models.UserLoginInfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RAINY on 4/25/2016.
 */
public class ServiceGeneratorString {
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Constants.getInstance().getURL_BASE());

    public static <S> S createService(Class<S> serviceClass) {

        builder.baseUrl(Constants.getInstance().getURL_BASE() + Constants.getInstance().getAPI_VERSION());
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.writeTimeout(10, TimeUnit.SECONDS);
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                if (UserLoginInfo.getInstance().getToken() != null) {
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", Constants.AUTHORIZATION)
                            .header("Content-Type", "multipart/form-data")
                            .header("Token", UserLoginInfo.getInstance().getToken())
                            .header("Version", Constants.API_VERSION_CODE)
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                } else {
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", Constants.AUTHORIZATION)
                            .header("Content-Type", "multipart/form-data")
                            .header("Apikey", Constants.API_KEY)
                            .header("Version", Constants.API_VERSION_CODE)
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }

            }
        });
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter((Type) String.class, (Object) new StringDesirializer());
        builder.addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()));
        return builder.client(httpClient.build()).build().create(serviceClass);
    }
}
