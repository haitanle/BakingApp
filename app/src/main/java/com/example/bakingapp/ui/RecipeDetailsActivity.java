package com.example.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsFragment.OnStepSelected, StepsAdapter.ListItemClickListener {
//public class RecipeDetailsActivity extends AppCompatActivity implements StepsRecyclerAdapter.OnStepSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public void onStepsClicked(int recipeID, int stepID) {

        //Toast.makeText(this, "On Step clicked with position "+ste.getShortDescription(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtra("recipeID",recipeID);
        intent.putExtra("stepID", stepID);
        startActivity(intent);
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

//    @Override
//    public void onStepClick() {
//        Toast.makeText(this, "On Step clicked" , Toast.LENGTH_LONG).show();
//    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        // COMPLETED (12) Show a Toast when an item is clicked, displaying that item number that was clicked
        /*
         * Create a Toast and store it in our Toast field.
         * The Toast that shows up will have a message similar to the following:
         *
         *                     Item #42 clicked.
         */
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

    }
}
