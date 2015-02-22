package com.iris.irisapp.feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 21/02/2015.
 */
public class NewsHeadline
{
    private String headline;
    private List<NewsArticle> outlets;

    public NewsHeadline()
    {
        outlets = new ArrayList<>();
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public List<NewsArticle> getOutlets() {
        return outlets;
    }

    public void setOutlets(List<NewsArticle> outlets) {
        this.outlets = outlets;
    }

    public void addOutlet(NewsArticle outlet) {this.outlets.add(outlet);}
}
