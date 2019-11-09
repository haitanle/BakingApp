package com.example.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.ui.MainActivity;

public class WidgetIntentService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS = "com.example.bakingApp.action.update_ingredients";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WidgetIntentService() {
        super("WidgetIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
        final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)){
                handleWidgetUpdateAction();
            }
        }
    }

    private void handleWidgetUpdateAction() {
        // pass recipe data to RecipeWidgetProvider
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        // pass data to RecipeWidgeProvider
        RecipeWidgetProvider.updateRecipeWidgets(this, widgetManager, appWidgetIds);
    }

    public static void startActionUpdateIngredients(Context context){
        Intent updateIntent = new Intent(context, WidgetIntentService.class);
        updateIntent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(updateIntent);
    }
}
