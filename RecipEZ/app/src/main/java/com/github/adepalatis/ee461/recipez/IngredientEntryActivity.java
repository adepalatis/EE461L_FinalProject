package com.github.adepalatis.ee461.recipez;

/**
 * Created by Tony on 3/27/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;

public class IngredientEntryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner cuisineSpinner;
    private EditText ingredientEntryBox;
    private GridView selectedIngredientsView;
    private Button searchButton;
    private Button addButton;
    private ArrayList<CharSequence> ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_entry);

        // Initialize spinner, list of selected ingredients, ingredient entry field, checkboxes, and button
        cuisineSpinner = (Spinner)findViewById(R.id.cuisineSpinner);
        ingredientEntryBox = (EditText)findViewById(R.id.ingredientEntryBox);
        selectedIngredientsView = (GridView)findViewById(R.id.selectedIngredientsGrid);
        searchButton = (Button)findViewById(R.id.searchButton);
        addButton = (Button)findViewById(R.id.addButton);
        ingredientsList = new ArrayList<CharSequence>();

        // Configure listeners
        try {
            searchButton.setOnClickListener(this);
            addButton.setOnClickListener(this);
            cuisineSpinner.setOnItemSelectedListener(this);
        } catch(Exception e) {
            System.err.println(e.toString());
        }

        // Configure the cuisine spinner
        ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(this, R.array.cuisine_array, android.R.layout.simple_spinner_dropdown_item);
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineSpinner.setAdapter(cuisineAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        CharSequence selectedCuisine = (CharSequence)parent.getItemAtPosition(pos);
        if(selectedCuisine.toString().equals("Chinese")) {
            ((Button) findViewById(R.id.searchButton)).setText(R.string.test_string);
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
                break;

            case R.id.addButton:
                addIngredientToGrid(ingredientEntryBox.getText().toString());
                break;
        }
    }

    public void addIngredientToGrid(CharSequence ingredient) {
        // Make sure "ingredient" has not already been entered
        if(!ingredientsList.contains(ingredient) && ingredient.length() != 0) {
            // Add "ingredient" to the list
            ingredientsList.add(ingredient);

            // Create a new adapater with the updated "ingredientsList"
            TextViewAdapter ingredientsAdapter = new TextViewAdapter(this, ingredientsList);
            selectedIngredientsView.setAdapter(ingredientsAdapter);
            ingredientsAdapter.notifyDataSetChanged();
        }
    }

}
