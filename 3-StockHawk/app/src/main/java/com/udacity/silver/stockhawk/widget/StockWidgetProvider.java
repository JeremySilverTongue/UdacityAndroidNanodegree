package com.udacity.silver.stockhawk.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.udacity.silver.stockhawk.MainActivity;
import com.udacity.silver.stockhawk.R;
import com.udacity.silver.stockhawk.GraphActivity;
import com.udacity.silver.stockhawk.sync.QuoteSyncJob;


public class StockWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);


            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);


            Intent template = new Intent(context, GraphActivity.class);
            PendingIntent pendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(template)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_list, pendingIntentTemplate);


            views.setRemoteAdapter(R.id.widget_list, new Intent(context, WidgetRemoteViewsService.class));
            views.setEmptyView(R.id.widget_list, R.id.empty);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }


    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        if (QuoteSyncJob.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        }
    }


}
