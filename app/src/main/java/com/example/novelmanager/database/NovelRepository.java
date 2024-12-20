package com.example.novelmanager.database;



import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.novelmanager.model.Novel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NovelRepository {

    private NovelDao novelDao;
    private LiveData<List<Novel>> allNovels;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static final Map<String, String> responseCache = new HashMap<>();

    public NovelRepository(Application application) {
        NovelDatabase database = NovelDatabase.getInstance(application);
        novelDao = database.novelDao();
        allNovels = novelDao.getAllNovels();
    }

    public LiveData<List<Novel>> getAllNovels() {
        return allNovels;
    }

    public void insert(Novel novel) {
        executorService.execute(() -> novelDao.insert(novel));
    }

    public void update(Novel novel) {
        executorService.execute(() -> novelDao.update(novel));
    }

    public void delete(Novel novel) {
        executorService.execute(() -> novelDao.delete(novel));
    }

    public void deleteAllNovels() {
        executorService.execute(novelDao::deleteAllNovels);
    }

    public void fetchNovelsFromServer(String serverUrl) {
        executorService.execute(() -> {
            try {
                if (responseCache.containsKey(serverUrl)) {
                    Log.d("Network", "Using cached response");
                    parseAndSaveNovels(responseCache.get(serverUrl));
                    return;
                }
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept-Encoding", "gzip");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                connection.disconnect();
                responseCache.put(serverUrl, result.toString());
                parseAndSaveNovels(result.toString());
            } catch (Exception e) {
                Log.e("Network", "Error fetching data: " + e.getMessage());
            }
        });
    }

    private void parseAndSaveNovels(String jsonResponse) {
        // Conversion de JSON a objetos Novel no implementado.
    }
}


