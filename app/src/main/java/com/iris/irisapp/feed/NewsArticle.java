package com.iris.irisapp.feed;

import java.util.List;

/**
 * Created by Jack on 21/02/2015.
 */
public class NewsArticle
{
    private int id;

    private int outletId;
    private String articleUrl;

    public NewsArticle(int outletId, String url)
    {
        this.outletId = outletId;
        this.articleUrl = url;
    }

    public NewsArticle()
    {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }
}
