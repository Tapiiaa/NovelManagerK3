package com.example.novelmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.novelmanager.model.Novel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NovelDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "novels.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "novels";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_IS_FAVORITE = "isFavorite";
    private final WeakReference<Context> contextRef;

    public NovelDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.contextRef = new WeakReference<>(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_GENRE + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0");
        }
    }

    public void addNovel(Novel novel, boolean isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, novel.getTitle());
        values.put(COLUMN_AUTHOR, novel.getAuthor());
        values.put(COLUMN_GENRE, novel.getGenre());
        values.put(COLUMN_YEAR, novel.getYear());
        values.put(COLUMN_IS_FAVORITE, isFavorite ? 1 : 0);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Novel> getAllNovels() {
        List<Novel> novels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1;
                Novel novel = new Novel(title, author, genre, year);
                novel.setFavorite(isFavorite);
                novels.add(novel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return novels;
    }

    public void updateNovelFavoriteStatus(String title, boolean isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_FAVORITE, isFavorite ? 1 : 0);

        String whereClause = COLUMN_TITLE + "=?";
        String[] whereArgs = {title};

        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    public List<Novel> getFavoriteNovels() {
        List<Novel> novels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_IS_FAVORITE + "=?";
        String[] selectionArgs = {"1"};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR));
                Novel novel = new Novel(title, author, genre, year);
                novel.setFavorite(true);
                novels.add(novel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return novels;
    }
}
