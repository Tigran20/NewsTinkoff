package com.alextroy.tinkoffnewstest.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alextroy.tinkoffnewstest.R;
import com.alextroy.tinkoffnewstest.dto.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {

    public static final String KEY = "key";

    private TextView textDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_activity);

        init();
        getIntentData();
    }

    private void init() {
        textDescription = findViewById(R.id.news_description);
    }

    private void getIntentData() {
        final NewsItem data = (NewsItem) getIntent().getSerializableExtra(KEY);
        textDescription.setText(data.getText());
    }

    public static void newIntent(Context context, NewsItem newsItem) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable(KEY, newsItem);
        intent.putExtras(bundle);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
