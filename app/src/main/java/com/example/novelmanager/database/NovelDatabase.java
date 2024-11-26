package com.example.novelmanager.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

import com.example.novelmanager.model.Novel;

@Database(entities = {Novel.class}, version = 1, exportSchema = false)
public abstract class NovelDatabase extends RoomDatabase {

    public abstract NovelDao novelDao();

    private static volatile NovelDatabase INSTANCE;

    public static NovelDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (NovelDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NovelDatabase.class, "novel_database")
                            .fallbackToDestructiveMigration() // Evita problemas de migración
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Callback público con métodos públicos
    public static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    // Lógica para inicializar datos en la base de datos si es necesario
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    // Lógica opcional al abrir la base de datos
                }
            };
}
