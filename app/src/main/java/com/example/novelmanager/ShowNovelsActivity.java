// ShowNovelsActivity.java
package com.example.novelmanager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelmanager.model.Novel;
import com.example.novelmanager.ui.NovelAdapter;

import java.util.List;

public class ShowNovelsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NovelAdapter novelAdapter;
    private NovelDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_novels);

        recyclerView = findViewById(R.id.recyclerViewNovels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new NovelDatabaseHelper(this);
        List<Novel> novels = databaseHelper.getAllNovels();

        novelAdapter = new NovelAdapter(novels);
        recyclerView.setAdapter(novelAdapter);
    }
}
