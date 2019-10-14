package com.example.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnImageClickListener {

    private int RecipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onImageSelected(int indexID) {

        RecipeDetailFragment ingredientsFragment = new RecipeDetailFragment();

        ingredientsFragment.setmRceipeIds(Recipe.getAllRecipeIDs(this));

        Log.d(MainActivity.class.getSimpleName(), "id pressed "+indexID);

        ingredientsFragment.setmListIndex(indexID);
    }
}
