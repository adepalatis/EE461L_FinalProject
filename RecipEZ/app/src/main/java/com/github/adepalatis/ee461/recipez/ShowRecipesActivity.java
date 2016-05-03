package com.github.adepalatis.ee461.recipez;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.ListView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Tony on 3/27/2016.
 */
public class ShowRecipesActivity extends AppCompatActivity {

    ListView recipeList;
    ProgressDialog progress;
    Activity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipes);

        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recipeList = (ListView) findViewById(R.id.showRecipes);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            final RecipeSearchParameters rsp = data.getParcelable("RSP");
            final FoodAPI api = FoodAPI.getInstance();

            if (rsp != null) {
                Callback c = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("App", Arrays.toString(e.getStackTrace()));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String q = rsp.getQuery();
                        final Response res = response;

                        self.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (q != null) {
                                        List<RecipeSearchResult> rsr = api.parseRecipeSearchResultJson(res, true);
                                        recipeList.setAdapter(new RecipeSearchResultListViewAdapter(self, rsr));
                                    } else {
                                        List<RecipeSearchResult> rsr = api.parseRecipeSearchResultJson(res, false);
                                        recipeList.setAdapter(new RecipeSearchResultListViewAdapter(self, rsr));
                                    }
                                    progress.hide();
                                } catch (IOException e) {
                                    Log.d("App", Arrays.toString(e.getStackTrace()));
                                }
                            }
                        });
                    }
                };

                try {
                    progress = new ProgressDialog(this);
                    progress.setMessage("Searching for recipes");
                    progress.setIndeterminate(true);
                    progress.show();
                    api.searchRecipes(rsp, c);
                } catch (Exception e) {
                    Log.d("App", Arrays.toString(e.getStackTrace()));
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
        if (r.missedIngredients != null) {
            i.putParcelableArrayListExtra("missingIngredients", new ArrayList<>(Arrays.asList(r.missedIngredients)));
        }
        if (r.usedIngredients != null) {
            i.putParcelableArrayListExtra("usedIngredients", new ArrayList<>(Arrays.asList(r.usedIngredients)));
        }
        startActivity(i);
    }

}
