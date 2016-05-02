package com.github.adepalatis.ee461.recipez;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        if (mImageView != null) {
            mImageView.setImageResource(R.drawable.app_icon);
        }

        // Button listeners
        try {
            Button b = (Button)findViewById(R.id.startButton);
            if (b != null) {
                b.setOnClickListener(this);
                b.setBackgroundColor(Color.WHITE);
            }
        } catch(Exception e){
            System.err.println(e.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nutrition_info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                startActivity(new Intent(this, IngredientEntryActivity.class));
                break;
        }
    }
}
