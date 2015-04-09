package com.iris.irisapp.activity.components;

import android.R;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iris.irisapp.db.DbAccessor;
import com.iris.irisapp.feed.NewsHeadline;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesList implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private Context context;
    private ListView headlinesListView;
    private List<NewsHeadline> loadedHeadlines;
    private int preLastArticleIndex;

    public HeadlinesList(Context context, ListView headlineView) {
        this.context = context;
        this.loadedHeadlines = new ArrayList<>();
        this.headlinesListView = headlineView;
        this.headlinesListView.setOnItemClickListener(this);
        this.headlinesListView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        switch (view.getId()) {
            case R.id.list:
                /* Taken from SO - http://stackoverflow.com/questions/5123675/find-out-if-listview-is-scrolled-to-the-bottom
                 Code to determine when the ListView has been scorlled to the bottom - requests more articles to load
                 */

                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLastArticleIndex != lastItem) {
                        preLastArticleIndex = lastItem;
                    }


                    if (loadedHeadlines != null && loadedHeadlines.size() >0)
                    {
                        NewsHeadline lastHeadline = loadedHeadlines.get(loadedHeadlines.size()-1);
                        loadHeadlines(lastHeadline.getCategoryId(), loadedHeadlines.size(), true);
                    }
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (loadedHeadlines != null & loadedHeadlines.size() > 0) {
            NewsHeadline headline = loadedHeadlines.get(position);
            if (headline != null) {
                OutletsList outletsPrompt = new OutletsList(context, headline);
                outletsPrompt.displayHeadlineOutletPrompt(headline);
            }
        }
    }

    public void loadHeadlines(int categoryId, int rowNumber, boolean appendToLoadedHeadlines)
    {
        DbAccessor db = new DbAccessor();

        if (appendToLoadedHeadlines)
        {
            List<NewsHeadline> headlines = db.loadArticles(categoryId, rowNumber);
            if (headlines != null && headlines.size() >0) {
                this.loadedHeadlines.addAll(headlines);
            }
        }
        else
        {
            this.loadedHeadlines = new ArrayList<>(db.loadArticles(categoryId, rowNumber));
        }

        List<String> headlineText = new ArrayList<>();

        for (NewsHeadline story: loadedHeadlines)
        {
            if (story.getHeadline() != null) {
                headlineText.add(story.getHeadline());
            }
        }

        if (headlineText.size() > 0)
        {
            if (headlinesListView.getAdapter() == null) {
                headlinesListView.setAdapter(new HeadlineListAdapter(context, R.layout.simple_list_item_1, headlineText));
            }
            else
            {
                ((HeadlineListAdapter)headlinesListView.getAdapter()).refill(headlineText);
            }
        }
        else
        {
            headlineText.add(":( No news today. Sorry!");
            headlinesListView.setAdapter(new HeadlineListAdapter(context, R.layout.simple_list_item_1, headlineText));
        }
    }

    public ListView getHeadlinesListView() {
        return headlinesListView;
    }

    public List<NewsHeadline> getLoadedHeadlines() {
        return loadedHeadlines;
    }

    public void setLoadedHeadlines(List<NewsHeadline> loadedHeadlines) {
        this.loadedHeadlines = loadedHeadlines;
    }
}
