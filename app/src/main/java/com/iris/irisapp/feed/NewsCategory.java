package com.iris.irisapp.feed;

import java.util.HashMap;
import java.util.Map;

public enum NewsCategory
{
    NULL_CATEGORY(0, "Placeholder Category", "#000000"),
    WORLD_NEWS(2, "Global News", "#E64C4C"),
    BUSINESS(3, "Business", "#C962DE"),
    TECHNOLOGY(4, "Technology", "#00B5FF"),
    UK_NEWS(5, "UK News", "#57DEA8");

    private final int categoryId;
    private final String categoryName;
    private final String categoryColour;

    private static final Map categoryIdLookup = new HashMap();
    private static final Map categoryNameLookup = new HashMap();

    static
    {
        //Create reverse lookup hash map
        for(NewsCategory category : NewsCategory.values())
        {
            categoryIdLookup.put(category.getCategoryId(), category);
        }

        for(NewsCategory category : NewsCategory.values())
        {
            categoryNameLookup.put(category.getCategoryName(), category);
        }
    }

    private NewsCategory(int categoryId, String category, String categoryColour)
    {
        this.categoryId = categoryId;
        this.categoryName = category;
        this.categoryColour = categoryColour;
    }

    public int getCategoryId() {return categoryId; }

    public String getCategoryName()
    {
        return categoryName;
    }

    public String getCategoryColour()
    {
        return categoryColour;
    }

    public static NewsCategory getCategoryByName(String category)
    {
        return (NewsCategory) categoryNameLookup.get(category);
    }


    public static NewsCategory getCategoryById(int categoryId)
    {
        return (NewsCategory) categoryIdLookup.get(categoryId);
    }
}
