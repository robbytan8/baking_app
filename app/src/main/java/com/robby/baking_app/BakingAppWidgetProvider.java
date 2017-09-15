package com.robby.baking_app;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * @author Robby Tan
 */

public class BakingAppWidgetProvider extends AppWidgetProvider {

    public static final String DATA_FETCHED="com.robby.baking_app.DATA_FETCHED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            Intent serviceIntent = new Intent(context, RecipeFetchService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    appWidgetIds[i]);
            context.startService(serviceIntent);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(DATA_FETCHED)) {
            int appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
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
        remoteViews.setRemoteAdapter(appWidgetId, R.id.spin_widget_recipe,
                svcIntent);
        // setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.spin_widget_recipe, R.id.tv_widget_recipe_ingredient);
        return remoteViews;
    }
}
