package com.iris.irisapp.activity.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iris.irisapp.R;
import com.iris.irisapp.feed.NewsOutlet;

import java.util.List;

public class OutletListAdapter extends ArrayAdapter {

    List<NewsOutlet> outlets;
    Context context;

    public OutletListAdapter(Context context, List<NewsOutlet> outlets) {
        super(context, android.R.layout.select_dialog_singlechoice, outlets);
        this.context = context;
        this.outlets = outlets;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.outletsrow, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.txtOutlet);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageOutletIcon);
        textView.setText(outlets.get(position).getOutletActual());
        imageView.setImageResource(outlets.get(position).getOutletIconId());


        return rowView;
    }
}