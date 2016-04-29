package com.github.adepalatis.ee461.recipez;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by michael on 4/29/16.
 */
public class RecipeSearchResultListViewAdapter extends BaseAdapter {

    private final Context c;
    private List<RecipeSearchResult> r;
    private static LayoutInflater i = null;

    public class ListElement {
        ImageView img;
        TextView title;
    }

    public RecipeSearchResultListViewAdapter(Activity a, List<RecipeSearchResult> r) {
        this.c = a;
        this.r = r;
        i = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return r.size();
    }

    @Override
    public RecipeSearchResult getItem(int index) {
        return r.get(index);
    }

    @Override
    public long getItemId(int index) {
        return getItem(index).id;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {
        ListElement e = new ListElement();
        View row = i.inflate(R.layout.recipe_list, null);
        e.img = (ImageView) row.findViewById(R.id.recipeImage);
        e.title = (TextView) row.findViewById(R.id.recipeTitle);

        try {
            String url = getItem(index).image.contains("https") ? getItem(index).image : "https://spoonacular.com/recipeImages/" + getItem(index).image;
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, getItem(index).title);

            e.img.setImageDrawable(d);
            e.title.setText(getItem(index).title);
        } catch (Exception e1) {

        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShowRecipesActivity) c).transitionToNext(getItem(index));
            }
        });

        return row;
    }
}
