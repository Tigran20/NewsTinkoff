package com.alextroy.tinkoffnewstest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alextroy.tinkoffnewstest.R;
import com.alextroy.tinkoffnewstest.dto.NewsItem;
import com.alextroy.tinkoffnewstest.ui.NewsDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int position) {
        final NewsItem result = listItem.get(position);
        holder.titleTextView.setText(result.getText());

        long originalDate = result.getPublicationDate().getMilliseconds();
        String date = getDate(originalDate, "HH:mm");
        holder.date.setText(date);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailsActivity.newIntent(context, result);
            }
        });
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

        private CardView cardView;
        private TextView titleTextView;
        private TextView date;

        public NewsViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            titleTextView = itemView.findViewById(R.id.news_description);
            date = itemView.findViewById(R.id.news_date);
        }
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void sendData() {

    }
}
