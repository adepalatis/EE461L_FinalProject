package com.github.adepalatis.ee461.recipez;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 5/1/16.
 */
public class IngredientsExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Activity a;
    private List<List<String>> ingredients;
    private List<String> groups, elements;
    private LayoutInflater l;

    public IngredientsExpandableListViewAdapter(Activity a, List<List<String>> ingredients, List<String> groups) {
        this.a = a;
        this.ingredients = ingredients;
        this.groups = groups;
        l = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return elements.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        elements = ingredients.get(groupPosition);

        if (convertView == null) {
            convertView = newChildView();
        }
        return null;
    }

    private View newChildView() {
        View v = l.inflate(R.layout.ingredient_group, null);
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
