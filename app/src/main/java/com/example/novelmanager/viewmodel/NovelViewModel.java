package com.example.novelmanager.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.novelmanager.database.NovelRepository;
import com.example.novelmanager.model.Novel;

import java.util.List;

public class NovelViewModel extends AndroidViewModel {

    private NovelRepository repository;
    private LiveData<List<Novel>> allNovels;

    public NovelViewModel(@NonNull Application application) {
        super(application);
        repository = new NovelRepository(application);
        allNovels = repository.getAllNovels();
    }

    public void insert(Novel novel) {
        repository.insert(novel);
    }

    public void update(Novel novel) {
        repository.update(novel);
    }

    public void delete(Novel novel) {
        repository.delete(novel);
    }

    public void deleteAllNovels() {
        repository.deleteAllNovels();
    }

    public LiveData<List<Novel>> getAllNovels() {
        return allNovels;
    }
}

