package com.lawlett.news.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.lawlett.news.data.models.Article;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT*FROM article")
    List<Article> getAll();

    @Query("SELECT*FROM article")
    LiveData<List<Article>> getAllLive();

    @Insert
    void insert(List<Article> articles);

    @Query("DELETE FROM article")
    void deleteAll();

}
