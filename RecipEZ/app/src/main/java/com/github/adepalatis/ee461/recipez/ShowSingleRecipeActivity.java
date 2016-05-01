package com.github.adepalatis.ee461.recipez;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ShowSingleRecipeActivity extends AppCompatActivity {

    ImageView recipeImage;
    TextView recipeTitle, servings, readyIn;
    ExpandableListView missing, used;
    //used for back button
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_recipe);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Bundle data = getIntent().getExtras();

        if (data != null) {
            Integer recipeId = data.getInt("recipeId");
            List<Ingredient> missingIngredients = data.getParcelableArrayList("missingIngredients");
            List<Ingredient> usedIngredients = data.getParcelableArrayList("usedIngredients");

            recipeImage = (ImageView) findViewById(R.id.recipeImage);
            recipeTitle = (TextView) findViewById(R.id.recipeTitle);
            servings = (TextView) findViewById(R.id.numServings);
            readyIn = (TextView) findViewById(R.id.minutes);
            missing = (ExpandableListView) findViewById(R.id.missingIngredientsList);
            used = (ExpandableListView) findViewById(R.id.usedIngredientsList);

            try {
                Recipe r = FoodAPI.getInstance().getRecipe(recipeId, true);

                String url = r.image.contains("https") ? r.image : "https://spoonacular.com/recipeImages/" + r.image;
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, r.title);
                recipeImage.setImageDrawable(d);

                recipeTitle.setText(r.title);
                servings.setText(r.servings.toString());
                readyIn.setText(r.readyInMinutes.toString() + " " + getString(R.string.minutes));

            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
        }
    }
}
