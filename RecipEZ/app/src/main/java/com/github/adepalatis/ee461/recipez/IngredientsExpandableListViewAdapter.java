package com.github.adepalatis.ee461.recipez;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = newGroupView();
        }

        CheckedTextView t = (CheckedTextView) convertView.getTag();
        t.setText(groups.get(groupPosition));
        t.setChecked(isExpanded);

        return convertView;
    }

    private View newGroupView() {
        View v = l.inflate(R.layout.ingredient_group, null);
        CheckedTextView t = (CheckedTextView) v.findViewById(R.id.ingredientGroup);
        v.setTag(t);
        return v;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        elements = ingredients.get(groupPosition);

        if (convertView == null) {
            convertView = newChildView();
        }

        TextView t = (TextView) convertView.getTag();
        t.setText(elements.get(childPosition));

        final int gp = groupPosition;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ELV", "clicked: " + groups.get(gp));
                Toast.makeText(a, ingredients.get(gp).get(childPosition), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private View newChildView() {
        View v = l.inflate(R.layout.ingredient_group_element, null);
        TextView t = (TextView) v.findViewById(R.id.ingredient);
        v.setTag(t);
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
