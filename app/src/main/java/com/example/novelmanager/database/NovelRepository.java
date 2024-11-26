package com.example.novelmanager.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.novelmanager.model.Novel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NovelRepository {

    private NovelDao novelDao;
    private LiveData<List<Novel>> allNovels;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

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

    // Método para realizar una llamada remota para cargar novelas desde un servidor
    public void fetchNovelsFromServer(String serverUrl) {
        executorService.execute(() -> {
            try {
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept-Encoding", "gzip"); // Solicita respuesta comprimida
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

                // Aquí procesarías el resultado JSON en novelas y las guardarías localmente
                parseAndSaveNovels(result.toString());

            } catch (Exception e) {
                e.printStackTrace();
                // Manejar errores de red, como mostrar un mensaje al usuario
            }
        });
    }

    // Método hipotético para parsear JSON y guardar novelas localmente
    private void parseAndSaveNovels(String jsonResponse) {
        // Convertir jsonResponse a objetos Novel y guardarlos usando insert()
    }
}

