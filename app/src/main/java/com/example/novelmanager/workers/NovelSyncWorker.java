package com.example.novelmanager.workers;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.novelmanager.database.NovelRepository;

public class NovelSyncWorker extends Worker {

    public NovelSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            // Obtiene la URL del servidor de los datos de entrada
            String serverUrl = getInputData().getString("serverUrl");
            if (serverUrl != null) {
                NovelRepository repository = new NovelRepository((Application) getApplicationContext());
                repository.fetchNovelsFromServer(serverUrl);
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}

