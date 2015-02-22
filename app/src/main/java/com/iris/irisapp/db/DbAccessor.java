package com.iris.irisapp.db;

import android.os.AsyncTask;

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
public class DbAccessor extends AsyncTask
{

    @Override
    protected Object doInBackground(Object[] params)
    {
        List<ProcessedArticle> articles = new ArrayList<>();

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
                    article.setHeadline(parsedArticle.getString("Headline"));
                    article.setUrl(parsedArticle.getString("Url"));
                    article.setCategoryId(parsedArticle.getInt("CategoryId"));

                    String date = parsedArticle.getString("PublicationDate");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
                    article.setPublicationDate(dateFormatter.parse(date));

                    articles.add(article);
                }
            }


            in.close();
        }
        catch (Exception e)
        {
        }

        return articles;
    }
}
