package com.iris.irisapp.activity;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.iris.irisapp.R;
import com.iris.irisapp.activity.components.HeadlinesList;
import com.iris.irisapp.db.DbAccessor;
import com.iris.irisapp.feed.NewsCategory;
import com.iris.irisapp.feed.NewsHeadline;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {
    private NewsCategory currentCategory;
    private Button btnPreviousCategory;
    private Button btnNextCategory;
    private TextView newsCategoryCard;
    private HeadlinesList headlinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final int WORLD_NEWS_CATEGORY_ID = 2;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start by loading the world news category initially
        newsCategoryCard = (TextView) findViewById(R.id.txtCategory);


        ListView headlineView = (ListView) findViewById(android.R.id.list);
        headlinesList = new HeadlinesList(this, headlineView);


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

            headlinesList.setLoadedHeadlines(getCategoryArticles(categoryId, 0));

            for (NewsHeadline story: headlinesList.getLoadedHeadlines())
            {
                if (story.getHeadline() != null) {
                    headlines.add(story.getHeadline());
                }
            }

            String[] headlinesArr = new String[headlines.size()];
            headlinesArr = headlines.toArray(headlinesArr);
            if (headlinesArr.length > 0)
            {
                headlinesList.getHeadlinesList().setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, headlinesArr));
            }
            else
            {
                headlinesList.getHeadlinesList().setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{":( No news today. Sorry!"}));
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
