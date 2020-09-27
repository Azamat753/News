package com.lawlett.news.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.news.R;
import com.lawlett.news.data.models.Article;
import com.lawlett.news.utils.Extension;
import com.lawlett.news.utils.IOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    List<Article> list = new ArrayList<>();
    private Context context;
    private IOnClickListener listener;

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_holder, parent, false);
        NewsViewHolder view_holder = new NewsViewHolder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setBusiness(List<Article> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void updateAdapter(List<Article> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(IOnClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle;
        ImageView newsImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            subTitle = itemView.findViewById(R.id.sub_title);
            newsImage = itemView.findViewById(R.id.news_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        public void bind(Article example) {
            title.setText((example.getTitle()));
            subTitle.setText(example.getDescription());
            Extension.loadImage(itemView.getContext(), example.getUrlToImage(), newsImage);

        }
    }

}
