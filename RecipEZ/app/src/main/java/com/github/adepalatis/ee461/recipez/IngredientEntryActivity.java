package com.github.adepalatis.ee461.recipez;

/**
 * Created by Tony on 3/27/2016.
 */

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

import java.util.ArrayList;

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


    private EditText ingredientEntryBox;
    private GridView ingredientsGrid;
    private Button searchButton;
    private Button addButton;
    private ArrayList<CharSequence> selectedIngredients;
    private GoogleApiClient mGoogleApiClient;

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
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_ingredient_entry);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(Color.WHITE);

        // Initialize spinner, list of selected ingredients, ingredient entry field, checkboxes, and button
        cuisineSpinner = (MultiSpinner) findViewById(R.id.cuisineSpinner);
        dietSpinner = (MultiSpinner) findViewById(R.id.dietSpinner);
        intoleranceSpinner = (MultiSpinner) findViewById((R.id.intoleranceSpinner));
        typeSpinner = (MultiSpinner) findViewById((R.id.typeSpinner));
        ingredientsGrid = (GridView)findViewById(R.id.selectedIngredientsGrid);
        searchButton = (Button)findViewById(R.id.searchButton);
        addButton = (Button)findViewById(R.id.addButton);
        selectedIngredients = new ArrayList<CharSequence>();
        selectedCuisines = new ArrayList<String>();
        selectedDiets = new ArrayList<String>();
        selectedType = new ArrayList<String>();
        selectedIntolerances = new ArrayList<String>();
        ingredientEntryBox = (EditText)findViewById(R.id.ingredientEntryBox);
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
        } catch(Exception e) {
            System.err.println(e.toString());
        }

        // Configure the cuisine spinner
        cuisineSpinner.setItems(getResources().getStringArray(R.array.cuisine_array), this);
        dietSpinner.setItems(getResources().getStringArray(R.array.diet_array), this);
        intoleranceSpinner.setItems(getResources().getStringArray(R.array.allergy_array), this);
        typeSpinner.setItems(getResources().getStringArray(R.array.type), this);
        // Configure the google API client
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API).build();

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowCustomEnabled(true);
//
//        LayoutInflater inflator = (LayoutInflater) this
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflator.inflate(R.layout.actionbar, null);
//        actionBar.setCustomView(v);
//        ArrayList<String> COUNTRIES = new ArrayList<String>();
//        COUNTRIES.add("USA");
//        COUNTRIES.add("England");
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//        AutoCompleteTextView textView = (AutoCompleteTextView) v
//                .findViewById(R.id.ingredientEntryBox);
//        textView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        CharSequence selectedCuisine = (CharSequence)parent.getItemAtPosition(pos);

    }

    @Override
    public void onItemsSelected(View v, boolean[] selected) {
        for(int k = 0; k < selected.length; k++) {

            switch (v.getId()) {
                case R.id.cuisineSpinner:
                    if (selected[k]) { selectedCuisines.add(cuisineSpinner.getItems()[k]); }
                    break;
                case R.id.dietSpinner:
                    if(selected[k]) { selectedDiets.add(dietSpinner.getItems()[k]); }
                    break;
                case R.id.intoleranceSpinner:
                    if(selected[k]) { selectedIntolerances.add(intoleranceSpinner.getItems()[k]);}
                    break;
                case R.id.typeSpinner:
                    if(selected[k]) {selectedType.add(typeSpinner.getItems()[k]);}
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.searchButton:
                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
                for(CharSequence i : selectedIngredients) {
                    Ingredient myIngredient = new Ingredient((String)i);
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

//                if(nextActivity.getExtras() == null) { Log.d("wtf", "wtf"); }
                Log.d("thing2", nextActivity.toString());
                startActivity(nextActivity);
                break;

            case R.id.addButton:
                addIngredientToGrid(ingredientEntryBox.getText().toString());
                break;

            case R.id.signOutButton:
                signOut();
                startActivity(new Intent(this, GoogleLoginActivity.class));
                break;
        }
    }

    public void signOut() {Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
            new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {

                }
            });

    }

    public void addIngredientToGrid(CharSequence ingredient) {
        // Make sure "ingredient" has not already been entered
        if(!selectedIngredients.contains(ingredient) && !ingredient.equals("")) {
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
