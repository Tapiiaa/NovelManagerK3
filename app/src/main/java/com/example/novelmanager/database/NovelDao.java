package com.example.novelmanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.novelmanager.model.Novel;

import java.util.List;

@Dao
public interface NovelDao {

    @Insert
    void insert(Novel novel);

    @Update
    void update(Novel novel);

    @Delete
    void delete(Novel novel);

    @Query("DELETE FROM novel_table")
    void deleteAllNovels();

    @Query("SELECT * FROM novel_table ORDER BY title ASC")
    LiveData<List<Novel>> getAllNovels();
}


