package com.example.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Ingredients;
import com.example.bakingapp.data.Recipe;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        RecipeDetailFragment ingredientFragment = new RecipeDetailFragment();

        int position = getIntent().getIntExtra("position",-1);

        Log.d(RecipeDetailsActivity.class.getSimpleName(), "position here "+String.valueOf(position));

        Recipe recipe = Recipe.getAllRecipeIDs(this).get(position);

        Log.d(RecipeDetailsActivity.class.getSimpleName(), "recipe ingredients "+ recipe.getIngredientsList().get(1).getIngredients());

        ingredientFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.ingredients_container, ingredientFragment).commit();


        //Intent intent = new Intent(this, StepsActivity.class);
        //startActivity(intent);
    }
}
