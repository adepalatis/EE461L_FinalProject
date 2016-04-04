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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class IngredientEntryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner cuisineSpinner;
    private EditText ingredientEntryBox;
    private GridView selectedIngredients;
    private Button findRecipesButton;
    private Button addIngredientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_entry);

        // Initialize spinner, list of selected ingredients, ingredient entry field, checkboxes, and button
        cuisineSpinner = (Spinner)findViewById(R.id.cuisine_spinner);
        ingredientEntryBox = (EditText)findViewById(R.id.ingredientEntryBox);
        selectedIngredients = (GridView)findViewById(R.id.gridView);
        findRecipesButton = (Button)findViewById(R.id.findRecipesButton);
        addIngredientButton = (Button)findViewById(R.id.addIngredientButton);

        // Configure listeners
        try {
            findRecipesButton.setOnClickListener(this);
            addIngredientButton.setOnClickListener(this);
            cuisineSpinner.setOnItemSelectedListener(this);
        } catch(Exception e) {
            System.err.println(e.toString());
        }

        // Configure the cuisine spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cuisine_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        CharSequence selectedCuisine = (CharSequence)parent.getItemAtPosition(pos);
        if(selectedCuisine.toString().equals("Chinese")) {
            ((Button) findViewById(R.id.findRecipesButton)).setText(R.string.test_string);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.findRecipesButton:
                startActivity(new Intent(this, ShowRecipesActivity.class));
                break;

            case R.id.addIngredientButton:
                addIngredientToGrid(ingredientEntryBox.getText());
                break;
        }
    }

    public void addIngredientToGrid(CharSequence ingredient) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, R.layout.activity_show_recipes);
        adapter.add(ingredient);
        selectedIngredients.setAdapter(adapter);
    }

}
