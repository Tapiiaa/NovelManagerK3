package com.example.novelmanager;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.novelmanager.model.Novel;

import java.util.List;

public class NewAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            // Cargar las novelas favoritas (asegúrate de que esta función obtenga los datos de manera correcta)
            List<Novel> favoriteNovels = getFavoriteNovels(context);

            // Configurar RemoteViews para el widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            // Mostrar los títulos de hasta tres novelas favoritas
            if (favoriteNovels.size() > 0) {
                views.setTextViewText(R.id.favorite_novel_1, favoriteNovels.get(0).getTitle());
            }
            if (favoriteNovels.size() > 1) {
                views.setTextViewText(R.id.favorite_novel_2, favoriteNovels.get(1).getTitle());
            }
            if (favoriteNovels.size() > 2) {
                views.setTextViewText(R.id.favorite_novel_3, favoriteNovels.get(2).getTitle());
            }

            // Configurar el clic para redirigir a MainActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

            // Actualizar el widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private List<Novel> getFavoriteNovels(Context context) {

        NovelDatabaseHelper databaseHelper = new NovelDatabaseHelper(context);
        return databaseHelper.getFavoriteNovels(); // Asegúrate de tener un método que obtenga solo las favoritas
    }
}



