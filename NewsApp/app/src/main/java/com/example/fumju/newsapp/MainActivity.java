package com.example.fumju.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterOnClickHandler{

    static final String TAG = "mainactivty";
    private ProgressBar progress;
    private RecyclerView newsRecyclerView;
    private NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        newsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new NewsAdapter(this);

        newsRecyclerView.setAdapter(adapter);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if (itemNumber == R.id.action_search) {
            URL url = NetworkUtils.makeURL();
            new NewsRequests().execute(url);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        @Override
        public void onItemClick(NewsItem newsItem){
            Uri webpage = Uri.parse(newsItem.getArticleUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }


    public class NewsRequests extends AsyncTask<URL, Void, ArrayList<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newsRecyclerView.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {

            ArrayList<NewsItem> result = null;
            URL url = params[0];

            Log.d(TAG, "url: " + url.toString());

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            newsRecyclerView.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            adapter.setNewsItems(data);
        }
    }
    }
