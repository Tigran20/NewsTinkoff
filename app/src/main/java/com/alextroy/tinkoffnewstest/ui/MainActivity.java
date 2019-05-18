package com.alextroy.tinkoffnewstest.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alextroy.tinkoffnewstest.R;
import com.alextroy.tinkoffnewstest.adapter.NewsAdapter;
import com.alextroy.tinkoffnewstest.api.NewsApp;
import com.alextroy.tinkoffnewstest.dto.GeneralNews;
import com.alextroy.tinkoffnewstest.dto.NewsItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.alextroy.tinkoffnewstest.Utils.sortByDate;

public class MainActivity extends AppCompatActivity {

    public static final String ERROR = "Error";
    private List<NewsItem> news;

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private SwipeRefreshLayout swipeRefreshLayout;

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
        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new NewsAdapter(getApplicationContext(), news);

        setRecyclerView();
        updateData();
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getNews() {
        NewsApp.getApi().getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<GeneralNews>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(GeneralNews generalNews) {
                        List<NewsItem> newsItems = generalNews.getPayload();
                        sortByDate(newsItems);
                        adapter.setData(newsItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(findViewById(R.id.recycler_view), R.string.error_lodaing_data, Snackbar.LENGTH_SHORT).show();
                        Log.i(ERROR, e.getMessage());
                    }
                });
    }

    private void updateData() {
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getNews();
                        Snackbar.make(findViewById(R.id.recycler_view), R.string.update_data, Snackbar.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }
}
