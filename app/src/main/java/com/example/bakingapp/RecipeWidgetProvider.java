package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bakingapp.ui.MainActivity;
import com.example.bakingapp.ui.RecipeDetailsActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//
//        // create the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
//
//        views.setTextViewText(R.id.recipe_name_text, widgetText);
//
//        // Create the Intent on click
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        views.setOnClickPendingIntent(R.id.recipe_name_text, pendingIntent);

        RemoteViews views = getWidgetGridview(context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static RemoteViews getWidgetGridview(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        // create the Intent to call the GridViewService for data
        Intent intent = new Intent(context, GridViewService.class);
        remoteViews.setRemoteAdapter(R.id.widget_gridview, intent);

        // Intent to open the RecipeDetail Activity
        Intent detailIntent = new Intent(context, RecipeDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.ingredients_grid_view, pendingIntent);

        return remoteViews;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

