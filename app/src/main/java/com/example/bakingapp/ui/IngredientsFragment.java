package com.example.bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

public class IngredientsFragment extends Fragment {

    private static final String TAG = IngredientsFragment.class.getSimpleName();

    private Recipe recipe;

    public IngredientsFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.ingredients_grid_view);

        IngredientsAdapter adapter = new IngredientsAdapter(getContext(), getRecipe());

        gridView.setAdapter(adapter);

        return rootView;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}