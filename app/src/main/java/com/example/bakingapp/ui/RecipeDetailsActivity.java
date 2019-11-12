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

        if (MainActivity.isTablet){
            Log.d(RecipeDetailsActivity.class.getSimpleName(), "This is tablet layout");
        }

        if (savedInstanceState == null){

            ExoPlayerFragment exoFragment = new ExoPlayerFragment();
            exoFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.video_player, exoFragment).commit();
        }

        int position = getIntent().getIntExtra("position",-1);
        Recipe recipe = Recipe.getRecipeByID(this, position);

        IngredientsFragment ingredientFragment = new IngredientsFragment();
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