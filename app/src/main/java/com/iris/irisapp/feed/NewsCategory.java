package com.iris.irisapp.feed;

public enum NewsCategory
{
    WORLD_NEWS("Global News", "ff4444"),
    UK_NEWS("UK News", "85ff00"),
    TECHNOLOGY("Technology", "00B5FF");

    private final String categoryName;
    private final String cardColour;

    private NewsCategory(String category, String cardColour)
    {
        this.categoryName = category;
        this.cardColour = cardColour;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public String getCardColour()
    {
        return cardColour;
    }

    public NewsCategory getCategory(String category)
    {
        switch(category)
        {
            case("Global News"): return NewsCategory.WORLD_NEWS;
            case("UK News"): return NewsCategory.UK_NEWS;
            case("Technology"): return NewsCategory.TECHNOLOGY;

            default: return null;
        }
    }
}
