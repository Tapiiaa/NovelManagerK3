package com.example.novelmanager;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
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
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        updateAllWidgets(context);
    }

    @Override
    public void onDisabled(Context context) {
        clearWidgetData(context);
    }

    private void updateAllWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new android.content.ComponentName(context, NewAppWidget.class));
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void clearWidgetData(Context context) {
        NovelDatabaseHelper databaseHelper = new NovelDatabaseHelper(context);
        databaseHelper.getWritableDatabase().delete("novels", null, null);
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
