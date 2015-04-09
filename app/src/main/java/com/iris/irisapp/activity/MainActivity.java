package com.iris.irisapp.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.iris.irisapp.R;
import com.iris.irisapp.activity.components.CategoryCard;
import com.iris.irisapp.activity.components.HeadlinesList;
import com.iris.irisapp.activity.components.SwipeContainer;


public class MainActivity extends ListActivity {
    private Button btnPreviousCategory;
    private Button btnNextCategory;

    private CategoryCard category;
    private HeadlinesList headlines;
    private SwipeContainer swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headlines = new HeadlinesList(this, (ListView) findViewById(android.R.id.list));
        category= new CategoryCard(this, (TextView) findViewById(R.id.txtCategory), headlines);
        swipeContainer = new SwipeContainer(this, (SwipeRefreshLayout) findViewById(R.id.swipeContainer), category);

        // Add button listener for previous category selection
        btnPreviousCategory = (Button) findViewById(R.id.btnPrev);
        btnPreviousCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int prevCategoryId = category.getCurrentCategory().getCategoryId()-1;
                category.loadCategory( category.getCurrentCategory().getCategoryId(), prevCategoryId);
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
                category.loadCategory( category.getCurrentCategory().getCategoryId(), nextCategoryId);
            }
        });

    }
}
