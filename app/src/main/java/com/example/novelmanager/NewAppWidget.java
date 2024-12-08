package com.example.novelmanager;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.novelmanager.database.NovelDatabaseHelper;
import com.example.novelmanager.model.Novel;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            if (shouldUpdateWidget(context)) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        Log.d("Widget", "Widget enabled");
    }

    @Override
    public void onDisabled(Context context) {
        Log.d("Widget", "Widget disabled");
    }

    private boolean shouldUpdateWidget(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        long lastUpdate = prefs.getLong("lastUpdate", 0);
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate > 3600000) { // Update every hour
            prefs.edit().putLong("lastUpdate", currentTime).apply();
            return true;
        }
        return false;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        NovelDatabaseHelper databaseHelper = new NovelDatabaseHelper(context);
        List<Novel> novels = databaseHelper.getAllNovels();

        StringBuilder novelsList = new StringBuilder();
        for (Novel novel : novels) {
            novelsList.append(novel.getTitle()).append("\n");
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, novelsList.length() > 0 ? novelsList.toString() : "No novels available");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
