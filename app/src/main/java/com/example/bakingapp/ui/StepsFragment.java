package com.example.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
//    OnStepSelected mCallback;


    public StepsFragment(){
    }

//    public interface OnStepSelected {
//        public void onStepsClicked(int recipeID, int stepID);
//    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        try{
//           mCallback = (OnStepSelected) context;
//        }catch (ClassCastException e){
//            throw new ClassCastException("onSelectedImage not implemented");
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_steps_recycler_view, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.steps_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        StepsAdapter adapter = new StepsAdapter(getContext(), getRecipe(), this);
        recyclerView.setAdapter(adapter);


//        recyclerView.setOnClickListener(new RecyclerView.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//            }
//        });

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

        Log.d(TAG, "Fragment step clicked: "+clickedItemIndex);
        Intent intent = new Intent(getContext(), StepsActivity.class);
        intent.putExtra("recipeID",recipe.getId());
        intent.putExtra("stepID", recipe.getStepsList().get(clickedItemIndex).getId());
        startActivity(intent);

    }

}
