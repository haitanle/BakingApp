package com.example.bakingapp.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (MainActivity.isTablet){
        }

        IngredientsFragment ingredientFragment = new IngredientsFragment();
        int position = getIntent().getIntExtra("position",-1);

        Recipe recipe = Recipe.getAllRecipeIDs(this).get(position);
        ingredientFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.ingredients_container, ingredientFragment).commit();

        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setRecipe(recipe);
        fragmentManager.beginTransaction().add(R.id.steps_description_container, stepsFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return onOptionsItemSelected(item);
    }
}
