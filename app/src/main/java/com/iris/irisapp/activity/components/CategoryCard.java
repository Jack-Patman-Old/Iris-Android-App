package com.iris.irisapp.activity.components;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.iris.irisapp.feed.NewsCategory;

/**
 * Created by Jack on 13/03/2015.
 */
public class CategoryCard
{
    private Context context;
    private TextView newsCategoryCard;
    private NewsCategory currentCategory;
    private HeadlinesList headlinesList;
    private final int WORLD_NEWS_CATEGORY_ID = 2;

    public CategoryCard(Context context, TextView newsCategoryCard, HeadlinesList headlinesList)
    {
        this.context = context;
        this.newsCategoryCard = newsCategoryCard;
        this.headlinesList = headlinesList;

        loadCategory(WORLD_NEWS_CATEGORY_ID);
    }

    public void loadCategory(int categoryId)
    {
        NewsCategory category = NewsCategory.getCategoryById(categoryId);

        if (newsCategoryCard != null && category != null && category.getCategoryId() != 0)
        {
            currentCategory = category;
            newsCategoryCard.setBackgroundColor(Color.parseColor(category.getCategoryColour()));
            newsCategoryCard.setText(category.getCategoryName());
            headlinesList.loadHeadlines(categoryId, 0, false);
        }
    }

    public TextView getNewsCategoryCard() {
        return newsCategoryCard;
    }

    public void setNewsCategoryCard(TextView newsCategoryCard) {
        this.newsCategoryCard = newsCategoryCard;
    }

    public NewsCategory getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(NewsCategory currentCategory) {
        this.currentCategory = currentCategory;
    }

    public HeadlinesList getHeadlinesList() {
        return headlinesList;
    }

    public void setHeadlinesList(HeadlinesList headlinesList) {
        this.headlinesList = headlinesList;
    }
}
