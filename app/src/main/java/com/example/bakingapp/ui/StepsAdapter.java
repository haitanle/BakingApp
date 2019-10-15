package com.example.bakingapp.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Steps;

// Custom adapter class that displays a list of Recipe in a GridView
public class StepsAdapter extends BaseAdapter {

    // Keep track of the context and list of images to display
    private Context mContext;
    private Recipe recipe;

    public StepsAdapter(Context mContext, Recipe recipe) {
        this.mContext = mContext;
        this.recipe = recipe;
    }

    @Override
    public int getCount() {
        return recipe.getStepsList().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        TextView textView;
        if(convertView == null){
            // If the view is not recycled, this creates a new TextView
            textView = new TextView(mContext);
            textView.setPadding(8, 8, 8, 8);
        } else{
            textView = (TextView) convertView;
        }

        Steps steps = recipe.getStepsList().get(position);
        String shortDescription = String.valueOf(steps.getShortDescription());

        // set the recipe name to the textview
        textView.setText(shortDescription);

        return textView;
    }
}
