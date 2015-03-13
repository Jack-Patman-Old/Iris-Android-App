package com.iris.irisapp.activity.components;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Jack on 13/03/2015.
 */
public class HeadlineListAdapter extends ArrayAdapter {

    public HeadlineListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    /* Credit Sean Schulte for the elegant refresh solution,
    http://vikinghammer.com/2011/06/17/android-listview-maintain-your-scroll-position-when-you-refresh/     */
    public void refill(List<String> headlines)
    {
        super.clear();
        super.addAll(headlines);
        notifyDataSetChanged();
    }
}
