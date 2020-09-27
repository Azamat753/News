package com.lawlett.news.utils;

import com.lawlett.news.data.models.Article;

import java.util.List;

public interface IApiClient {
    void getNewsByCategory(String apiKey, String country, String category,
                           int page, int pageSize, ICallBack callBack);

    interface ICallBack extends IBaseCallBack<List<Article>> {
        @Override
        void onSuccess(List<Article> result);

        @Override
        void onFailure(Exception e);
    }
}
