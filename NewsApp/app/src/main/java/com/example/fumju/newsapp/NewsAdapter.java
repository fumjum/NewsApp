package com.example.fumju.newsapp;

/**
 * Created by fumju on 6/28/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.data;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private ArrayList<NewsItem> data;
    private NewsAdapterOnClickHandler newsAdapterOnClickHandler;


    public NewsAdapter(NewsAdapterOnClickHandler newsAdapterOnClickHandler){
        this.newsAdapterOnClickHandler = newsAdapterOnClickHandler;
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder newsAdapterViewHolder, int position){
        newsAdapterViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(data == null){
            return 0;
        }else{
            return data.size();
        }
    }

    public void setNewsItems(ArrayList<NewsItem> newsItems){
        data = newsItems;
        notifyDataSetChanged();
    }

    public interface NewsAdapterOnClickHandler{
        void onItemClick(NewsItem newsItem);
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView authorView;
        TextView titleView;
        TextView descriptionView;
        TextView dateView;

        public NewsAdapterViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            authorView = (TextView)view.findViewById(R.id.author);
            titleView = (TextView)view.findViewById(R.id.title);
            descriptionView = (TextView)view.findViewById(R.id.description);
            dateView = (TextView)view.findViewById(R.id.date);
        }

        public void bind(int pos){
            NewsItem news = data.get(pos);
            authorView.setText(news.getAuthor());
            titleView.setText(news.getTitle());
            descriptionView.setText(news.getDescription());
            dateView.setText(news.getDatePublished());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            NewsItem newsItem = data.get(pos);
            newsAdapterOnClickHandler.onItemClick(newsItem);
        }
    }

}
