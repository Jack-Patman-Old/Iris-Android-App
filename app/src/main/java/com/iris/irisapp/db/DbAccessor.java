package com.iris.irisapp.db;

import android.util.Log;

import com.iris.irisapp.feed.NewsArticle;
import com.iris.irisapp.feed.NewsHeadline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 21/02/2015.
 */
public class DbAccessor
{
    public List<NewsHeadline> loadArticles(int categoryId, int rowNumber) {
        List<Integer> articleIds = getNextArticleIds(categoryId, rowNumber);
        List<NewsHeadline> headlines = getArticlesById(categoryId, articleIds);

        return headlines;
    }

    public List<Integer> getNextArticleIds(int categoryId, int rowNumber) {
        try {
            final String GET_PROCESSED_ARTICLES_IDS_URL = "http://91.212.182.221/~jackpatm/get_processed_article_ids.php?lastarticleindex=" + rowNumber + "&categoryid=" + categoryId;

            ArticleIdAccessor accessor = new ArticleIdAccessor();
            List<Integer> articleIds = (List<Integer>) accessor.execute(new Object[]{GET_PROCESSED_ARTICLES_IDS_URL}).get();

            return articleIds;
        } catch (Exception e) {
            Log.e("IRIS EXCEPTION", "Exception encountered attempting to load list of ArticleIds, exception was" + e);
        }

        return null;
    }

    private List<NewsHeadline> getArticlesById(int categoryId, List<Integer> articleIds) {
        List<NewsHeadline> headlines = new ArrayList<>();

        for (int articleId : articleIds) {
            try {
                final String GET_PROCESSED_ARTICLES_URL = "http://91.212.182.221/~jackpatm/load_processed_article.php?requestedarticleid=" + articleId;

                ArticleAccessor accessor = new ArticleAccessor();
                NewsHeadline headline = (NewsHeadline) accessor.execute(new Object[]{GET_PROCESSED_ARTICLES_URL}).get();
                headline.setHeadlineId(articleId);
                headline.setCategoryId(categoryId);
                headlines.add(headline);

            } catch (Exception e) {
                Log.e("IRIS EXCEPTION", "Exception encountered attempting to load Article, exception was" + e);
            }
        }

        return headlines;
    }
}
