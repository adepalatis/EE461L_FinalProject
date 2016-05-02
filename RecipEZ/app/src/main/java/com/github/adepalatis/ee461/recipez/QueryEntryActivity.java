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
        implements View.OnClickListener {

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

        // Initialize lists of selected filters
        selectedCuisines = new ArrayList<String>();
        selectedDiets = new ArrayList<String>();
        selectedType = new ArrayList<String>();
        selectedIntolerances = new ArrayList<String>();

        queryEntryBox = (EditText)findViewById(R.id.queryEntryBox);

        try {
            searchButton.setOnClickListener(this);
        } catch(Exception e) {
            Log.d("setListener failure", e.toString());
        }
    }

    @Override
    public void onClick(View v) {

    }
}
