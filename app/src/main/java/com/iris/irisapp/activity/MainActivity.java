package com.iris.irisapp.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ListActivity {
    private NewsCategory currentCategory;
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
        headlinesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                //args2 is the listViews Selected index
                // Load Headline<args2>;
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

    private void loadCategory(int categoryId)
    {
        NewsCategory category = NewsCategory.getCategoryById(categoryId);
        List<String> headlines =  new ArrayList<>();

        if (newsCategoryCard != null && category != null && category.getCategoryId() != 0)
        {
            currentCategory = category;
            newsCategoryCard.setBackgroundColor(Color.parseColor(category.getCategoryColour()));
            newsCategoryCard.setText(category.getCategoryName());

            List<NewsHeadline> stories = getCategoryArticles(categoryId, 0);

            for (NewsHeadline story: stories)
            {
                headlines.add(story.getHeadline());
            }

            String[] headlinesArr = new String[headlines.size()];
            headlinesArr = headlines.toArray(headlinesArr);

            headlinesList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, headlinesArr));
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
