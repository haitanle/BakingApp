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

import java.util.List;

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = RecipeDetailFragment.class.getSimpleName();

    private List<Recipe> mRceipeIds;
    private int mListIndex;

    public RecipeDetailFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.ingredients_grid_view);

        //RecipeDetailFragment mAdapter = new RecipeDetailFragment(getContext(), Recipe.getAllRecipeIDs(getContext()));

        //gridView.setAdapter(mAdapter);

        //todo: finish recipeDetailFragment display

        return rootView;
    }

    public List<Recipe> getmRceipeIds() {
        return mRceipeIds;
    }

    public void setmRceipeIds(List<Recipe> mRceipeIds) {
        this.mRceipeIds = mRceipeIds;
    }

    public int getmListIndex() {
        return mListIndex;
    }

    public void setmListIndex(int mListIndex) {
        this.mListIndex = mListIndex;
    }
}
