package com.example.novelmanager.workers;


import android.app.Application;
import android.content.Context;
import android.util.Log;

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
        long startTime = System.currentTimeMillis();
        Log.d("Worker", "Synchronization started");
        try {
            String serverUrl = getInputData().getString("serverUrl");
            if (serverUrl != null) {
                NovelRepository repository = new NovelRepository((Application) getApplicationContext());
                repository.fetchNovelsFromServer(serverUrl);
            }
            Log.d("Worker", "Synchronization completed in " + (System.currentTimeMillis() - startTime) + " ms");
            return Result.success();
        } catch (Exception e) {
            Log.e("Worker", "Error in synchronization", e);
            return Result.failure();
        }
    }

}
