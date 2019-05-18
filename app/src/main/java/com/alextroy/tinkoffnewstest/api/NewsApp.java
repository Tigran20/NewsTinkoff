package com.alextroy.tinkoffnewstest.api;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApp extends Application {

    private static final String BASE_URL = "https://api.tinkoff.ru/v1/";

    private Retrofit retrofit;
    private static NewsApi api;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(NewsApi.class);
    }

    public static NewsApi getApi() {
        return api;
    }
}
