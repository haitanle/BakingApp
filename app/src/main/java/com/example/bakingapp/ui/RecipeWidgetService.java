package com.example.bakingapp.ui;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.bakingapp.RecipeWidgetProvider;

public class RecipeWidgetService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE_WIDGETS = "com.example.android.recipewidget.update_recipe_widget";


    public RecipeWidgetService(String name) {
        super("RecipeWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent !=null){
            if (intent.getAction().equals(ACTION_UPDATE_RECIPE_WIDGETS)){
                handleActionUpdateRecipeWidget();
            }
        }
    }

    private void handleActionUpdateRecipeWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeWidget(this, appWidgetManager, appWidgetIds);
    }

    public static void startActionUpdateRecipeWidget(Context context){
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(intent);
    }




}


