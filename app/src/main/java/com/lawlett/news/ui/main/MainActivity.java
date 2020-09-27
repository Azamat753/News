package com.lawlett.news.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lawlett.news.R;
import com.lawlett.news.data.models.Article;
import com.lawlett.news.recycler.NewsAdapter;
import com.lawlett.news.ui.DetailActivity;
import com.lawlett.news.ui.DetailsActivity;
import com.lawlett.news.utils.App;
import com.lawlett.news.utils.Extension;
import com.lawlett.news.utils.IOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NewsAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    private List<Article> list = new ArrayList<>();
    ImageView more, hamburger;
    MainViewModel viewModel;
    int page = 1, pageSize = 10;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        initialization();
        initListeners();
        getDataByNews();

    }

    private void getDataByNews() {
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            viewModel.getDataByNews(page, pageSize);
            if (App.dataBase.newsDao().getAll() != null)
                App.dataBase.newsDao().deleteAll();
            viewModel.newsData.observe(this, new Observer<List<Article>>() {
                @Override
                public void onChanged(List<Article> articles) {
                    App.dataBase.newsDao().insert(articles);
                    list.addAll(articles);
                    adapter.updateAdapter(list);
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            viewModel.newsFromRoom.observe(this, new Observer<List<Article>>() {
                @Override
                public void onChanged(List<Article> articles) {
                    if (articles != null) {
                        list.addAll(articles);
                        adapter.updateAdapter(list);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
        viewModel.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initialization() {
        more = findViewById(R.id.more);
        hamburger = findViewById(R.id.hamburger);
        swipeRefreshLayout = findViewById(R.id.swipe_refresher);
        nestedScrollView = findViewById(R.id.scroll_view);
        progressBar = findViewById(R.id.my_progressBar);
        nestedScrollView = findViewById(R.id.scroll_view);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void initListeners() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
                        page++;
                        pageSize = +10;
                        progressBar.setVisibility(View.VISIBLE);
                        viewModel.getDataByNews(page,pageSize);
                    }else {
                        Extension.showToast(MainActivity.this, "Проверьте интернет");
                    }
                }
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Extension.showToast(MainActivity.this, "More");
            }
        });
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Extension.showToast(MainActivity.this, "Hamburger");
            }
        });
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecyclerView();
            }
        });

        adapter.setOnItemClickListener(new IOnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("url", list.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    private void refreshRecyclerView() {
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            list.clear();
            page = 1;
            pageSize = 10;
            viewModel.getDataByNews(page, pageSize);
            Extension.showToast(this, "Страницы обновлена");
        } else {
            Extension.showToast(this, "Проверьте интернет");
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}