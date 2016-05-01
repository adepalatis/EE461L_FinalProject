package com.github.adepalatis.ee461.recipez;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_recipe);

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

            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
        }
    }
}
