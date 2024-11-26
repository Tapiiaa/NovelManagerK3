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
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Recuperar todas las novelas de la base de datos
        NovelDatabaseHelper databaseHelper = new NovelDatabaseHelper(context);
        List<Novel> novels = databaseHelper.getAllNovels();

        // Crear un string con todas las novelas
        StringBuilder novelsList = new StringBuilder();
        for (Novel novel : novels) {
            novelsList.append(novel.getTitle()).append("\n");
        }

        // Configurar la vista del widget
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, novelsList.toString());

        // Agregar funcionalidad al widget: abrir la aplicación al hacer clic
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Actualizar el widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

