package com.lawlett.news.ui.main;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lawlett.news.data.models.Article;
import com.lawlett.news.utils.App;
import com.lawlett.news.utils.IApiClient;

import java.util.List;

public class MainViewModel extends ViewModel {
    public static final String TAG = "TAG";
    MutableLiveData<List<Article>> newsData = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    LiveData<List<Article>> newsFromRoom = App.iStorage.getAllLive();

    void getDataByNews(int page, int pageSize) {
        App.newsRepository.getNewsByCategory("0c11e5cf70b94bf595513fcae7cea2dc", "ru",
                "business", page, pageSize, new IApiClient.ICallBack() {
                    @Override
                    public void onSuccess(List<Article> result) {
                        newsData.setValue(result);
                        isLoading.setValue(true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "onFailure: " + e);
                    }
                });
    }
}
