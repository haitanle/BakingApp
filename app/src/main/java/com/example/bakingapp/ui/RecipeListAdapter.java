package com.example.bakingapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bakingapp.data.Recipe;

import java.util.List;

// Custom adapter class that displays a list of Recipe in a GridView
public class RecipeListAdapter extends BaseAdapter {

    // Keep track of the context and list of images to display
    private Context mContext;
    private List<Recipe> mRecipeIds;

    public RecipeListAdapter(Context mContext, List<Recipe> mRecipeIds) {
        this.mContext = mContext;
        this.mRecipeIds = mRecipeIds;
    }


    @Override
    public int getCount() {
        return mRecipeIds.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        TextView textView;
        if(view == null){
            // If the view is not recycled, this creates a new TextView
            textView = new TextView(mContext);
            textView.setTextSize(30);
            int color = Color.parseColor("#00BCD4");
            textView.setBackgroundColor(color);
            textView.setPadding(5,5,5,5);
        } else{
            textView = (TextView) view;
        }

        // set the recipe name to the textview
        textView.setText(mRecipeIds.get(position).getName());
        return textView;
    }
}
