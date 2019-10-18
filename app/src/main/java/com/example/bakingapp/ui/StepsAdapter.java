package com.example.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

// Custom adapter class that displays a list of Recipe in a GridView
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsPlaceHolder> {

    // Keep track of the context and list of images to display
    private Context mContext;
    private Recipe recipe;

    final private StepItemClickListener mOnClickListener;


    public StepsAdapter(Context mContext, Recipe recipe, StepItemClickListener listener) {
        this.mContext = mContext;
        this.recipe = recipe;
        mOnClickListener = listener;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface StepItemClickListener {
        void onStepItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public StepsPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StepsPlaceHolder placeHolder;

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_text_view, parent, false);

        placeHolder = new StepsPlaceHolder(view);

        return placeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsPlaceHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipe.getStepsList().size();
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup viewGroup) {
//
//        TextView textView;
//        if(convertView == null){
//            // If the view is not recycled, this creates a new TextView
//            textView = new TextView(mContext);
//            textView.setPadding(8, 8, 8, 8);
//        } else{
//            textView = (TextView) convertView;
//        }
//
//        Steps steps = recipe.getStepsList().get(position);
//        String shortDescription = String.valueOf(steps.getShortDescription());
//
//        // set the recipe name to the textview
//        textView.setText(shortDescription);
//
//        return textView;
//    }

    public class StepsPlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView stepsTextView;

        public StepsPlaceHolder(@NonNull View itemView) {
            super(itemView);

            stepsTextView = (TextView) itemView.findViewById(R.id.text_view_list);
            itemView.setOnClickListener(this);
        }

        public void bind(final int position){

//            stepsTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(StepsAdapter.class.getSimpleName(), "position bind " + recipe.getStepsList().get(position).getShortDescription());
//
//                    Intent intent = new Intent(v.getContext(), StepsActivity.class);
//                    intent.putExtra("recipeID",recipe.getId());
//                    intent.putExtra("stepID", recipe.getStepsList().get(position).getId());
//
//
//                }
//            });

            stepsTextView.setText(recipe.getStepsList().get(position).getShortDescription());
        }

        /**
         * Called whenever a user clicks on an item in the list.
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Log.d(IngredientsAdapter.class.getSimpleName(), "Adapter position clicked "+clickedPosition);
            mOnClickListener.onStepItemClick(clickedPosition);
        }
    }
}
