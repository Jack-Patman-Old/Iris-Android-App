package com.iris.irisapp.activity.components;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.iris.irisapp.activity.MainActivity;

/**
 * Created by Jack on 13/03/2015.
 */
public class SwipeContainer implements SwipeRefreshLayout.OnRefreshListener
{
    private MainActivity mainActivity;
    private CategoryCard category;
    private SwipeRefreshLayout swipeLayout;

    public SwipeContainer(MainActivity mainActivity, SwipeRefreshLayout swipeLayout, CategoryCard category)
    {
        this.mainActivity = mainActivity;
        this.category = category;
        this.swipeLayout = swipeLayout;

        this.swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh()
    {
        category.getHeadlinesList().loadHeadlines(category.getCurrentCategory().getCategoryId(), 0, false);
        swipeLayout.setRefreshing(false);
    }
}
