package com.alextroy.tinkoffnewstest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alextroy.tinkoffnewstest.dto.NewsItem;

import java.util.Comparator;
import java.util.List;

public class Utils {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void sortByDate(List<NewsItem> newsItems) {
        newsItems.sort(new Comparator<NewsItem>() {
            @Override
            public int compare(NewsItem o1, NewsItem o2) {
                return Long.compare(o1.getPublicationDate().getMilliseconds(), o2.getPublicationDate().getMilliseconds());
            }
        });
    }
}
