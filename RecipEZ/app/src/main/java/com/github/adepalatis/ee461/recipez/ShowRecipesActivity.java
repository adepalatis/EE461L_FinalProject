package com.github.adepalatis.ee461.recipez;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        recipeList = (ListView) findViewById(R.id.showRecipes);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            RecipeSearchParameters rsp = data.getParcelable("RSP");
            List<RecipeSearchResult> rsr;
            FoodAPI api = FoodAPI.getInstance();

            if (rsp != null) {
                if (!rsp.getQuery().equals("")) { // Use query recipe search
                    try {
                        rsr = api.searchRecipes(rsp.getCuisine(), rsp.getDiet(),
                                rsp.getExcludeIngredients(), rsp.getIntolerance(), rsp.isLimitLicense(),
                                rsp.getMaxNumber(), rsp.getOffset(), rsp.getQuery(), rsp.getType());
                        recipeList.setAdapter(new RecipeSearchResultListViewAdapter(this, rsr));
                    } catch (Exception e) {
                        Log.d("Error", e.toString());
                    }
                } else {
                    try {
                        rsr = api.searchRecipes(rsp.isIngredientLists(), rsp.getIngredients(),
                                rsp.isLimitLicense(), rsp.getMaxNumber(), rsp.getRanking());
                        recipeList.setAdapter(new RecipeSearchResultListViewAdapter(this, rsr));
                    } catch (Exception e) {
                        Log.d("Error", e.toString());
                    }
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    public void transitionToNext(RecipeSearchResult r) {
        Intent i = new Intent(this, ShowSingleRecipeActivity.class);
        i.putExtra("recipeId", r.id);
        i.putParcelableArrayListExtra("missingIngredients", new ArrayList<>(Arrays.asList(r.missedIngredients)));
        i.putParcelableArrayListExtra("usedIngredients", new ArrayList<>(Arrays.asList(r.usedIngredients)));
        startActivity(i);
    }

}
