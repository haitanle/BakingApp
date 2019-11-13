package com.example.bakingapp.ui;

import android.os.Bundle;
import android.util.Log;
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

        int position = getIntent().getIntExtra("position",-1);
        Recipe recipe = Recipe.getRecipeByID(this, position);

        IngredientsFragment ingredientFragment = new IngredientsFragment();
        ingredientFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.ingredients_container, ingredientFragment).commit();

        StepsFragment stepsFragment = new StepsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        stepsFragment.setArguments(bundle);

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


//todo: widget not populating for small screen
//todo: placeholder for not videoURL using picasso
//todo: startup failure when launch
//done: double click necessary for landscape view
//done: step recipe not getting passed on set in RecipeDetailsActivity
//done: saving video for tablet