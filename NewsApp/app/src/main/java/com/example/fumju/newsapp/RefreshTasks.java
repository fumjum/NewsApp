package com.example.fumju.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.fumju.newsapp.Data.Article;
import com.example.fumju.newsapp.Data.DBHelper;
import com.example.fumju.newsapp.Data.DatabaseUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by fumju on 7/28/2017.
 */

public class RefreshTasks {

    public static final String ACTION_REFRESH = "refresh";

    //refreshes the articles by deleting them from the database and getting the new ones in there
    public static void refreshArticles(Context context){
        ArrayList<Article> result = null;
        URL url = NetworkUtils.makeURL();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try{
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DatabaseUtils.bulkInsert(db, result);

        }catch(IOException e){
            e.printStackTrace();

        }catch(JSONException e){
            e.printStackTrace();
        }

        db.close();
    }
}
