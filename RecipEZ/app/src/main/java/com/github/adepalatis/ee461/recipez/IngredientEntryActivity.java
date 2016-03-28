package com.github.adepalatis.ee461.recipez;

/**
 * Created by Tony on 3/27/2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class IngredientEntryActivity extends AppCompatActivity {

    private Spinner cuisineSpinner = (Spinner)findViewById(R.id.cuisine_spinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cuisine_array, android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        cuisineSpinner.setAdapter(adapter);
    }

}
