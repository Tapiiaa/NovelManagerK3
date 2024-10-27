package com.example.novelmanager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.novelmanager.model.Novel;

import java.util.ArrayList;
import java.util.List;

public class NovelDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "novels.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "novels";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_YEAR = "year";

    public NovelDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_AUTHOR + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_YEAR + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNovel(Novel novel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, novel.getTitle());
        values.put(COLUMN_AUTHOR, novel.getAuthor());
        values.put(COLUMN_GENRE, novel.getGenre());
        values.put(COLUMN_YEAR, novel.getYear());

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
                novels.add(new Novel(title, author, genre, year));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return novels;
    }
}
