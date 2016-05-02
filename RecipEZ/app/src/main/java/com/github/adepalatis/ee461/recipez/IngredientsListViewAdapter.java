package com.github.adepalatis.ee461.recipez;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * Created by michael on 5/1/16.
 */
public class IngredientsListViewAdapter extends BaseAdapter {

    private List<String> ingredients;
    private LayoutInflater li;

    public static class Element {
        TextView t;
    }

    public IngredientsListViewAdapter(List<String> i) {
        this.ingredients = i;
    }

    public void setInflater(Activity a) {
        li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newView();
        }

        Element e = (Element) convertView.getTag();
        e.t.setText((String) getItem(position));

        return convertView;
    }

    private View newView() {
        View v = li.inflate(R.layout.ingredient_list, null);

        Element e = new Element();
        e.t = (TextView) v.findViewById(R.id.ingredient);

        v.setTag(e);
        return v;
    }
}
