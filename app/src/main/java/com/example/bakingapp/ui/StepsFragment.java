package com.example.bakingapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

public class StepsFragment extends Fragment implements StepsAdapter.StepItemClickListener {

    private static final String TAG = StepsFragment.class.getSimpleName();

    private Recipe recipe;

    private int currentStepID;

    public StepsFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_steps_recycler_view, container, false);

        //final View layoutView = inflater.inflate(R.layout.activity_ingredients, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.steps_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        StepsAdapter adapter = new StepsAdapter(getContext(), getRecipe(), this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onStepItemClick(int clickedItemIndex){

        if (!MainActivity.isTablet){
            Log.d(TAG, "Fragment step clicked: "+clickedItemIndex);
            Intent intent = new Intent(getContext(), StepsActivity.class);
            intent.putExtra("recipeID",recipe.getId());
            intent.putExtra("stepID", recipe.getStepsList().get(clickedItemIndex).getId());
            startActivity(intent);
        }else{
            Intent layoutIntent = new Intent(getContext(), RecipeDetailsActivity.class);
            String stepURL = recipe.getStepsList().get(currentStepID).getVideoUrl();
            layoutIntent.putExtra("recipeID",recipe.getId());
            layoutIntent.putExtra("position", MainActivity.recipeSelected);
            layoutIntent.putExtra("stepID", recipe.getStepsList().get(clickedItemIndex).getId());
            startActivity(layoutIntent);
        }
    }
}
