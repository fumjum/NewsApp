package com.example.fumju.newsapp.Data;

/**
 * Created by fumju on 7/28/2017.
 */

//This is the object for articles
//was originally NewsItems before the database was added
//Added imageUrl
public class Article {

    public String author;
    public String title;
    public String description;
    public String articleUrl;
    public String datePublished;
    public String imageURL;

    public Article(){}

    public Article(String author, String title, String description, String articleUrl, String datePublished, String imageURL){
        this.author = author;
        this.title = title;
        this.description = description;
        this.articleUrl = articleUrl;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }
}
