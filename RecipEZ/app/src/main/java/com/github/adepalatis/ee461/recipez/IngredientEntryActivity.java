package com.github.adepalatis.ee461.recipez;

/**
 * Created by Tony on 3/27/2016.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
        implements View.OnClickListener, AdapterView.OnItemSelectedListener, MultiSpinner.MultiSpinnerListener {

    private MultiSpinner cuisineSpinner;
    private MultiSpinner dietSpinner;
    private MultiSpinner intoleranceSpinner;
    private MultiSpinner typeSpinner;
    private ArrayList<String> selectedCuisines;
    private ArrayList<String> selectedDiets;
    private ArrayList<String> selectedIntolerances;
    private ArrayList<String> selectedType;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_entry);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Initialize spinner, list of selected ingredients, ingredient entry field, checkboxes, and button
        cuisineSpinner = (MultiSpinner) findViewById(R.id.cuisineSpinner);
        dietSpinner = (MultiSpinner) findViewById(R.id.dietSpinner);
        intoleranceSpinner = (MultiSpinner) findViewById((R.id.intoleranceSpinner));
        typeSpinner = (MultiSpinner) findViewById((R.id.typeSpinner));
        ingredientsGrid = (GridView) findViewById(R.id.selectedIngredientsGrid);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        addButton = (Button) findViewById(R.id.addButton);
        selectedIngredients = new ArrayList<>();
        selectedCuisines = new ArrayList<>();
        selectedDiets = new ArrayList<>();
        selectedType = new ArrayList<>();
        selectedIntolerances = new ArrayList<>();

        Button dummy = (Button) findViewById(R.id.button);

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
                        if (toConvert.size() < 10) {
                            toConvert = toConvert.subList(0, toConvert.size());
                        } else {
                            toConvert = toConvert.subList(0, 10);
                        }

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
            cuisineSpinner.setOnItemSelectedListener(this);
            dietSpinner.setOnItemSelectedListener(this);
            intoleranceSpinner.setOnItemSelectedListener(this);
            typeSpinner.setOnItemSelectedListener(this);
            findViewById(R.id.signOutButton).setOnClickListener(this);
            dummy.setOnClickListener(this);
        } catch (Exception e) {
            Log.d("Aoo", Arrays.toString(e.getStackTrace()));
        }

        // Configure the cuisine spinner
        cuisineSpinner.setItems(getResources().getStringArray(R.array.cuisine_array), this);
        dietSpinner.setItems(getResources().getStringArray(R.array.diet_array), this);
        intoleranceSpinner.setItems(getResources().getStringArray(R.array.allergy_array), this);
        typeSpinner.setItems(getResources().getStringArray(R.array.type), this);
    }

    @Override
    public void onItemsSelected(View v, boolean[] selected) {
        for (int k = 0; k < selected.length; k++) {
            switch (v.getId()) {
                case R.id.cuisineSpinner:
                    if (selected[k]) {
                        selectedCuisines.add(cuisineSpinner.getItems()[k]);
                    }
                    break;
                case R.id.dietSpinner:
                    if (selected[k]) {
                        selectedDiets.add(dietSpinner.getItems()[k]);
                    }
                    break;
                case R.id.intoleranceSpinner:
                    if (selected[k]) {
                        selectedIntolerances.add(intoleranceSpinner.getItems()[k]);
                    }
                    break;
                case R.id.typeSpinner:
                    if (selected[k]) {
                        selectedType.add(typeSpinner.getItems()[k]);
                    }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                // Don't start the next activity until the user enters ingredients
                if (selectedIngredients.isEmpty()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    builder1.setMessage("Enter at least one ingredient");
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
                    String ingredientName = ((String) i).substring(0, i.length() - 2);
                    Ingredient myIngredient = new Ingredient(ingredientName);
                    myIngredient.setAisle("Hello");
                    myIngredient.setAmount(0.0);
                    myIngredient.setImage("Hello");
                    myIngredient.setOriginalString("Hello");
                    myIngredient.setUnit("Hello");
                    ingredients.add(myIngredient);
                }
                RecipeSearchParameters RSP = new RecipeSearchParameters(ingredients);
                RSP.setCuisine(selectedCuisines);
                RSP.setDiet(selectedDiets);
                RSP.setType(selectedType);
                RSP.setIntolerance(selectedIntolerances);
                RSP.setExcludeIngredients(new ArrayList<Ingredient>());
                Intent nextActivity = new Intent(this, ShowRecipesActivity.class);
                nextActivity.putExtra("RSP", RSP);
                startActivity(nextActivity);
                break;

            case R.id.addButton:
                addIngredientToGrid(ingredientEntryBox.getText().toString() + "  x");
                break;

            // DUMMY BUTTON FOR TRANSITIONING TO QUERYENTRYACTIVITY
            case R.id.button:
                Log.d("button", "button clicked");
                startActivity(new Intent(this, QueryEntryActivity.class));
                break;
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
