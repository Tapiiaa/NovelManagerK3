package com.example.novelmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.novelmanager.model.Novel;
import com.example.novelmanager.preferences.SettingsActivity;
import com.example.novelmanager.ui.NovelAdapter;
import com.example.novelmanager.viewmodel.NovelViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonAddBook;
    private Button buttonSettings;
    private RecyclerView recyclerView;
    private NovelViewModel novelViewModel;
    private NovelAdapter novelAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddBook = findViewById(R.id.buttonAddBook);
        buttonSettings = findViewById(R.id.buttonSettings);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        novelAdapter = new NovelAdapter();
        recyclerView.setAdapter(novelAdapter);

        novelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);

        novelViewModel.getAllNovels().observe(this, new Observer<List<Novel>>() {
            @Override
            public void onChanged(List<Novel> novels) {
                novelAdapter.setNovels(novels);
            }
        });

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para agregar novela
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences("com.example.novelmanager_preferences", MODE_PRIVATE);
        applyUserSettings();
    }

    private void applyUserSettings() {
        boolean darkMode = sharedPreferences.getBoolean("dark_mode", false);
        if (darkMode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }
}
