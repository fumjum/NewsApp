package com.example.fumju.newsapp;

/**
 * Created by fumju on 6/28/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fumju.newsapp.Data.Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.data;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private Context context;
    public static final String TAG = "newsadapter";
//    private ArrayList<NewsItem> data;
//    private NewsAdapterOnClickHandler newsAdapterOnClickHandler;


    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    //Allows the recycler view to get a new viewholder
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    //Allows recycler view to display data at a specific position
    @Override
    public void onBindViewHolder(ItemHolder holder, int position){
        holder.bind(position);
    }

    //uses the db cursor to get the item count instead of using the arraylist before
    @Override
    public int getItemCount(){
        return cursor.getCount();
    }

    //This class sets up a holder for all the items in the db
    //It includes the elements needed from the api that are displayed in the view
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView author;
        TextView title;
        TextView description;
        TextView date;
        ImageView image;

        ItemHolder(View view){
            super(view);
            author = (TextView)view.findViewById(R.id.author);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            date = (TextView)view.findViewById(R.id.date);
            image = (ImageView)view.findViewById(R.id.image);
            view.setOnClickListener(this);
        }

        //bind method sets the text of the view to the columns of the database
        public void bind(int pos){
            cursor.moveToPosition(pos);
            author.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR)));
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            date.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DATE)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_IMAGE));
            Log.d(TAG, url);
            //if the image url is not null, Picasso will load the image into the view
            if(url != null){
                Picasso.with(context).load(url).into(image);
            }
        }

        //when an item is clicked will call the onItemClick method from listener
        @Override
        public void onClick(View v){
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);
        }
    }
}

//old methods before changes, kept in case I needed them

//    @Override
//    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        Context context = viewGroup.getContext();
//        int layoutIdForListItem = R.layout.item;
//        LayoutInflater inflater = LayoutInflater.from(context);
//        boolean shouldAttachToParentImmediately = false;
//
//        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
//
//        return new NewsAdapterViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(NewsAdapterViewHolder newsAdapterViewHolder, int position){
//        newsAdapterViewHolder.bind(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        if(data == null){
//            return 0;
//        }else{
//            return data.size();
//        }
//    }
//
//    public void setNewsItems(ArrayList<NewsItem> newsItems){
//        data = newsItems;
//        notifyDataSetChanged();
//    }
//
//    public interface NewsAdapterOnClickHandler{
//        void onItemClick(NewsItem newsItem);
//    }
//
//    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView authorView;
//        TextView titleView;
//        TextView descriptionView;
//        TextView dateView;
//
//        public NewsAdapterViewHolder(View view){
//            super(view);
//            view.setOnClickListener(this);
//            authorView = (TextView)view.findViewById(R.id.author);
//            titleView = (TextView)view.findViewById(R.id.title);
//            descriptionView = (TextView)view.findViewById(R.id.description);
//            dateView = (TextView)view.findViewById(R.id.date);
//        }
//
//        public void bind(int pos){
//            NewsItem news = data.get(pos);
//            authorView.setText(news.getAuthor());
//            titleView.setText(news.getTitle());
//            descriptionView.setText(news.getDescription());
//            dateView.setText(news.getDatePublished());
//        }
//
//        @Override
//        public void onClick(View v) {
//            int pos = getAdapterPosition();
//            NewsItem newsItem = data.get(pos);
//            newsAdapterOnClickHandler.onItemClick(newsItem);
//        }
//    }
//
//}
