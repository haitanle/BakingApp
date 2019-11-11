package com.example.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeWidgetProvider;
import com.example.bakingapp.WidgetIntentService;
import com.example.bakingapp.data.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnImageClickListener {

    static public int recipeSelected;

    public static boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeSelected = 0;

        if (findViewById(R.id.tablet_main_fragment) != null){
            isTablet = true;
        } else {
            isTablet = false;
        }

        if (isTablet){
            if (savedInstanceState == null){

            }
        }
    }

    @Override
    public void onImageSelected(int position) {

        RecipeFragment recipeFragment = new RecipeFragment();

        recipeFragment.setmRceipeIds(Recipe.getAllRecipeIDs(this));

        Recipe recipe = Recipe.getRecipeByID(this, position);

        Log.d(MainActivity.class.getSimpleName(), "id pressed "+recipe.getId());

        Intent recipeDetailIntent = new Intent(this, RecipeDetailsActivity.class);

        recipeSelected = position;
        WidgetIntentService.startActionUpdateIngredients(this);

        recipeDetailIntent.putExtra("position", position);

        startActivity(recipeDetailIntent);
    }
}
