package com.iris.irisapp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iris.irisapp.R;
import com.iris.irisapp.db.DbAccessor;
import com.iris.irisapp.db.ProcessedArticle;
import com.iris.irisapp.feed.NewsArticle;
import com.iris.irisapp.feed.NewsCategory;
import com.iris.irisapp.feed.NewsHeadline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {
    private NewsCategory currentCategory;
    Button btnPreviousCategory;
    Button btnNextCategory;
    TextView newsCategoryCard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final int WORLD_NEWS_CATEGORY_ID = 2;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start by loading the world news category initially
        newsCategoryCard = (TextView) findViewById(R.id.txtCategory);
        loadCategory(WORLD_NEWS_CATEGORY_ID);

        // Add button listener for previous category selection
        btnPreviousCategory = (Button) findViewById(R.id.btnPrev);
        btnPreviousCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadCategory(currentCategory.getCategoryId()-1);
            }
        });

        // Add button listener for next category selection
        btnNextCategory = (Button) findViewById(R.id.btnNext);
        btnNextCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadCategory(currentCategory.getCategoryId()+1);
            }
        });


    }

    private void loadCategory(int categoryId)
    {
        NewsCategory category = NewsCategory.getCategoryById(categoryId);

        if (newsCategoryCard != null && category != null && category.getCategoryId() != 0)
        {
            currentCategory = category;
            newsCategoryCard.setBackgroundColor(Color.parseColor(category.getCategoryColour()));
            newsCategoryCard.setText(category.getCategoryName());

            List<NewsHeadline> headlines = getCategoryArticles(categoryId);
            // loadHeadlineCards();
        }
    }

    private List<NewsHeadline> getCategoryArticles(int categoryId)
    {
        final String ARTICLE_SCRIPT_URL = "http://91.212.182.221/~jackpatm/load_processed_articles.php";
        DbAccessor db = new DbAccessor();

        List<ProcessedArticle> articles = null;
        try {
            articles = (List<ProcessedArticle>) db.execute(new Object[]{ARTICLE_SCRIPT_URL}).get();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        return groupArticles(articles, categoryId);
    }

    private List<NewsHeadline> groupArticles(List<ProcessedArticle> articles, int categoryId)
    {
        List<String> groupedArticleUrls = new ArrayList<>();
        List<NewsHeadline> headlines = new ArrayList<>();

        for (ProcessedArticle article: articles)
        {
            if (article.getCategoryId()!= categoryId)
            {
                continue;
            }

            if (groupedArticleUrls.contains(article.getUrl()))
            {
                continue;
            }

            else
            {
                for (ProcessedArticle comparisonArticle: articles)
                {
                    NewsHeadline headline = new NewsHeadline();
                    headline.setHeadline(article.getHeadline());

                    if (comparisonArticle.getCategoryId() != categoryId)
                    {
                        continue;
                    }
                    else if (comparisonArticle.getHeadline().equals(article.getHeadline()))
                    {
                        groupedArticleUrls.add(comparisonArticle.getUrl());

                        NewsArticle groupedArticle = new NewsArticle();
                        groupedArticle.setArticleUrl(comparisonArticle.getUrl());

                        headline.addOutlet(groupedArticle);
                        headlines.add(headline);
//                        groupedArticle.setOutletAcronym(comparisonArticle.);
                    }
                }
            }

        }

        return headlines;
    }

}
