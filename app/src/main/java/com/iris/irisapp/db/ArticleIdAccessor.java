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
 * Created by Jack on 10/03/2015.
 */
public class ArticleIdAccessor extends AsyncTask
{
    @Override
    protected List<Integer> doInBackground(Object[] params)
    {
        List<Integer> articleIds = new ArrayList<>();

        try
        {
            URL articlesScript = new URL(params[0].toString());
            URLConnection yc = articlesScript.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = in.readLine()) != null)
            {
                sb.append(line + "\n");
            }

            String[] jsonArticleIds = sb.toString().split("(?<=\\})");

            JSONObject object = new JSONObject(sb.toString());

            if (jsonArticleIds != null && jsonArticleIds.length > 0)
            {
                for (String jsonArticleId : jsonArticleIds)
                {
                    if (jsonArticleId == null || jsonArticleId.isEmpty() || jsonArticleId.equals("\n"))
                    {
                        continue;
                    }

                    JSONObject parsedArticleId = new JSONObject(jsonArticleId);

                    articleIds.add(parsedArticleId.getInt("Id"));
                }
            }


            in.close();
        }
        catch (Exception e)
        {
        }

        return articleIds;
    }
}
