package com.github.adepalatis.ee461.recipez;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tony on 4/24/2016.
 */

public class TextViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CharSequence> textViewValues;

    public TextViewAdapter(Context context, ArrayList<CharSequence> textViewValues) {
        this.context = context;
        this.textViewValues = textViewValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.ingredient, null);

            // set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(textViewValues.get(position));
        } else {
            gridView = convertView;
        }

        gridView.setBackgroundColor(Color.RED);
        return gridView;
    }

    @Override
    public int getCount() {
        return textViewValues.size();
    }

    @Override
    public Object getItem(int position) {
        return textViewValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
