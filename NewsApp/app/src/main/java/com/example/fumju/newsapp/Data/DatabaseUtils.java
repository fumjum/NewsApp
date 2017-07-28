package com.example.fumju.newsapp.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.fumju.newsapp.Data.Contract.TABLE_ARTICLES.*;

/**
 * Created by fumju on 7/28/2017.
 */

public class DatabaseUtils {

    //gets all the database items and sets them to the cursor, order by date
    public static Cursor getAll(SQLiteDatabase db){
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_NAME_DATE + " DESC");

        return cursor;
    }

    //Inserts the articles in the database froom the arraylist
    public static void bulkInsert(SQLiteDatabase db, ArrayList<Article> articles){

        db.beginTransaction();
        try{
            for(Article a : articles){
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_AUTHOR, a.getAuthor());
                cv.put(COLUMN_NAME_TITLE, a.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, a.getDescription());
                cv.put(COLUMN_NAME_DATE, a.getDatePublished());
                cv.put(COLUMN_NAME_IMAGE, a.getImageURL());
                cv.put(COLUMN_NAME_URL, a.getArticleUrl());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
            db.close();
        }
    }

    //deletes the table from the database
    public static void deleteAll(SQLiteDatabase db){
        db.delete(TABLE_NAME, null, null);
    }
}
