package com.lawlett.news.utils;

import android.app.Application;

import androidx.room.Room;

import com.lawlett.news.data.internet.ApiClient;
import com.lawlett.news.data.internet.NewsRepository;
import com.lawlett.news.room.AppDataBase;

public class App extends Application {

    public static AppDataBase dataBase;
    public static IApiClient apiClient;
    public static NewsRepository newsRepository;
    public static IStorage iStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        dataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
                .allowMainThreadQueries().build();

        apiClient = new ApiClient();
        iStorage = new Storage(dataBase.newsDao());
        newsRepository = new NewsRepository(apiClient, iStorage, dataBase.newsDao());

    }
}
