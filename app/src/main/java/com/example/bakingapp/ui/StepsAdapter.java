package com.example.bakingapp.ui;

import android.content.Context;
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

    public StepsAdapter(Context mContext, Recipe recipe) {
        this.mContext = mContext;
        this.recipe = recipe;
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
    public void onBindViewHolder(@NonNull StepsPlaceHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return recipe.getStepsList().size();
    }

    public class StepsPlaceHolder extends RecyclerView.ViewHolder{

        TextView stepsTextView;

        public StepsPlaceHolder(@NonNull View itemView) {
            super(itemView);

            stepsTextView = (TextView) itemView.findViewById(R.id.text_view_list);
        }

        public void bind(int position){
            stepsTextView.setText(recipe.getStepsList().get(position).getShortDescription());
        }
    }
}
