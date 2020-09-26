package com.lawlett.news.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lawlett.news.R;
import com.lawlett.news.data.internet.RetrofitBuilder;
import com.lawlett.news.data.models.Example;
import com.lawlett.news.recycler.NewsAdapter;
import com.lawlett.news.utils.Extension;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NewsAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        initListeners();
    }

    private void initListeners() {
        final ImageView more, hamburger;
        more = findViewById(R.id.more);
        hamburger = findViewById(R.id.hamburger);
        swipeRefreshLayout = findViewById(R.id.swipe_refresher);
        progressBar = findViewById(R.id.my_progressBar);
        nestedScrollView = findViewById(R.id.scroll_view);
        getBusiness(page, limit);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    limit = +10;
                    progressBar.setVisibility(View.VISIBLE);
                    getBusiness(page, limit);
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
                refreshRecyclerViewMessages();
            }
        });
    }

    private void refreshRecyclerViewMessages() {
        adapter.clear();
        getBusiness(page, limit);
        swipeRefreshLayout.setRefreshing(true);
    }

    private void getBusiness(int page, int limit) {
        RetrofitBuilder.getService().
                getExample("0c11e5cf70b94bf595513fcae7cea2dc", "ru", "business", page, limit).
                enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            adapter.setBusiness(response.body().getArticles());
                            swipeRefreshLayout.setRefreshing(false);
                            progressBar.setVisibility(View.GONE);

                        } else {
                            Extension.showToast(MainActivity.this, "Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Extension.showToast(MainActivity.this, "Failure");
                        swipeRefreshLayout.setRefreshing(true);
                        progressBar.setVisibility(View.GONE);

                    }
                });
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