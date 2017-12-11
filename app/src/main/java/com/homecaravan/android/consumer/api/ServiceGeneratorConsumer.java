package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.consumerbase.ConsumerUser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGeneratorConsumer {
    private static Retrofit.Builder builder;

    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        setApiBaseUrl(com.homecaravan.android.api.Constants.getInstance().getURL_BASE_CONSUMER() +
                com.homecaravan.android.api.Constants.getInstance().getAPI_VERSION());
        try {
            addInterceptor(httpClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addConverterFactory(GsonConverterFactory.create());
        addLogInterceptor(httpClient);
        return builder.client(httpClient.build()).build().create(serviceClass);
    }


    private static void addInterceptor(OkHttpClient.Builder httpClient) throws IOException {
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.header("Authorization", com.homecaravan.android.api.Constants.AUTHORIZATION);
                requestBuilder.header("Apikey", com.homecaravan.android.api.Constants.API_KEY);

                if (ConsumerUser.getInstance().getData().getToken() != null) {
                    requestBuilder.header("Token", ConsumerUser.getInstance().getData().getToken());
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);

            }
        });

    }

    private static void addConverterFactory(Converter.Factory factory) {
        builder.addConverterFactory(factory);
    }

    private static void addLogInterceptor(OkHttpClient.Builder httpClient) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
    }

    private static void setApiBaseUrl(String baseUrl) {
        builder = new Retrofit.Builder()
                .baseUrl(baseUrl);
    }
}
