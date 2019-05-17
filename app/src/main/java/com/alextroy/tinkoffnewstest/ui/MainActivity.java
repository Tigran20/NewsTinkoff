package com.alextroy.tinkoffnewstest.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alextroy.tinkoffnewstest.R;
import com.alextroy.tinkoffnewstest.adapter.NewsAdapter;
import com.alextroy.tinkoffnewstest.api.App;
import com.alextroy.tinkoffnewstest.dto.GeneralNews;
import com.alextroy.tinkoffnewstest.dto.NewsItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private List<NewsItem> news;

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        init();
        getNews();
    }

    private void init() {
        news = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new NewsAdapter(getApplicationContext(), news);

        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getNews() {
        App.getApi().getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<GeneralNews>() {
                    @Override
                    public void onSuccess(GeneralNews generalNews) {
                        adapter.setData(generalNews.getPayload());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, getString(R.string.error_lodaing_data), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
