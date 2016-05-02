package com.github.adepalatis.ee461.recipez;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowSingleRecipeActivity extends AppCompatActivity {

    ImageView recipeImage;
    TextView recipeTitle, servings, readyIn;
    ListView missing, used;

    //used for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.nutrition_info:
                int a = 1;
                return true;
            case R.id.show_recipe:
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_google_login, menu);
        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_recipe);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.showOverflowMenu();
        myToolbar.setTitleTextColor(Color.WHITE);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Bundle data = getIntent().getExtras();

        if (data != null) {
            Integer recipeId = data.getInt("recipeId");
            List<Ingredient> missingIngredients = data.getParcelableArrayList("missingIngredients");
            List<Ingredient> usedIngredients = data.getParcelableArrayList("usedIngredients");

            recipeImage = (ImageView) findViewById(R.id.recipeViewImage);
            recipeTitle = (TextView) findViewById(R.id.recipeViewTitle);
            servings = (TextView) findViewById(R.id.numServings);
            readyIn = (TextView) findViewById(R.id.minutes);
            missing = (ListView) findViewById(R.id.missingIngredientsList);
            used = (ListView) findViewById(R.id.usedIngredientsList);

            try {
                Recipe r = FoodAPI.getInstance().getRecipe(recipeId, true);

                String url = r.image.contains("https") ? r.image : "https://spoonacular.com/recipeImages/" + r.image;
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, r.title);
                recipeImage.setImageDrawable(d);

                recipeTitle.setText(r.title);
                servings.setText(r.servings.toString());
                readyIn.setText(r.readyInMinutes.toString() + " " + getString(R.string.minutes));

                IngredientsListViewAdapter m = new IngredientsListViewAdapter(getIngredients(missingIngredients));
                m.setInflater(this);
                IngredientsListViewAdapter u = new IngredientsListViewAdapter(getIngredients(usedIngredients));
                u.setInflater(this);

                missing.setAdapter(m);
                used.setAdapter(u);

                justifyListViewHeightBasedOnChildren(missing);
                justifyListViewHeightBasedOnChildren(used);

                missing.setEnabled(false);
                missing.setOnClickListener(null);
                used.setEnabled(false);
                used.setOnClickListener(null);
            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
        }
    }

    private List<String> getIngredients(List<Ingredient> ing) {
        ArrayList<String> s = new ArrayList<>();
        for (Ingredient i: ing) {
            s.add(i.getName());
        }

        return s;
    }

    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        IngredientsListViewAdapter adapter = (IngredientsListViewAdapter) listView.getAdapter();

        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

}
