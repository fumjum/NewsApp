package com.example.fumju.newsapp;

import android.net.Uri;
import android.util.Log;

import com.example.fumju.newsapp.Data.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by fumju on 6/19/2017.
 */

public class NetworkUtils {
    public static final String NEWSAPI_BASE_URL = "https://newsapi.org/v1/articles";
    public static final String PARAM_QUERY = "source";
    public static final String PARAM_SORT = "sortBy";
    public static final String PARAM_KEY = "apiKey";
    public static final String KEY = "d6ea471fcbdd494991394bb87530c043";

    public static final String TAG = "NetworkUtils";

    public static URL makeURL(){
        Uri uri = Uri.parse(NEWSAPI_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, "the-next-web")
                .appendQueryParameter(PARAM_SORT, "latest")
                .appendQueryParameter(PARAM_KEY, KEY).build();

        URL url = null;
        try{
            url = new URL(uri.toString());
            Log.d(TAG, "Url: " + url);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);

            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            } else {
                return null;
            }

        }finally{
            urlConnection.disconnect();
        }
    }

    //In this method I added the Image Url from the api
    public static ArrayList<Article> parseJSON(String json) throws JSONException {
        ArrayList<Article> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("articles");
        //String imgURL = null;

        for(int i = 0; i < items.length(); i++){
            JSONObject article = items.getJSONObject(i);
            String author = article.getString("author");
            String title = article.getString("title");
            String description = article.getString("description");
            String articleUrl = article.getString("url");
            String datePublished = article.getString("publishedAt");
            String imgURL = article.getString("urlToImage");

            result.add(new Article(author, title, description, articleUrl, datePublished, imgURL));
        }
        Log.d(TAG, "final articles size: " + result.size());
        return result;
    }
}
