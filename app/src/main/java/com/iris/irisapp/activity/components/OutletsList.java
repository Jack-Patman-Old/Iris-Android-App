package com.iris.irisapp.activity.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;


import com.iris.irisapp.R;
import com.iris.irisapp.feed.NewsHeadline;
import com.iris.irisapp.feed.NewsOutlet;

import java.util.ArrayList;
import java.util.List;

public class OutletsList
{

    private Context context;
    private OutletListAdapter adapter;

    public OutletsList(Context context, NewsHeadline headline)
    {
        adapter = new OutletListAdapter(context,  headline.getNewsOutlets());
        this.context = context;

    }


    public void displayHeadlineOutletPrompt(final NewsHeadline headline) {
        List<NewsOutlet> outlets = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.news_outlet_prompt_title);

        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setAdapter(adapter,
                new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (headline != null) {
                            String url = sanitizeUrl(headline.getArticles().get(which).getArticleUrl());
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            context.startActivity(browserIntent);
                        }
                    }
                });

        builder.show();
    }

    // Method for removing String formatting from Urls
    private String sanitizeUrl(String url) {
        String sanitizedUrl = url;
        sanitizedUrl = sanitizedUrl.replace("\n", "");
        sanitizedUrl = sanitizedUrl.replace("\t", "");

        return sanitizedUrl;
    }
}
