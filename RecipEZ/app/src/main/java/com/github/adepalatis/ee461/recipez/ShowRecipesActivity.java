package com.github.adepalatis.ee461.recipez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Tony on 3/27/2016.
 */
public class ShowRecipesActivity extends AppCompatActivity {

    ListView recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipes);

        recipeList = (ListView) findViewById(R.id.showRecipes);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            RecipeSearchParameters rsp = data.getParcelable("RSP");
            List<RecipeSearchResult> rsr = null;

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

            recipeList.setAdapter(new RecipeSearchResultListViewAdapter(this, rsr));

            // TODO: Set onClick of each ImageView to transition with Intent to new activity
        }
    }

    public void transitionToNext(RecipeSearchResult r) {
        Intent i = new Intent(this, ShowSingleRecipeActivity.class);
        i.putExtra("recipeId", r.id);
        startActivity(i);
    }

}
