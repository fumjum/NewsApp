package com.example.fumju.newsapp.Data;

import android.provider.BaseColumns;

/**
 * Created by fumju on 7/28/2017.
 */

//This sets up strings to be used throughout the app to refer to the database table and columns
public class Contract {

    public static class TABLE_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE = "image_url";
        public static final String COLUMN_NAME_URL = "url";
    }
}
