package com.example.fumju.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "mainactivty";
    private ProgressBar progress;
    private TextView newsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        newsTextView = (TextView) findViewById(R.id.news_data);
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


    public class NewsRequests extends AsyncTask<URL, Void, String> {

        String query;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newsTextView.setText("");
            progress.setVisibility(View.VISIBLE);

        }
        @Override
        protected String doInBackground(URL... params){

            URL url = params[0];
            String result = null;

            try{
                result = NetworkUtils.getResponseFromHttpUrl(url);

            } catch (IOException e){
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(View.GONE);
            newsTextView.setText(s);
            }
        }
    }
