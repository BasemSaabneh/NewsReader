package com.devschema.sh4d0w.newsreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    static class ViewHolder {
        TextView title;
        TextView section;
        TextView date;
        TextView author;
    }

    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listNewsView = convertView;
        if (listNewsView == null) {
            listNewsView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item,
                            parent,
                            false);
        }

        ViewHolder holder = new ViewHolder();
        News currentNews = getItem(position);

        holder.title = (TextView) listNewsView.findViewById(R.id.title);
        holder.title.setText(currentNews.getNewsTitle());

        holder.section = (TextView) listNewsView.findViewById(R.id.section);
        holder.section.setText(currentNews.getNewsSectionName());

        holder.date = (TextView) listNewsView.findViewById(R.id.date);
        holder.date.setText(currentNews.getNewsPublicationDate());

        holder.author = (TextView) listNewsView.findViewById(R.id.author);
        holder.author.setText(currentNews.getNewsAuthor());

        return listNewsView;
    }
}
