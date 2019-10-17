package com.example.bakingapp.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Ingredients;
import com.example.bakingapp.data.Recipe;


// Custom adapter class that displays a list of Recipe in a GridView
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    // Keep track of the context and list of images to display
    private Context mContext;
    private Recipe recipe;

    public IngredientsAdapter(Context mContext, Recipe recipe) {
        this.mContext = mContext;
        this.recipe = recipe;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IngredientsViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_text_view, parent, false);
        viewHolder = new IngredientsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Log.d(IngredientsAdapter.class.getSimpleName(), "adapter position :"+position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipe.getIngredientsList().size();
    }


    public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewList;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewList = (TextView) itemView.findViewById(R.id.text_view_list);
        }

        public void bind(int position){

            Ingredients ingredient = recipe.getIngredientsList().get(position);
            String quantity = String.valueOf(ingredient.getQuantity());
            String measure = ingredient.getMeasure();
            String material = ingredient.getIngredients();

            textViewList.setText(quantity+" "+measure+" "+material);
        }

        /**
         * Called whenever a user clicks on an item in the list.
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Log.d(IngredientsAdapter.class.getSimpleName(), "Adapter position clicked "+clickedPosition);
        }
    }
}
