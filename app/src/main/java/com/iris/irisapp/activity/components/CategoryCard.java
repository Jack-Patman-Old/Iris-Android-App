package com.iris.irisapp.activity.components;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.iris.irisapp.feed.NewsCategory;

import static android.graphics.Color.parseColor;

/**
 * Created by Jack on 13/03/2015.
 */
public class CategoryCard {
    private final int WORLD_NEWS_CATEGORY_ID = 2;
    private Context context;
    private TextView newsCategoryCard;
    private NewsCategory currentCategory;
    private HeadlinesList headlinesList;

    public CategoryCard(Context context, TextView newsCategoryCard, HeadlinesList headlinesList) {
        this.context = context;
        this.newsCategoryCard = newsCategoryCard;
        this.headlinesList = headlinesList;

        loadCategory(WORLD_NEWS_CATEGORY_ID, WORLD_NEWS_CATEGORY_ID);
    }

    public void loadCategory(int currentCategoryId, int newCategoryId) {
        NewsCategory category = NewsCategory.getCategoryById(newCategoryId);

        if (newsCategoryCard != null && category != null && category.getCategoryId() >= 2) {

            Integer colorFrom = parseColor(NewsCategory.getCategoryById(currentCategoryId).getCategoryColour());
            Integer colorTo = parseColor(category.getCategoryColour());
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    newsCategoryCard.setBackgroundColor((Integer)animation.getAnimatedValue());
                }
            });

            colorAnimation.start();

            currentCategory = category;
            newsCategoryCard.setText(category.getCategoryName());
            headlinesList.loadHeadlines(newCategoryId, 0, false);
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
