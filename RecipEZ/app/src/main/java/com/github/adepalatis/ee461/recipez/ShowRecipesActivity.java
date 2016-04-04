package com.github.adepalatis.ee461.recipez;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

/**
 * Created by Tony on 3/27/2016.
 */
public class ShowRecipesActivity extends AppCompatActivity {

    private ExpandableListView recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipes);

        // Initialize list of search results
        recipes = (ExpandableListView)findViewById(R.id.expandableListView);
    }

}
