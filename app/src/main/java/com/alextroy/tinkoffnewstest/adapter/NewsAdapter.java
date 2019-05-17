package com.alextroy.tinkoffnewstest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alextroy.tinkoffnewstest.R;
import com.alextroy.tinkoffnewstest.dto.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> listItem;
    private Context context;

    public NewsAdapter(Context context, List<NewsItem> list) {
        this.context = context;
        listItem = list;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem result = listItem.get(position);
        holder.titleTextView.setText(result.getText());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public void setData(List<NewsItem> list) {
        listItem.addAll(list);
        if (listItem.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.no_news), Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.news_description);
        }
    }
}