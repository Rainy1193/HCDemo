package com.homecaravan.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.crashlytics.android.Crashlytics;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.activity.MainActivityConsumer;
import com.homecaravan.android.consumer.consumerdata.ConsumerDummyData;
import com.homecaravan.android.consumer.consumerdatabase.DatabaseDAO;
import com.homecaravan.android.consumer.service.ApplicationService;
import com.homecaravan.android.consumer.utils.ImageLoader;
import com.homecaravan.android.models.Account;
import com.homecaravan.android.models.Country;
import com.homecaravan.android.models.TimeBookAst;
import com.homecaravan.android.models.TimeResuggest;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static io.socket.client.IO.socket;

public class HomeCaravanApplication extends Application {
    private static HomeCaravanApplication mHomeCaravanApplication;
    private OkHttp3Downloader okHttp3Downloader;
    private Realm realm;
    private ImageLoader mImageLoader;

    public static String code;
    public static String location;
    public static String register_email = "";
    public static String register_id = "";
    public static String register_active_code = "";
    public static boolean is_back_from_listing_detail = false;
    public static ArrayList<TimeBookAst> arrSlotsAST = new ArrayList<>();
    public static ArrayList<TimeResuggest> arrSlotResuggest = new ArrayList<>();
    public OkHttpClient client;
    private InputMethodManager mInput;
    private DatabaseDAO databaseDAO;
    public static Socket mSocket;

    {
        try {
            mSocket = socket(Constants.getInstance().URL_BASE_CONSUMER_MESSAGE);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //Notification
    public static String mCaravanID = "";
    public static boolean mIsCaravanInAction = false;

    //Message module
    public static boolean mLoginSocketSuccess = false;
    public static boolean mReceiverMessageNotification = true;

    @Override
    public void onCreate() {
        super.onCreate();
//		Fabric.with(this, new Crashlytics());
        mHomeCaravanApplication = this;
        location = Locale.getDefault().getCountry();
        mInput = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        ConsumerDummyData consumerDummyData = new ConsumerDummyData(this);
        consumerDummyData.createDummyData();
        initRealm();
        databaseDAO = new DatabaseDAO(realm);
        databaseDAO.addSavedSearchDefault();
        MultiDex.install(this);
        mImageLoader = new ImageLoader(getApplicationContext());
        startService(new Intent(this, ApplicationService.class));
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public InputMethodManager getInput() {
        return mInput;
    }

    public static HomeCaravanApplication getInstance() {
        return mHomeCaravanApplication;
    }

    public Picasso buildPicasso() {
        Picasso.Builder picassoBuilder = new Picasso.Builder(this);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().cache(new okhttp3.Cache(getCacheDir(), 60 * 60 * 24 * 365));
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", Constants.AUTHORIZATION)
                        .addHeader("Apikey", Constants.API_KEY)
                        .build();
                return chain.proceed(newRequest);

            }
        });
        okHttp3Downloader = new OkHttp3Downloader(httpClient.build());
        picassoBuilder.downloader(okHttp3Downloader);
        return picassoBuilder.build();
    }

    public void setBgViewAllVersion(int Resource, View view, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(ContextCompat.getDrawable(context, Resource));
        } else {
            view.setBackgroundResource(Resource);
        }
    }

    public synchronized static boolean isNetAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mgr.getActiveNetworkInfo() != null && mgr.getActiveNetworkInfo().isConnected();
    }

    public static void askPermission(Activity activity, Context context, String permission, Integer requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    public void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getInstance(config);
    }

    public Realm getRealm() {
        return realm;
    }



}
