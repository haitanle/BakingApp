package com.example.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    public void onImageSelected(int position) {

        RecipeFragment recipeFragment = new RecipeFragment();

        recipeFragment.setmRceipeIds(Recipe.getAllRecipeIDs(this));

        Recipe recipe = Recipe.getAllRecipeIDs(this).get(position);

        Log.d(MainActivity.class.getSimpleName(), "id pressed "+recipe.getId());

        Intent recipeDetailIntent = new Intent(this, RecipeDetailsActivity.class);

        recipeDetailIntent.putExtra("position", position);

        startActivity(recipeDetailIntent);
    }
}
