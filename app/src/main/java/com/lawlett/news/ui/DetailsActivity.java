package com.lawlett.news.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lawlett.news.R;
import com.lawlett.news.utils.Extension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    private TextView title, desc, url, published, author;
    private ImageView image_d;
    public static final String IMAGE = "DIMAGE";
    public static final String TITLE = "DTITLE";
    public static final String DESC = "DDESC";
    public static final String PUBLISHED = "DPUB";
    public static final String URL = "DURL";
    public static final String AUTHOR = "AUTHOR";


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
                Extension.showToast(DetailsActivity.this, "More");
            }
        });
    }

    private void initViews() {
        title = findViewById(R.id.title_detail);
        desc = findViewById(R.id.description_detail);
        url = findViewById(R.id.url_detail);
        published = findViewById(R.id.publishedAt_detail);
        image_d = findViewById(R.id.image_detail);
        author = findViewById(R.id.author_detail);
    }

    @SuppressLint("SimpleDateFormat")
    public String dateConvert(String mainDate) {
        Date from = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    .parse(Objects.requireNonNull(mainDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String to = new SimpleDateFormat("dd-MM-yyyy HH:mm")
                .format(from);
        published.setText(to);

        return to;
    }


    @SuppressLint("SetTextI18n")
    private void getDataIntent() {
        if (getIntent() != null) {
            String iImage = getIntent().getStringExtra(IMAGE);
            String iTitle = getIntent().getStringExtra(TITLE);
            String iDesc = getIntent().getStringExtra(DESC);
            String iUrl = getIntent().getStringExtra(URL);
            String iPublished = getIntent().getStringExtra(PUBLISHED);
            String iAuthor = getIntent().getStringExtra(AUTHOR);
            String dataPublished= dateConvert(iPublished);

            if (iAuthor != null) {
                author.setText(getString(R.string.author) + iAuthor);
            } else {
                author.setText(R.string.author_unknown);
            }
            title.setText(iTitle);
            desc.setText(iDesc);
            Extension.loadImage(this, iImage, image_d);
            url.setText(getString(R.string.path) + iUrl);
            published.setText(getString(R.string.date_published)  + dataPublished);
            
        }
    }
}