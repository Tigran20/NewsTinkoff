package com.alextroy.tinkoffnewstest.api;

import com.alextroy.tinkoffnewstest.dto.Example;
import com.alextroy.tinkoffnewstest.dto.GeneralNews;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("news")
    Single<GeneralNews> getNews();

    @GET("news_content")
    Single<Example> getContent(@Query("id") String id);
}
