package com.example.bakingapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        // inflate the fragment layout with inside the container
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // Get a reference to the TextView in the fragment layout
        final TextView textView = (TextView) rootView.findViewById(R.id.ingredient_detail_textView);

        if (mRceipeIds != null){

            textView.setText(mRceipeIds.get(mListIndex).getName());
        } else{
            Log.v(TAG, "This fragment has a null list");
        }

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
