package com.github.adepalatis.ee461.recipez;

/**
 * Created by Tony on 3/27/2016.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IngredientEntryActivity extends AppCompatActivity
        implements View.OnClickListener {

    private AutoCompleteTextView ingredientEntryBox;
    private GridView ingredientsGrid;
    private Button addButton;
    private ArrayList<CharSequence> selectedIngredients;

    // Handles removing ingredients from the grid when user clicks on them
    public class IngredientClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            removeIngredient(selectedIngredients.get(position));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ingredient_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case R.id.switch_to_query:
                startActivity(new Intent(this, QueryEntryActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_entry);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Initialize list of selected ingredients, ingredient entry field, and button
        ingredientsGrid = (GridView) findViewById(R.id.selectedIngredientsGrid);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setBackgroundColor(Color.WHITE);
        addButton = (Button) findViewById(R.id.addButton);
        selectedIngredients = new ArrayList<>();

        //Button dummy = (Button) findViewById(R.id.button);

        ingredientEntryBox = (AutoCompleteTextView) findViewById(R.id.ingredientEntryBox);
        final List<String> toDisplay = new ArrayList<>();
        final ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, toDisplay);
        ingredientEntryBox.setAdapter(autoCompleteAdapter);

        ingredientEntryBox.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addButton.performClick();
                    return true;
                }
                return false;
            }
        });

        ingredientEntryBox.addTextChangedListener(new TextWatcher() {
            String text;
            List<Ingredient> toConvert;

            @Override
            public void afterTextChanged(Editable mEdit) {
                text = mEdit.toString();

                if (text.equals("")) {
                    autoCompleteAdapter.clear();
                    autoCompleteAdapter.notifyDataSetChanged();
                    return;
                }

                Callback c = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("App", Arrays.toString(e.getStackTrace()));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        toConvert = FoodAPI.getInstance().parseIngredientJson(response);

                        IngredientEntryActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                autoCompleteAdapter.clear();
                                for (Ingredient i : toConvert) {
                                    autoCompleteAdapter.add(i.getName());
                                }
                                autoCompleteAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                };

                try {
                    FoodAPI.getInstance().searchIngredient(text, c);
                } catch (IOException e) {
                    Log.d("App", Arrays.toString(e.getStackTrace()));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        // Configure listeners
        try {
            searchButton.setOnClickListener(this);
            addButton.setOnClickListener(this);
            ingredientsGrid.setOnItemClickListener(new IngredientClickListener());
            findViewById(R.id.signOutButton).setOnClickListener(this);
            //dummy.setOnClickListener(this);
        } catch (Exception e) {
            Log.d("Aoo", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                // Don't start the next activity until the user enters ingredients
                if (selectedIngredients.isEmpty()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
                    builder1.setMessage("Add at least one ingredient to your pantry");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    break;
                }

                ArrayList<Ingredient> ingredients = new ArrayList<>();
                for (CharSequence i : selectedIngredients) {
                    String name = ((String) i).substring(0, i.length() - 3);
                    ingredients.add(new Ingredient(name));
                }
                RecipeSearchParameters rsp = new RecipeSearchParameters(ingredients, 1);
                rsp.setMaxNumber(99);
                rsp.setIngredientLists(true);

                Intent nextActivity = new Intent(this, ShowRecipesActivity.class);
                nextActivity.putExtra("RSP", rsp);
                startActivity(nextActivity);
                break;

            case R.id.addButton:
                addIngredientToGrid(ingredientEntryBox.getText().toString() + "  x");
                break;

            // DUMMY BUTTON FOR TRANSITIONING TO QUERYENTRYACTIVITY
            //case R.id.button:
            //    startActivity(new Intent(this, QueryEntryActivity.class));
            //    break;
        }
    }

    public void addIngredientToGrid(CharSequence ingredient) {
        // Make sure "ingredient" has not already been entered
        if (!selectedIngredients.contains(ingredient) && !ingredient.equals("  x")) {
            // Add "ingredient" to the list
            selectedIngredients.add(ingredient);
            updateGrid();
        }
        ingredientEntryBox.setText("");
    }

    public void removeIngredient(CharSequence ingredient) {
        selectedIngredients.remove(ingredient);
        updateGrid();
    }

    private void updateGrid() {
        // Create a new adapater with the updated "selectedIngredients"
        TextViewAdapter ingredientsAdapter = new TextViewAdapter(this, selectedIngredients);
        ingredientsGrid.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();

        // Clear the ingredient entry field
        ingredientEntryBox.setText("");
    }
}
