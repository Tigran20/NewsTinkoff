package com.alextroy.tinkoffnewstest.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alextroy.tinkoffnewstest.R;
import com.alextroy.tinkoffnewstest.api.NewsApp;
import com.alextroy.tinkoffnewstest.dto.Example;
import com.alextroy.tinkoffnewstest.dto.NewsItem;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsActivity extends AppCompatActivity {

    public static final String KEY = "key";
    public static final String POS = "pos";

    private int pos;
    private TextView textDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_activity);

        init();
        getNews();
    }

    private void init() {
        textDescription = findViewById(R.id.news_description);
    }

    public static void newIntent(Context context, NewsItem newsItem, int pos) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable(KEY, newsItem);
        intent.putExtra(POS, pos);
        intent.putExtras(bundle);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void getNews() {
        NewsItem data = (NewsItem) getIntent().getSerializableExtra(KEY);
        Intent intent = getIntent();
        pos = intent.getIntExtra(POS, 0);

        NewsApp.getApi().getContent(data.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Example>() {
                    @Override
                    public void onSuccess(Example generalNews) {
                        String plain = Html.fromHtml(generalNews.getPayload().getContent()).toString();
                        textDescription.setText(plain);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
}
