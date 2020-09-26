package com.lawlett.news.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        getBusiness();
        initListeners();
    }

    private void initListeners() {
        final ImageView more, hamburger;
        more = findViewById(R.id.more);
        hamburger = findViewById(R.id.hamburger);
        swipeRefreshLayout = findViewById(R.id.swipe_refresher);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Extension.showToast(MainActivity.this,"More");
            }
        });

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Extension.showToast(MainActivity.this,"Hamburger");
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
        getBusiness();
        swipeRefreshLayout.setRefreshing(true);
    }

    private void getBusiness() {
        RetrofitBuilder.getService().
                getExample("0c11e5cf70b94bf595513fcae7cea2dc", "ru", "business",1,10).
                enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            adapter.setBusiness(response.body().getArticles());
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            Extension.showToast(MainActivity.this,"Error");
                        }
                    }
                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Extension.showToast(MainActivity.this,"Failure");
                        swipeRefreshLayout.setRefreshing(true);

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