package com.lawlett.news.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lawlett.news.data.models.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NewsDao newsDao();

}
