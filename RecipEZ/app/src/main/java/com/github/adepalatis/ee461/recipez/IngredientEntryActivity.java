package com.github.adepalatis.ee461.recipez;

/**
 * Created by Tony on 3/27/2016.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

public class IngredientEntryActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener, MultiSpinner.MultiSpinnerListener {

    private MultiSpinner cuisines;
    private MultiSpinner diets;
    private MultiSpinner allergies;
    private ArrayList<CharSequence> cuisineList;
    private ArrayList<CharSequence> dietList;
    private ArrayList<CharSequence> allergiesList;

    private Spinner cuisineSpinner;
    private EditText ingredientEntryBox;
    private GridView selectedIngredientsView;
    private Button searchButton;
    private Button addButton;
    private ArrayList<CharSequence> ingredientsList;
    private GoogleApiClient mGoogleApiClient;

    // Handles removing ingredients from the grid when user clicks on them
    public class IngredientClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            removeIngredient(ingredientsList.get(position));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_entry);

        // Initialize spinner, list of selected ingredients, ingredient entry field, checkboxes, and button
        cuisines = (MultiSpinner) findViewById(R.id.cuisineSpinner);
        diets = (MultiSpinner) findViewById(R.id.dietSpinner);

//        cuisineSpinner = (Spinner)findViewById(R.id.cuisineSpinner);
        ingredientEntryBox = (EditText)findViewById(R.id.ingredientEntryBox);
        selectedIngredientsView = (GridView)findViewById(R.id.selectedIngredientsGrid);
        searchButton = (Button)findViewById(R.id.searchButton);
        addButton = (Button)findViewById(R.id.addButton);
        ingredientsList = new ArrayList<CharSequence>();
        cuisineList = new ArrayList<CharSequence>();
        dietList = new ArrayList<CharSequence>();
        allergiesList = new ArrayList<CharSequence>();

        // Configure listeners
        try {
            searchButton.setOnClickListener(this);
            addButton.setOnClickListener(this);
            selectedIngredientsView.setOnItemClickListener(new IngredientClickListener());
            cuisineSpinner.setOnItemSelectedListener(this);
            findViewById(R.id.signOutButton).setOnClickListener(this);
        } catch(Exception e) {
            System.err.println(e.toString());
        }

        // Configure the cuisine spinner
        cuisines.setItems(getResources().getStringArray(R.array.cuisine_array), this);
        diets.setItems(getResources().getStringArray(R.array.diet_array), this);

//        ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(this, R.array.cuisine_array, android.R.layout.simple_spinner_dropdown_item);
//        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        cuisineSpinner.setAdapter(cuisineAdapter);

        // Configure the google API client
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API).build();
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
                    if (selected[k]) { cuisineList.add(cuisines.getItems()[k]); }
                    break;
                case R.id.dietSpinner:
                    if(selected[k]) { dietList.add(diets.getItems()[k]); }
                    break;
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
                startActivity(new Intent(this, ShowRecipesActivity.class));
                v.setBackgroundColor(Color.RED);
                break;

            case R.id.addButton:
                addIngredientToGrid(ingredientEntryBox.getText().toString());
                break;

            // TODO: Implement this
            case R.id.savedRecipesButton:
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
        if(!ingredientsList.contains(ingredient) && !ingredient.equals("")) {
            // Add "ingredient" to the list
            ingredientsList.add(ingredient);
            updateGrid();
        }
    }

    public void removeIngredient(CharSequence ingredient) {
        ingredientsList.remove(ingredient);
        updateGrid();
    }

    private void updateGrid() {
        // Create a new adapater with the updated "ingredientsList"
        TextViewAdapter ingredientsAdapter = new TextViewAdapter(this, ingredientsList);
        selectedIngredientsView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();

        // Clear the ingredient entry field
        ingredientEntryBox.setText("");
    }

}
