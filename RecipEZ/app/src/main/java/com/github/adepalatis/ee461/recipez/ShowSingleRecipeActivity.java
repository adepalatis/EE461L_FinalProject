package com.github.adepalatis.ee461.recipez;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowSingleRecipeActivity extends AppCompatActivity {

    ImageView recipeImage;
    TextView recipeTitle, servings, readyIn;
    ListView missing, used;
    TableLayout table;
    Recipe r;
    Activity self = this;
    ProgressDialog progress;

    //used for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.nutrition_info:
                final Dialog d = new Dialog(this);
                d.setContentView(R.layout.nutrition_dialog);
                init(d);
                d.show();
                return true;
            case R.id.show_recipe:
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(r.sourceUrl));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_recipe);

        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle data = getIntent().getExtras();

        if (data != null) {
            final Integer recipeId = data.getInt("recipeId");
            final List<Ingredient> missingIngredients = data.getParcelableArrayList("missingIngredients");
            final List<Ingredient> usedIngredients = data.getParcelableArrayList("usedIngredients");

            recipeImage = (ImageView) findViewById(R.id.recipeViewImage);
            recipeTitle = (TextView) findViewById(R.id.recipeViewTitle);
            servings = (TextView) findViewById(R.id.numServings);
            readyIn = (TextView) findViewById(R.id.minutes);
            missing = (ListView) findViewById(R.id.missingIngredientsList);
            used = (ListView) findViewById(R.id.usedIngredientsList);

            try {
                Callback c = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("App", Arrays.toString(e.getStackTrace()));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        r = FoodAPI.getInstance().parseRecipeJson(response);

                        self.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String url = r.image.contains("https") ? r.image : "https://spoonacular.com/recipeImages/" + r.image;
                                    InputStream is = (InputStream) new URL(url).getContent();
                                    Drawable d = Drawable.createFromStream(is, r.title);
                                    recipeImage.setImageDrawable(d);

                                    recipeTitle.setText(r.title);
                                    servings.setText(r.servings.toString());
                                    readyIn.setText(r.readyInMinutes.toString() + " " + getString(R.string.minutes));

                                    if (missingIngredients != null && !missingIngredients.isEmpty()) {
                                        IngredientsListViewAdapter m = new IngredientsListViewAdapter(getIngredients(missingIngredients));
                                        m.setInflater(self);
                                        missing.setAdapter(m);
                                        justifyListViewHeightBasedOnChildren(missing);
                                    } else {
                                        findViewById(R.id.missingTV).setVisibility(View.GONE);
                                    }

                                    if(usedIngredients != null && !usedIngredients.isEmpty()) {
                                        IngredientsListViewAdapter u = new IngredientsListViewAdapter(getIngredients(usedIngredients));
                                        u.setInflater(self);
                                        used.setAdapter(u);
                                        justifyListViewHeightBasedOnChildren(used);
                                    } else {
                                        findViewById(R.id.usedTV).setVisibility(View.GONE);
                                    }

                                    progress.hide();
                                } catch (Exception e) {
                                    Log.d("App", Arrays.toString(e.getStackTrace()));
                                }
                            }
                        });
                    }
                };

                progress = new ProgressDialog(this);
                progress.setMessage("Getting your recipe");
                progress.setIndeterminate(true);
                progress.show();
                FoodAPI.getInstance().getRecipe(recipeId, true, c);
            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
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

    private void init(Dialog d) {
        List<Nutrient> nutrition = Arrays.asList(r.nutrition.nutrients);
        table = (TableLayout) d.findViewById(R.id.nutrientTable);

        for (Nutrient n: nutrition) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(params);

            TextView name = new TextView(this);
            name.setText(n.title);
            row.addView(name);

            TextView amount = new TextView(this);
            amount.setText(n.amount + " " + n.unit);
            row.addView(amount);

            TextView percent = new TextView(this);
            percent.setText(n.percentOfDailyNeeds + "%");
            row.addView(percent);

            table.addView(row);
        }
    }
}
