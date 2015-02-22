package com.iris.irisapp.feed;

import java.util.ArrayList;
import java.util.List;

public class NewsFeed
{
    private NewsCategory category;
    private List<NewsArticle> articleList;

    public NewsFeed(NewsCategory category)
    {
        this.category = category;
        LoadNextTwentyArticles();
    }

    public void LoadNextTwentyArticles()
    {
        if (articleList == null)
        {
            articleList = new ArrayList<>();
        }
        else {

        }
    }

    public NewsCategory getFeedCategory()
    {
        return category;
    }

    public List<NewsArticle> getFeedArticles()
    {
        return articleList;
    }

}
