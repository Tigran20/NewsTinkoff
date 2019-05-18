package com.alextroy.tinkoffnewstest.api;

import com.alextroy.tinkoffnewstest.dto.GeneralNews;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NewsApi {
    @GET("news")
    Single<GeneralNews> getNews();
}
