package com.iris.irisapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.iris.irisapp.R;
import com.iris.irisapp.db.DbAccessor;
import com.iris.irisapp.db.ProcessedArticle;
import com.iris.irisapp.feed.NewsArticle;
import com.iris.irisapp.feed.NewsHeadline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {
    private String currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbAccessor db = new DbAccessor();
        List<ProcessedArticle> articles = null;
        try {
            articles = (List<ProcessedArticle>) db.execute(new Object[]{"http://91.212.182.221/~jackpatm/load_processed_articles.php"}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<NewsHeadline> processedArticles = groupArticles(articles);

        setContentView(R.layout.activity_main);

        currentCategory = getResources().getStringArray(R.array.news_categories)[0];
        loadCategory(currentCategory);
    }

    private List<NewsHeadline> groupArticles(List<ProcessedArticle> articles)
    {
        List<String> groupedArticleUrls = new ArrayList<>();
        List<NewsHeadline> headlines = new ArrayList<>();

        for (ProcessedArticle article: articles)
        {
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
                    if (comparisonArticle.getHeadline().equals(article.getHeadline()))
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


    private void loadCategory(String currentCategory) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
