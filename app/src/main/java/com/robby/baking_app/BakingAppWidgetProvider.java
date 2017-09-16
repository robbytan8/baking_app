package com.robby.baking_app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * @author Robby Tan
 */

public class BakingAppWidgetProvider extends AppWidgetProvider {

    public static final String DATA_FETCHED = "com.robby.baking_app.DATA_FETCHED";
    public static final String ACTION_LEFT = "com.robby.baking_app.ACTION_LEFT";
    public static final String ACTION_RIGHT = "com.robby.baking_app.ACTION_RIGHT";
    private static int showIndex = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            Intent serviceIntent = new Intent(context, RecipeFetchService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            context.startService(serviceIntent);
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent = new Intent(context, BakingAppWidgetProvider.class);
        intent.setAction(ACTION_LEFT);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context,
                AppWidgetManager.INVALID_APPWIDGET_ID, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.ib_app_widget_left, actionPendingIntent);

        intent = new Intent(context, BakingAppWidgetProvider.class);
        intent.setAction(ACTION_RIGHT);
        actionPendingIntent = PendingIntent.getBroadcast(context,
                AppWidgetManager.INVALID_APPWIDGET_ID, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.ib_app_widget_right, actionPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        if (intent.getAction().equals(DATA_FETCHED)) {
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        } else if (intent.getAction().equals(ACTION_LEFT)) {
            moveDataForwardOrBackward(-1, remoteViews);
        } else if (intent.getAction().equals(ACTION_RIGHT)) {
            moveDataForwardOrBackward(1, remoteViews);
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        // which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);
        // RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, RecipeFetchService.class);
        // passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // setting a unique Uri to the intent
        // don't know its purpose to me right now
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        // setting adapter to listview of the widget
//        remoteViews.setRemoteAdapter(appWidgetId, R.id.spin_widget_recipe,
//                svcIntent);
        // setting an empty view in case of no data
//        remoteViews.setEmptyView(R.id.spin_widget_recipe, R.id.tv_widget_recipe_ingredient);
        return remoteViews;
    }

    private void moveDataForwardOrBackward(int movement, RemoteViews remoteViews) {
        //  Move data forward
        if (movement == 1) {
            if (showIndex < RecipeFetchService.getRecipes().size() - 1) {
                showIndex++;
            }
        }
        //  Move data backward
        else if (movement == -1) {
            if (showIndex > 0) {
                showIndex--;
            }
        }
        Log.d("Text Output Test", RecipeFetchService.getRecipes().get(showIndex).getName());
        remoteViews.setTextViewText(R.id.tv_app_widget_recipe_name,
                RecipeFetchService.getRecipes().get(showIndex).getName());
        remoteViews.setTextViewText(R.id.tv_app_widget_recipe_ingredients,
                RecipeFetchService.getRecipes().get(showIndex).getIngredientsInString());
    }
}
