package com.iris.irisapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.iris.irisapp.R;
import com.iris.irisapp.db.DbAccessor;
import com.iris.irisapp.db.ProcessedArticle;
import com.iris.irisapp.feed.NewsArticle;
import com.iris.irisapp.feed.NewsCategory;
import com.iris.irisapp.feed.NewsHeadline;
import com.iris.irisapp.feed.NewsOutlet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ListActivity {
    private NewsCategory currentCategory;
    private List<NewsHeadline> loadedHeadlines;
    Button btnPreviousCategory;
    Button btnNextCategory;
    TextView newsCategoryCard;
    ListView headlinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final int WORLD_NEWS_CATEGORY_ID = 2;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start by loading the world news category initially
        newsCategoryCard = (TextView) findViewById(R.id.txtCategory);


        headlinesList = (ListView) findViewById(android.R.id.list);
        headlinesList.setOnItemClickListener(
        new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int selectedIndex, long arg3)
            {
                //args2 is the listViews Selected index
                NewsHeadline headline = loadHeadline(selectedIndex);

                displayHeadlineOutlets(headline);
            }
        });

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

        loadCategory(WORLD_NEWS_CATEGORY_ID);
    }


    private NewsHeadline loadHeadline(int selectedIndex)
    {
        return (loadedHeadlines.get(selectedIndex));
    }


    private void displayHeadlineOutlets(final NewsHeadline headline)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Preferred Outlet");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        for (NewsArticle article: headline.getOutlets())
        {
            NewsOutlet outlet = NewsOutlet.getOutletById(article.getOutletId());

            if (outlet != null)
            {
                Drawable image = getResources().getDrawable(outlet.getOutletIconId());
                arrayAdapter.add(outlet.getOutletActual());
            }
        }

        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedHeadlineUrl = headline.getOutlets().get(which).getArticleUrl();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedHeadlineUrl));
                        startActivity(browserIntent);
                    }
                });
        builder.show();
    }

    private void loadCategory(int categoryId)
    {
        NewsCategory category = NewsCategory.getCategoryById(categoryId);
        List<String> headlines =  new ArrayList<>();

        if (newsCategoryCard != null && category != null && category.getCategoryId() != 0)
        {
            currentCategory = category;
            newsCategoryCard.setBackgroundColor(Color.parseColor(category.getCategoryColour()));
            newsCategoryCard.setText(category.getCategoryName());

            loadedHeadlines = getCategoryArticles(categoryId, 0);

            for (NewsHeadline story: loadedHeadlines)
            {
                if (story.getHeadline() != null) {
                    headlines.add(story.getHeadline());
                }
            }

            String[] headlinesArr = new String[headlines.size()];
            headlinesArr = headlines.toArray(headlinesArr);
            if (headlinesArr.length > 0)
            {
                headlinesList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, headlinesArr));
            }
            else
            {
                headlinesList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{":( No news today. Sorry!"}));
            }
        }
    }


    private List<NewsHeadline> getCategoryArticles(int categoryId, int lastIndice)
    {

        DbAccessor db = new DbAccessor();

        List<Integer> articleIds = db.GetNextArticleIds(categoryId, lastIndice);
        List<NewsHeadline> headlines = new ArrayList<>();
        for(int indice: articleIds)
        {
            headlines.add(db.LoadArticle(indice));
        }

        return headlines;
    }

}
