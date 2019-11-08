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

import java.util.List;

public class RecipeFragment extends Fragment {

    private List<Recipe> mRceipeIds;
    private int mListIndex;

    OnImageClickListener mCallback;

    public interface OnImageClickListener{
        void onImageSelected(int position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement OnImageClickListener");
        }
    }

    /**
     * Mandatory constructor for Fragment
     */
    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        GridView gridView = null;

        if (rootView.findViewById(R.id.recipe_grid_view_tablet) != null){
            gridView = (GridView) rootView.findViewById(R.id.recipe_grid_view_tablet);
            gridView.setNumColumns(2);
        } else {
            gridView = (GridView) rootView.findViewById(R.id.recipe_grid_view);
        }

        RecipeListAdapter mAdapter = new RecipeListAdapter(getContext(), Recipe.getAllRecipeIDs(getContext()));

        gridView.setAdapter(mAdapter);

        // Set click listener to trigger callback when an item is selected
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int indexID = view.getId();
                mCallback.onImageSelected(position);
            }
        });

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
