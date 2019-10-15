package com.example.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Steps;

import java.util.List;

public class StepsFragment extends Fragment {

    private static final String TAG = StepsFragment.class.getSimpleName();

    private Recipe recipe;
    OnStepSelected mCallback;

    public StepsFragment(){
    }

    public interface OnStepSelected {
        public void onStepsClicked(int recipeID, int stepID);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
           mCallback = (OnStepSelected) context;
        }catch (ClassCastException e){
            throw new ClassCastException("onSelectedImage not implemented");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.ingredients_grid_view);

        StepsAdapter adapter = new StepsAdapter(getContext(), getRecipe());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                List<Steps> stepsList = recipe.getStepsList();
                mCallback.onStepsClicked(recipe.getId(), stepsList.get(position).getId());
            }
        });

        return rootView;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
