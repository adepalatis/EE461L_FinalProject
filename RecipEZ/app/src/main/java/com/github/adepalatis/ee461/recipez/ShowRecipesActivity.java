package com.github.adepalatis.ee461.recipez;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.GridLayout;

import java.util.List;

/**
 * Created by Tony on 3/27/2016.
 */
public class ShowRecipesActivity extends AppCompatActivity {

    GridLayout recipes = (GridLayout) findViewById(R.id.show_recipes);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipes);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            RecipeSearchParameters rsp = data.getParcelable("RSP");
            List<RecipeSearchResult> rsr;

            FoodAPI api = FoodAPI.getInstance();
            if (rsp.getQuery() != null) { // Use query recipe search
                try {
                    rsr = api.searchRecipes(rsp.getCuisine(), rsp.getDiet(),
                            rsp.getExcludeIngredients(), rsp.getIntolerance(), rsp.isLimitLicense(),
                            rsp.getMaxNumber(), rsp.getOffset(), rsp.getQuery(), rsp.getType());
                } catch(Exception e) {
                    System.err.println(e);
                }
            } else {
                try {
                    rsr = api.searchRecipes(rsp.isIngredientLists(), rsp.getIngredients(),
                            rsp.isLimitLicense(), rsp.getMaxNumber(), rsp.getRanking());
                } catch(Exception e) {
                    System.err.println(e);
                }
            }

            // TODO: Create rsr.size() number of ImageViews as children of GridLayout recipes;
            // TODO: Set image of each ImageView with rsr.get(#).image
            // TODO: Set onClick of each ImageView to transition with Intent to new activity
        }
    }

}
