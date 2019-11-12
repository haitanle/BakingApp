package com.example.bakingapp.ui;

/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Steps;

import java.util.ArrayList;
import java.util.List;

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}


class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
    private Context mContext;

    List<Steps> stepList;

    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.

//        try {
//            for (int i = 0; i < Recipe.getRecipeByID(mContext, MainActivity.recipeSelected).getStepsList().size(); i++) {
//                mWidgetItems.add(new WidgetItem(Recipe.getRecipeByID(mContext, MainActivity.recipeSelected).getStepsList().get(i).getShortDescription()));
//            }
//        } catch (NullPointerException e){
//            for (int i = 0; i < Recipe.getRecipeByID(mContext, 1).getStepsList().size(); i++) {
//                mWidgetItems.add(new WidgetItem(Recipe.getRecipeByID(mContext, 1).getStepsList().get(i).getShortDescription()));
//            }
//        }
//        // We sleep for 3 seconds here to show how the empty view appears in the interim.
//        // The empty view is set in the StackWidgetProvider and should be a sibling of the
//        // collection view.
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        mWidgetItems.clear();
    }
    public int getCount() {
        int count = 1;

        try {
            count = stepList.size();
        } catch (NullPointerException e) {
            count = 1;
        }

        return count;
    }

    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.
        if (stepList == null || stepList.size() == 0) return null;

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        rv.setTextViewText(R.id.widget_item, stepList.get(position).getShortDescription());

        // Next, we set a fill-intent which will be used to fill-in the pending intent template
        // which is set on the collection view in StackWidgetProvider.
//        Bundle extras = new Bundle();
//        extras.putInt(RecipeWidgetProvider.EXTRA_ITEM, position);
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtras(extras);
//        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);
//
//        try {
//            System.out.println("Loading view " + position);
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // Return the remote views object.
        return rv;
    }


    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }
    public int getViewTypeCount() {
        return 1;
    }
    public long getItemId(int position) {
        return position;
    }
    public boolean hasStableIds() {
        return true;
    }
    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged

        stepList = Recipe.getRecipeByID(mContext,MainActivity.recipeSelected).getStepsList();

    }
}