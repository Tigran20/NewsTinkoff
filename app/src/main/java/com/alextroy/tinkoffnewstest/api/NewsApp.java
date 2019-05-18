package com.alextroy.tinkoffnewstest.api;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApp extends Application {

    private static final String BASE_URL = "https://api.tinkoff.ru/v1/";
    private static NewsApi api;

    private int cacheSize = 10 * 1024 * 1024;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private Cache cache;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        cache = new Cache(context.getCacheDir(), cacheSize);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(offlineInterceptor)
                .addNetworkInterceptor(onlineInterceptor)
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        api = retrofit.create(NewsApi.class);
    }

    public static NewsApi getApi() {
        return api;
    }

    //Cache
    private static Interceptor onlineInterceptor = chain -> {
        okhttp3.Response response = chain.proceed(chain.request());
        int maxAge = 60;
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxAge)
                .removeHeader("Pragma")
                .build();
    };

    private Interceptor offlineInterceptor = chain -> {
        Request request = chain.request();
        if (!isNetworkConnected()) {
            int maxStale = 60 * 60 * 24 * 30;
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return chain.proceed(request);
    };

    private boolean isNetworkConnected() {
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}
