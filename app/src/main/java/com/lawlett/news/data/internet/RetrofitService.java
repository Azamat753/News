package com.lawlett.news.data.internet;

import com.lawlett.news.data.models.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("v2/top-headlines")
    Call<Example> getExample(
            @Query("apiKey") String apikey,
            @Query("country") String language,
            @Query("category")String category,
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );
    @GET("v2/everything")
    Call<Example> getNewsByKeyWord(@Query("q") String q,
                                   @Query("language") String country,
                                   @Query("apiKey") String apikey);
}

