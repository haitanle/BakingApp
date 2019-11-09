package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.data.Ingredients;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.ui.MainActivity;
import com.example.bakingapp.ui.RecipeDetailsActivity;

import java.util.List;

public class GridViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplicationContext());
    }
}

class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredients> ingredientsList;

    public GridRemoteViewFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged(){
        int recipeIndex = MainActivity.recipeSelected;
        this.ingredientsList = Recipe.getRecipeByID(mContext, recipeIndex).getIngredientsList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (this.ingredientsList == null) return 0;
        return this.ingredientsList.size();
    }

    // set ingredient to view 
    @Override
    public RemoteViews getViewAt(int position) {
        if (this.ingredientsList == null || this.ingredientsList.size() == 0) return null;

        RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), R.layout.ingredient_list_widget);

        String ingredient = this.ingredientsList.get(position).getIngredients();
        String measure = this.ingredientsList.get(position).getMeasure();
        double quantity = this.ingredientsList.get(position).getQuantity();

        String ingredient_text = String.format("%.2f %s %s", quantity, measure, ingredient);
        remoteViews.setTextViewText(R.id.ingredient_textView, ingredient_text);

        // Pass the Ingredients ID as extra for Widget GridView
        Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
        intent.putExtra("position", MainActivity.recipeSelected);

        remoteViews.setOnClickFillInIntent(R.id.recipe_name_text, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
