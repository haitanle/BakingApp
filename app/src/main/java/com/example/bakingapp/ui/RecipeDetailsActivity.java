package com.example.bakingapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        IngredientsFragment ingredientFragment = new IngredientsFragment();
        int position = getIntent().getIntExtra("position",-1);
        Log.d(RecipeDetailsActivity.class.getSimpleName(), "position here "+String.valueOf(position));

        Recipe recipe = Recipe.getAllRecipeIDs(this).get(position);

        Log.d(RecipeDetailsActivity.class.getSimpleName(), "recipe ingredients "+ recipe.getIngredientsList().get(1).getIngredients());

        ingredientFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.ingredients_container, ingredientFragment).commit();

        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setRecipe(recipe);
        fragmentManager.beginTransaction().add(R.id.steps_description_container, stepsFragment).commit();


        //Intent intent = new Intent(this, StepsActivity.class);
        //startActivity(intent);
    }
}
