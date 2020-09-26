package com.lawlett.news.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.news.R;
import com.lawlett.news.data.models.Article;
import com.lawlett.news.ui.DetailActivity;
import com.lawlett.news.utils.Extension;
import com.lawlett.news.utils.IOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements IOnClickListener {
    List<Article> list = new ArrayList<>();
    private Context context;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private boolean isLoadingAdded = false;

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_holder, parent, false);
        NewsViewHolder view_holder = new NewsViewHolder(view);
        view_holder.setOnClickListener(this);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setBusiness(List<Article> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public void clear() {
        list.clear();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("url", list.get(position).getUrl());
        context.startActivity(intent);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle;
        ImageView newsImage;
        private IOnClickListener listener;
        private int position;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            subTitle = itemView.findViewById(R.id.sub_title);
            newsImage = itemView.findViewById(R.id.news_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                }
            });
        }

        public void bind(Article example, int position) {
            title.setText((example.getTitle()));
            subTitle.setText(example.getDescription());
            Extension.loadImage(itemView.getContext(), example.getUrlToImage(), newsImage);
            this.position = position;
        }

        public void setOnClickListener(IOnClickListener listener) {
            this.listener = listener;
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.my_progressBar);

        }
    }

}
