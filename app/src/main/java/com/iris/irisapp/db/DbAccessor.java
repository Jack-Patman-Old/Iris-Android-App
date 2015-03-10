package com.iris.irisapp.db;

import android.os.AsyncTask;
import android.util.Log;

import com.iris.irisapp.feed.NewsArticle;
import com.iris.irisapp.feed.NewsHeadline;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 21/02/2015.
 */
public class DbAccessor
{
    public List<Integer> GetNextArticleIds(int categoryId, int currentArticleIndex)
    {
        try {
            final String GET_PROCESSED_ARTICLES_IDS_URL = "http://91.212.182.221/~jackpatm/get_processed_article_ids.php?lastarticleindex=" + currentArticleIndex + "&categoryid=" + categoryId;

            ArticleIdAccessor accessor = new ArticleIdAccessor();
            List<Integer> articleIds = (List<Integer>) accessor.execute(new Object[]{GET_PROCESSED_ARTICLES_IDS_URL}).get();

            return articleIds;
        }
        catch (Exception e)
        {
            Log.e("IRIS EXCEPTION", "Exception encountered attempting to load list of ArticleIds, exception was"+e);
        }

        return null;
    }

    public NewsHeadline LoadArticle(int articleId)
    {
        try
        {
            final String GET_PROCESSED_ARTICLES_URL = "http://91.212.182.221/~jackpatm/load_processed_article.php?requestedarticleid=" + articleId;

            ArticleAccessor accessor = new ArticleAccessor();
            NewsHeadline headline = (NewsHeadline) accessor.execute(new Object[]{GET_PROCESSED_ARTICLES_URL}).get();

            return headline;
        }
        catch (Exception e)
        {
            Log.e("IRIS EXCEPTION", "Exception encountered attempting to load Article, exception was"+e);
        }

        return null;
    }
}
