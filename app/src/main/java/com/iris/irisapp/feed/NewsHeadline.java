package com.iris.irisapp.feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jack on 21/02/2015.
 */
public class NewsHeadline
{
    private int headlineId;
    private String headline;
    private Date publicationDate;
    private int categoryId;
    private List<NewsArticle> articles;

    public NewsHeadline()
    {
        articles = new ArrayList<>();
    }

    public List<NewsOutlet> getNewsOutlets ()
    {
        List<NewsOutlet> outlets = new ArrayList<>();

        for (NewsArticle article: articles)
        {
            outlets.add(NewsOutlet.getOutletById(article.getOutletId()));
        }

        return outlets;
    }

    public int getHeadlineId() {
        return headlineId;
    }

    public void setHeadlineId(int headlineId) {
        this.headlineId = headlineId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;}

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsArticle> articles) {
        this.articles = articles;
    }

    public void addOutlet(NewsArticle outlet) {this.articles.add(outlet);}
}
