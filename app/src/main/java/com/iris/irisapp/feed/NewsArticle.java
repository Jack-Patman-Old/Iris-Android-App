package com.iris.irisapp.feed;

import java.util.List;

/**
 * Created by Jack on 21/02/2015.
 */
public class NewsArticle
{
    private int id;

    private String outletAcronym;
    private String articleUrl;

    public NewsArticle(String acronym, String url)
    {
        this.outletAcronym = acronym;
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

    public String getOutletAcronym() {
        return outletAcronym;
    }

    public void setOutletAcronym(String outletAcronym) {
        this.outletAcronym = outletAcronym;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }
}
