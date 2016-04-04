package com.github.adepalatis.ee461.recipez;

/**
 * Created by Tony on 3/27/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class IngredientEntryActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner cuisineSpinner;
    private GridView selectedIngredients;
    private Button findRecipesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_entry);

        // Configure Button listener
        try {
            findViewById(R.id.findRecipesButton).setOnClickListener(this);
        } catch(Exception e) {
            System.err.println(e.toString());
        }

        // Initialize spinner, list of selected ingredients, ingredient entry field, checkboxes, and button
        cuisineSpinner = (Spinner)findViewById(R.id.cuisine_spinner);
        selectedIngredients = (GridView)findViewById(R.id.gridView);
        findRecipesButton = (Button)findViewById(R.id.findRecipesButton);

        // Configure the cuisine spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cuisine_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, ShowRecipesActivity.class));
    }

}
