package com.iris.irisapp.db;

import android.os.AsyncTask;

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
 * Created by Jack on 10/03/2015.
 */
public class ArticleAccessor extends AsyncTask
{

    @Override
    protected NewsHeadline doInBackground(Object[] params)
    {
        NewsHeadline headline = new NewsHeadline();

        try {
            URL articlesScript = new URL(params[0].toString());
            URLConnection yc = articlesScript.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = in.readLine()) != null)
            {
                sb.append(line + "\n");
            }

            String[] jsonArticles = sb.toString().split("(?<=\\})");

            JSONObject object = new JSONObject(sb.toString());

            if (jsonArticles != null && jsonArticles.length > 0)
            {
                for (String jsonArticle : jsonArticles)
                {
                    if (jsonArticle == null || jsonArticle.isEmpty() || jsonArticle.equals("\n"))
                    {
                        continue;
                    }

                    JSONObject parsedArticle = new JSONObject(jsonArticle);
                    ProcessedArticle article = new ProcessedArticle();

                    if (headline.getHeadline() == null)
                    {
                        headline.setHeadline(parsedArticle.getString("Headline"));
                    }

                    if (headline.getPublicationDate() == null)
                    {
                        String date = parsedArticle.getString("PublicationDate");
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
                        headline.setPublicationDate(dateFormatter.parse(date));
                    }

                    int outletId = parsedArticle.getInt("OutletId");
                    String url = parsedArticle.getString("Url");
                    headline.addOutlet(new NewsArticle(outletId, url));
                }
            }


            in.close();
        }
        catch (Exception e)
        {
        }

        return headline;
    }
}
