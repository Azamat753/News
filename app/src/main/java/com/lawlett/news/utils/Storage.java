package com.lawlett.news.utils;

import androidx.lifecycle.LiveData;

import com.lawlett.news.data.models.Article;
import com.lawlett.news.room.NewsDao;

import java.util.List;

public class Storage implements IStorage{
    private NewsDao newsDao;

    public Storage(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public LiveData<List<Article>> getAllLive() {
        return newsDao.getAllLive();
    }
}
