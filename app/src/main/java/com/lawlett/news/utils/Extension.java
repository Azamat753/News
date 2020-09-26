package com.lawlett.news.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public  class Extension {
    public static void showToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void loadImage(Context context, String path, ImageView view){
        Glide.with(context).load(path).into(view);
    }
}
