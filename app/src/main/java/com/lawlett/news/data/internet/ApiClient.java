package com.lawlett.news.data.internet;

import com.lawlett.news.data.models.Example;
import com.lawlett.news.utils.IApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements IApiClient {
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RetrofitService client = retrofit.create(RetrofitService.class);

    @Override
    public void getNewsByCategory(String apiKey, String country, String category, int page, int pageSize, final ICallBack callBack) {
        Call<Example> call = client.getNewsByCategory(apiKey, country, category, page, pageSize);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.onSuccess(response.body().getArticles());
                } else {
                    callBack.onFailure(new Exception("Response is empty" + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                callBack.onFailure(new Exception(t));
            }
        });
    }
}
