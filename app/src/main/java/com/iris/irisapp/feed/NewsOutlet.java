package com.iris.irisapp.feed;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.iris.irisapp.R;

import java.util.HashMap;
import java.util.Map;

public enum NewsOutlet
{
    BBC(1, "BBC", "BBC News", R.drawable.bbc_news_icon),
    CNN(2, "CNN", "CNN", R.drawable.cnn_news_icon),
    ITV(3, "ITV", "ITV News", R.drawable.itv_news_icon),
    TGN(4, "TGN", "Guardian", R.drawable.guardian_news_icon),
    SKY(5, "SKY", "Sky News", R.drawable.sky_news_icon),
    TLG(6, "TLG", "Telegraph", R.drawable.telegraph_news_icon),
    IND(7, "IND", "The Independent", R.drawable.independent_news_icon),
    STD(8, "STD", "London Evening Std.", R.drawable.eveningstandard_news_icon),
    TDM(9, "TDM", "Daily Mail", R.drawable.dailymail_news_icon);

    private final int outletId;
    private final String outletAcronym;
    private final String outletActual;
    private final int outletIconId;

    private static final Map outletIdLookup = new HashMap();
    private static final Map outletAcronymLookup = new HashMap();

    static
    {
        //Create reverse lookup hash map
        for(NewsOutlet outlet : NewsOutlet.values())
        {
            outletIdLookup.put(outlet.getOutletId(), outlet);
        }

        for(NewsOutlet outlet : NewsOutlet.values())
        {
            outletAcronymLookup.put(outlet.getOutletAcronym(), outlet);
        }
    }

    private NewsOutlet(int outletId, String outletAcronym, String outletActual, int outletIconId)
    {
        this.outletId = outletId;
        this.outletAcronym = outletAcronym;
        this.outletActual = outletActual;
        this.outletIconId = outletIconId;
    }


    public static NewsOutlet getOutletById(int outletId)
    {
        return (NewsOutlet) outletIdLookup.get(outletId);
    }


    public static NewsOutlet getOutletByAcronym(String acronym)
    {
        return (NewsOutlet) outletAcronymLookup.get(acronym);
    }

    public int getOutletId()
    {
        return outletId;
    }

    public String getOutletAcronym() {
        return outletAcronym;
    }

    public String getOutletActual() {
        return outletActual;
    }

    public int getOutletIconId() {
        return outletIconId;
    }

    public static Map getOutletIdLookup() {
        return outletIdLookup;
    }

    public static Map getOutletAcronymLookup() {
        return outletAcronymLookup;
    }
}
