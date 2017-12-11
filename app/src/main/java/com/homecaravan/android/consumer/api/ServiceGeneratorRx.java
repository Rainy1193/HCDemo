package com.homecaravan.android.consumer.api;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGeneratorRx {
    private static Retrofit.Builder mBuilder = new Retrofit.Builder()
            .baseUrl(Constants.URL_GOOGLE_PLACE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());


    /**
     * Create service s.
     *
     * @param <S>          the type parameter
     * @param classService the class service
     * @return the s
     */
    public static <S> S createService(Class<S> classService) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder newRequest = chain.request().newBuilder();
                return chain.proceed(newRequest.build());
            }
        });
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        mBuilder.addConverterFactory(GsonConverterFactory.create());
        mBuilder.client(builder.build());
        return mBuilder.build().create(classService);
    }
}
