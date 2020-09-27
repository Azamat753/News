package com.lawlett.news.data.internet;

import androidx.lifecycle.LiveData;

import com.lawlett.news.data.models.Article;
import com.lawlett.news.room.NewsDao;
import com.lawlett.news.utils.IApiClient;
import com.lawlett.news.utils.IStorage;

import java.util.List;

public class NewsRepository implements IApiClient, IStorage {
    private IApiClient apiClient;
    private IStorage iStorage;
    private NewsDao newsDao;

    public NewsRepository(IApiClient apiClient, IStorage iStorage, NewsDao newsDao) {
        this.apiClient = apiClient;
        this.iStorage = iStorage;
        this.newsDao = newsDao;
    }


    @Override
    public LiveData<List<Article>> getAllLive() {
        return null;
    }

    @Override
    public void getNewsByCategory(String apiKey, String country, String category, int page, int pageSize, final ICallBack callBack) {
        apiClient.getNewsByCategory(apiKey, country, category, page, pageSize, new ICallBack() {
            @Override
            public void onSuccess(List<Article> result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onFailure(Exception e) {
                callBack.onFailure(e);
            }
        });
    }
}
