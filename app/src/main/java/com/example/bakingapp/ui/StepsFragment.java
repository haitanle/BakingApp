package com.example.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

public class StepsFragment extends Fragment implements StepsAdapter.StepItemClickListener {

    private static final String TAG = StepsFragment.class.getSimpleName();

    private boolean dualPane;
    private Recipe recipe;

    public StepsFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_steps_recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.steps_rv);

        int position = getArguments().getInt("position");
        recipe = Recipe.getRecipeByID(getContext(), position);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        StepsAdapter adapter = new StepsAdapter(getContext(), getRecipe(), this);
        recyclerView.setAdapter(adapter);

        View exoPlayerFrame = getActivity().findViewById(R.id.video_player);
        dualPane = exoPlayerFrame != null && exoPlayerFrame.getVisibility() == View.VISIBLE;

        return rootView;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /*
     * Handles click position from the step list
     */
    @Override
    public void onStepItemClick(int clickedItemIndex){

        Log.d(TAG, "ingredient step clicked: "+clickedItemIndex);
        showPlayer(clickedItemIndex);
    }

    void showPlayer(int clickedItemIndex){

        if (dualPane){

            ExoPlayerFragment exoPlayerFragment = (ExoPlayerFragment) getFragmentManager().findFragmentById(R.id.video_player);

            /*Determine if exoPlayerFragment is already created or not*/
            if (exoPlayerFragment == null){

                exoPlayerFragment = ExoPlayerFragment.newInstance(clickedItemIndex);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.video_player, exoPlayerFragment);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            } else if (exoPlayerFragment.getShownInsdex() != clickedItemIndex){

                Bundle args = new Bundle();

                args.putInt("index", clickedItemIndex);
                exoPlayerFragment.setArguments(args);
                Bundle bundle = new Bundle();

                exoPlayerFragment.onActivityCreated(bundle);

            }else{
                exoPlayerFragment.onStart();
            }
        } else {

            Intent intent = new Intent(getContext(), StepsActivity.class);

            intent.putExtra(getString(R.string.intent_extra_recipeID),recipe.getId());
            intent.putExtra(getString(R.string.intent_extra_stepID), recipe.getStepsList().get(clickedItemIndex).getId());
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
