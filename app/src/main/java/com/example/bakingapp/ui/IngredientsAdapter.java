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

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IngredientsViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.recycler_view_text_view, parent, false);

        viewHolder = new IngredientsViewHolder(view);

        //viewHolder.textViewList.setText("test");

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

    //    @Override
//    public int getCount() {
//        return recipe.getIngredientsList().size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }

//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }

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
//        Ingredients ingredient = recipe.getIngredientsList().get(position);
//        String quantity = String.valueOf(ingredient.getQuantity());
//        String measure = ingredient.getMeasure();
//        String material = ingredient.getIngredients();
//
//        // set the recipe name to the textview
//        textView.setText(quantity+" "+measure+" "+material);
//
//        return textView;
//    }



    public class IngredientsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewList;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewList = (TextView) itemView.findViewById(R.id.text_view_list);
        }

        public void bind(int position){
            Log.d(IngredientsAdapter.class.getSimpleName(), "bind position "+position);
            textViewList.setText(recipe.getIngredientsList().get(position).getIngredients());
        }


    }
}
