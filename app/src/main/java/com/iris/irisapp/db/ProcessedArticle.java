package com.iris.irisapp.db;

import java.util.Date;

/**
 * Created by Jack on 21/02/2015.
 */
public class ProcessedArticle
{
    private int id;
    private String headline;
    private Date publicationDate;
    private String url;
    private int categoryId;

    public ProcessedArticle()
    {

    }
    public ProcessedArticle(int id, String headline, Date publicationDate, String url, int categoryId)
    {
        this.id = id;
        this.headline = headline;
        this.publicationDate = publicationDate;
        this.url = url;
        this.categoryId = categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getUrl() {
        return url;
    }

    public int getCategoryId() {
        return categoryId;
    }


}
