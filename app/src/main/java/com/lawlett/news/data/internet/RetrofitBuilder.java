package com.lawlett.news.data.internet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static RetrofitService service;

    public static RetrofitService getService() {
        if (service == null)
            service = builderRetrofit();
        return service;
    }

    private static RetrofitService builderRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService.class);
    }
}