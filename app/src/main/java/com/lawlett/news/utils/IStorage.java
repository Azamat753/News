package com.lawlett.news.utils;

import androidx.lifecycle.LiveData;

import com.lawlett.news.data.models.Article;

import java.util.List;

public interface IStorage {
LiveData<List<Article>> getAllLive();
}
