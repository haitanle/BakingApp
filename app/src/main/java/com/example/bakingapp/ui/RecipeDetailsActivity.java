package com.example.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Steps;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsFragment.OnStepSelected {

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

    }

    @Override
    public void onStepsClicked(int recipeID, int stepID) {

        //Toast.makeText(this, "On Step clicked with position "+ste.getShortDescription(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtra("recipeID",recipeID);
        intent.putExtra("stepID", stepID);
        startActivity(intent);
    }
}
