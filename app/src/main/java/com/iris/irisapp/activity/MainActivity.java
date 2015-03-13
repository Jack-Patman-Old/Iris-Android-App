package com.iris.irisapp.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.iris.irisapp.R;
import com.iris.irisapp.activity.components.CategoryCard;
import com.iris.irisapp.activity.components.HeadlinesList;


public class MainActivity extends ListActivity {
    private Button btnPreviousCategory;
    private Button btnNextCategory;

    private CategoryCard category;
    private HeadlinesList headlines;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final int WORLD_NEWS_CATEGORY_ID = 2;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headlines = new HeadlinesList(this, (ListView) findViewById(android.R.id.list));
        category= new CategoryCard(this, (TextView) findViewById(R.id.txtCategory), headlines);

        // Add button listener for previous category selection
        btnPreviousCategory = (Button) findViewById(R.id.btnPrev);
        btnPreviousCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int prevCategoryId = category.getCurrentCategory().getCategoryId()-1;
                category.loadCategory(prevCategoryId);
            }
        });

        // Add button listener for next category selection
        btnNextCategory = (Button) findViewById(R.id.btnNext);
        btnNextCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int nextCategoryId = category.getCurrentCategory().getCategoryId()+1;
                category.loadCategory(nextCategoryId);
            }
        });

        category.loadCategory(WORLD_NEWS_CATEGORY_ID);
    }
}
