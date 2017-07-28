package com.example.fumju.newsapp;

import android.os.AsyncTask;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by fumju on 7/28/2017.
 */

public class NewsJob extends JobService {
    AsyncTask mBackgroundTask;

    //refreshes the news articles as a "job" in the background
    @Override
    public boolean onStartJob(final JobParameters job){
        mBackgroundTask = new AsyncTask(){
            @Override
            protected void onPreExecute(){
                Toast.makeText(NewsJob.this, "News Refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params){
                RefreshTasks.refreshArticles(NewsJob.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o){
                jobFinished(job, false);
                super.onPostExecute(o);
            }

        };

        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job){
        if(mBackgroundTask != null)
            mBackgroundTask.cancel(false);

        return true;
    }
}
