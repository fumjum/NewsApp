package com.example.fumju.newsapp;

/**
 * Created by fumju on 6/28/2017.
 */

public class NewsItem {
    public String author;
    public String title;
    public String description;
    public String articleUrl;
    public String imageUrl;
    public String datePublished;

    public NewsItem(){}

    public NewsItem(String author, String title, String description, String articleUrl, String imageUrl, String datePublished){
        this.author = author;
        this.title = title;
        this.description = description;
        this.articleUrl = articleUrl;
        this.imageUrl = imageUrl;
        this.datePublished = datePublished;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }
}
