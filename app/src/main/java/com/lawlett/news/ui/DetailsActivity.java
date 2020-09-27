package com.lawlett.news.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lawlett.news.R;
import com.lawlett.news.data.models.Article;
import com.lawlett.news.utils.Extension;

public class DetailsActivity extends AppCompatActivity {
    private TextView title, desc, url, published, author;
    private ImageView image_d;
    private static final String KEY = "KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        initListeners();
        getDataIntent();
    }
    private void initListeners() {
        ImageView more, back;
        back = findViewById(R.id.details_back);
        more = findViewById(R.id.details_more);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Extension.showToast(DetailsActivity.this,"More");
            }
        });
    }
    private void initViews() {
        title = findViewById(R.id.title_detail);
        desc = findViewById(R.id.description_detail);
        url = findViewById(R.id.url_detail);
        published = findViewById(R.id.publishedAt_detail);
        author = findViewById(R.id.author_detail);
        image_d = findViewById(R.id.image_detail);
    }

    @SuppressLint("SetTextI18n")
    private void getDataIntent() {
        if (getIntent() != null) {
            Article article = (Article) getIntent().getSerializableExtra(KEY);
            if (article != null) {
                title.setText(article.getTitle());
                desc.setText(article.getDescription());
                Extension.loadImage(this,article.getUrlToImage(),image_d);
                if (article.getAuthor() != null) {
                    author.setText(getString(R.string.author) + article.getAuthor());
                } else {
                    author.setText(R.string.author_unknown);
                }
                url.setText(getString(R.string.path) + article.getUrl());
                published.setText(getString(R.string.date_published) + article.getPublishedAt());
            }
        }
    }
}