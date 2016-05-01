package com.github.adepalatis.ee461.recipez;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recipeList = (ListView) findViewById(R.id.showRecipes);
        Log.d("thing", getIntent().toString());
        Bundle data = getIntent().getExtras();
        if (data != null) {
            RecipeSearchParameters rsp = data.getParcelable("RSP");
            List<RecipeSearchResult> rsr;
            FoodAPI api = FoodAPI.getInstance();
            if (!rsp.getQuery().equals("")) { // Use query recipe search
                try {
                    rsr = api.searchRecipes(rsp.getCuisine(), rsp.getDiet(),
                            rsp.getExcludeIngredients(), rsp.getIntolerance(), rsp.isLimitLicense(),
                            5, rsp.getOffset(), rsp.getQuery(), rsp.getType());
                    recipeList.setAdapter(new RecipeSearchResultListViewAdapter(this, rsr));
                    Log.d("goodbye", "" + rsr.size());
                } catch(Exception e) {
                    Log.d("shit", e.toString());
                }
            } else {
                try {
                    rsr = api.searchRecipes(rsp.isIngredientLists(), rsp.getIngredients(),
                            rsp.isLimitLicense(), 5, rsp.getRanking());
                    recipeList.setAdapter(new RecipeSearchResultListViewAdapter(this, rsr));
                    Log.d("Hello", "" + rsr.size());
                } catch(Exception e) {
                    Log.d("sit", e.toString());
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
        startActivity(i);
    }

}
