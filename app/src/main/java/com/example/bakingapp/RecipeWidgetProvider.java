package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.bakingapp.ui.GridWidgetService;
import com.example.bakingapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";

    public static final String ACTION_UPDATE_RECIPE_WIDGETS = "com.example.android.recipewidget.update_recipe_widget";

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);

            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    // setOnClick for each widget to launch MainActivity.class
    //
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Widgets allow click handlers to only launch pending intents;
        views.setOnClickPendingIntent(R.id.empty_view, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update each of the widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {

//            Intent intent = new Intent(context, GridWidgetService.class);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
//
//            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
//
//            // set Intent's data with a URI
//            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//
//            // Get a connection to the RemoteView/Widget
//            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
//
//            // Create a Intent to the GridWidgetService
//            Intent toastIntent = new Intent(context, GridWidgetService.class);
//
//            // set the service to Action called Toast
//            toastIntent.setAction(RecipeWidgetProvider.TOAST_ACTION);
//
//            // put data the service for the Widget's id
//            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
//
//            // Create a Pending Intent to open GridWidgetService
//            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            // set Pending Intent to GridWidgetService to the widget
//            rv.setPendingIntentTemplate(R.id.widget_gridview, toastPendingIntent);

            // Update the widget
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){

        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

