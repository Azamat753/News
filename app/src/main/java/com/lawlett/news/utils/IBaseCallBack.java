package com.lawlett.news.utils;

public interface IBaseCallBack<R> {
    void onSuccess(R result);

    void onFailure(Exception e);

}
