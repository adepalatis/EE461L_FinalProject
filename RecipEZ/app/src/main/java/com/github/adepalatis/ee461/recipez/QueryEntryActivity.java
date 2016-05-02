package com.github.adepalatis.ee461.recipez;

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
    private ArrayList<String> selectedType;
    private EditText queryEntryBox;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_entry);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(Color.WHITE);

        // Initialize spinners, search button, and lists of selected filters
        cuisineSpinner = (MultiSpinner) findViewById(R.id.cuisineSpinner);
        dietSpinner = (MultiSpinner) findViewById(R.id.dietSpinner);
        intoleranceSpinner = (MultiSpinner) findViewById((R.id.intoleranceSpinner));
        typeSpinner = (MultiSpinner) findViewById((R.id.typeSpinner));
        searchButton = (Button)findViewById(R.id.searchButton);
        selectedCuisines = new ArrayList<String>();
        selectedDiets = new ArrayList<String>();
        selectedType = new ArrayList<String>();
        selectedIntolerances = new ArrayList<String>();

        queryEntryBox = (EditText)findViewById(R.id.queryEntryBox);

        // Configure the filter spinners' values and set this activity as a listener
        cuisineSpinner.setItems(getResources().getStringArray(R.array.cuisine_array), this);
        dietSpinner.setItems(getResources().getStringArray(R.array.diet_array), this);
        intoleranceSpinner.setItems(getResources().getStringArray(R.array.allergy_array), this);
        typeSpinner.setItems(getResources().getStringArray(R.array.type), this);

        // Configure listeners
        try {
            searchButton.setOnClickListener(this);
        } catch(Exception e) {
            Log.d("setListener failure", e.toString());
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemsSelected(View v, boolean[] selected) {

    }
}
