package com.example.fumju.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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

import com.example.fumju.newsapp.Data.Contract;
import com.example.fumju.newsapp.Data.DBHelper;
import com.example.fumju.newsapp.Data.DatabaseUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener {

    static final String TAG = "mainactivty";
    private ProgressBar progress;
    private RecyclerView newsRecyclerView;
    private NewsAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;

    private static final int NEWS_LOADER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        newsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //checks if is first install
        boolean isFirst = prefs.getBoolean("isFirst", true);
//        adapter = new NewsAdapter(this);
//
//        newsRecyclerView.setAdapter(adapter);
        //if it is first install, changes boolean to indicate that it is not
        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
        //sets up the schedule to refresh at certain intervals
        ScheduleUtilities.scheduleRefresh(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new NewsAdapter(cursor, this);
        newsRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
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
//            URL url = NetworkUtils.makeURL();
//            new NewsRequests().execute(url);
//            return true;
            load();
        }

        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                RefreshTasks.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);

        adapter = new NewsAdapter(cursor, this);
        newsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {
    }

    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
//            Uri webpage = Uri.parse(newsItem.getArticleUrl());
//            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//
//            if(intent.resolveActivity(getPackageManager()) != null){
//                startActivity(intent);
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }
}


//    public class NewsRequests extends AsyncTask<URL, Void, ArrayList<NewsItem>> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            newsRecyclerView.setVisibility(View.INVISIBLE);
//            progress.setVisibility(View.VISIBLE);
//
//        }
//
//        @Override
//        protected ArrayList<NewsItem> doInBackground(URL... params) {
//
//            ArrayList<NewsItem> result = null;
//            URL url = params[0];
//
//            Log.d(TAG, "url: " + url.toString());
//
//            try {
//                String json = NetworkUtils.getResponseFromHttpUrl(url);
//                result = NetworkUtils.parseJSON(json);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<NewsItem> data) {
//            super.onPostExecute(data);
//            newsRecyclerView.setVisibility(View.VISIBLE);
//            progress.setVisibility(View.GONE);
//            adapter.setNewsItems(data);
//        }
//    }
//    }
