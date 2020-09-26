package com.lawlett.news.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.news.data.models.NewsModel;
import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT*FROM newsModel")
    List<NewsModel> getAll();

    @Query("SELECT*FROM newsModel")
    LiveData<List<NewsModel>> getAllLive();

    @Insert
    void insert(NewsModel newsModel);

    @Delete
    void delete(NewsModel newsModel);

    @Delete
    void deleteAll(List<NewsModel> newsModel);

    @Update
    void update(NewsModel newsModel);

}
