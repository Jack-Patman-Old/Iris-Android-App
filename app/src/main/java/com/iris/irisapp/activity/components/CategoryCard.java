package com.iris.irisapp.activity.components;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.iris.irisapp.feed.NewsCategory;


public class CategoryCard
{
    private Context context;
    private TextView newsCategoryCard;
    private NewsCategory currentCategory;
    private HeadlinesList headlinesList;

    public CategoryCard(Context context, TextView newsCategoryCard, HeadlinesList headlinesList)
    {
        this.context = context;
        this.newsCategoryCard = newsCategoryCard;
        this.headlinesList = headlinesList;
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
