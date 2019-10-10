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

public class RecipeFragment extends Fragment {

    /**
     * Mandatory constructor for Fragment
     */
    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.recipe_grid_view);

        RecipeListAdapter mAdapter = new RecipeListAdapter(getContext(), Recipe.getAllRecipeIDs(getContext()));

        gridView.setAdapter(mAdapter);

        return rootView;
    }
}
