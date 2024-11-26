// ShowNovelsActivity.java
package com.example.novelmanager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelmanager.database.NovelDatabaseHelper;
import com.example.novelmanager.model.Novel;
import com.example.novelmanager.ui.NovelAdapter;

import java.util.List;

public class ShowNovelsActivity extends AppCompatActivity implements NovelAdapter.OnItemClickListener {

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
        novelAdapter = new NovelAdapter(novels, this);
        recyclerView.setAdapter(novelAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Novel selectedNovel = novelAdapter.getNovelAtPosition(position);
        String genreMessage = getGenreMessage(selectedNovel.getGenre());
        Toast.makeText(this, genreMessage, Toast.LENGTH_SHORT).show();
    }

    private String getGenreMessage(String genre) {
        switch (genre.toLowerCase()) {
            case "terror":
                return "Esta novela es de Terror. Prepárate para asustarte!";
            case "comedia":
                return "Es una novela de Comedia. ¡Disfruta de las risas!";
            case "drama":
                return "Un drama intenso te espera en esta novela.";
            case "prueba":
                return "Estamos testeando la app, en breve desaparecera este genero";
            default:
                return "Este es un género interesante: " + genre;
        }
    }


}
