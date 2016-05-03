package com.github.adepalatis.ee461.recipez;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tony on 5/2/2016.
 */
public class QueryEntryActivity extends AppCompatActivity
        implements View.OnClickListener, MultiSpinner.MultiSpinnerListener {

    private MultiSpinner cuisineSpinner;
    private MultiSpinner dietSpinner;
    private MultiSpinner intoleranceSpinner;
    private MultiSpinner typeSpinner;
    private ArrayList<String> selectedCuisines;
    private ArrayList<String> selectedDiets;
    private ArrayList<String> selectedIntolerances;
    private String selectedType;
    private EditText queryEntryBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_entry);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Initialize spinners, search button, and lists of selected filters
        cuisineSpinner = (MultiSpinner) findViewById(R.id.cuisineSpinner);
        dietSpinner = (MultiSpinner) findViewById(R.id.dietSpinner);
        intoleranceSpinner = (MultiSpinner) findViewById((R.id.intoleranceSpinner));
        typeSpinner = (MultiSpinner) findViewById((R.id.typeSpinner));
        selectedCuisines = new ArrayList<String>();
        selectedDiets = new ArrayList<String>();
        selectedType = "";
        selectedIntolerances = new ArrayList<String>();

        queryEntryBox = (EditText)findViewById(R.id.queryEntryBox);

        // Configure the filter spinners' values and set this activity as a listener
        cuisineSpinner.setItems(getResources().getStringArray(R.array.cuisine_array), this);
        dietSpinner.setItems(getResources().getStringArray(R.array.diet_array), this);
        intoleranceSpinner.setItems(getResources().getStringArray(R.array.allergy_array), this);
        typeSpinner.setItems(getResources().getStringArray(R.array.type), this);

        // Configure listeners
        try {
            Button searchButton = (Button) findViewById(R.id.searchButton);
            searchButton.setOnClickListener(this);
        } catch(Exception e) {
            Log.d("App", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onClick(View v) {
        // Don't start the next activity until the user enters a query
        if(queryEntryBox.getText().toString().equals("")) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder1.setMessage("Please enter a search query.");
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
            return;
        }

        String query = queryEntryBox.getText().toString();
        RecipeSearchParameters RSP = new RecipeSearchParameters(query);
        RSP.setCuisine(selectedCuisines);
        RSP.setDiet(selectedDiets);
        RSP.setType(selectedType);
        RSP.setIntolerance(selectedIntolerances);
        RSP.setExcludeIngredients(new ArrayList<Ingredient>());
        Intent nextActivity = new Intent(this, ShowRecipesActivity.class);
        nextActivity.putExtra("RSP", RSP);
        startActivity(nextActivity);
    }

    @Override
    public void onItemsSelected(View v, boolean[] selected) {
        boolean clearedCuisines = false;
        boolean clearedDiets = false;
        boolean clearedIntolerances = false;
        boolean clearedType = false;

        for(int k = 0; k < selected.length; k++) {
            switch (v.getId()) {
                case R.id.cuisineSpinner:
                    if(!clearedCuisines) {
                        selectedCuisines.clear();
                        clearedCuisines = true;
                    }
                    if (selected[k]) {
                        selectedCuisines.add(cuisineSpinner.getItems()[k]);
                    }

                    break;
                case R.id.dietSpinner:
                    if(!clearedDiets) {
                        selectedDiets.clear();
                        clearedDiets = true;
                    }
                    if (selected[k]) {
                        selectedDiets.add(dietSpinner.getItems()[k]);
                    }
                    break;
                case R.id.intoleranceSpinner:
                    if(!clearedIntolerances) {
                        selectedIntolerances.clear();
                        clearedIntolerances = true;
                    }
                    if (selected[k]) {
                        selectedIntolerances.add(intoleranceSpinner.getItems()[k]);
                    }
                    break;
                case R.id.typeSpinner:
                    if (selected[k]) {
                        selectedType = typeSpinner.getItems()[k];
                    }
            }
        }
    }

}
