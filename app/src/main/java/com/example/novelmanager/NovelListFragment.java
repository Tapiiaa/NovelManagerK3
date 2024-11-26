package com.example.novelmanager;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.novelmanager.model.Novel;
import com.example.novelmanager.ui.NovelAdapter;
import com.example.novelmanager.viewmodel.NovelViewModel;

import java.util.ArrayList;
import java.util.List;

public class NovelListFragment extends Fragment implements NovelAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private NovelAdapter novelAdapter;
    private NovelViewModel novelViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel_list, container, false);

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        // Inicializar NovelAdapter con lista vacía y listener
        novelAdapter = new NovelAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(novelAdapter);

        // Configurar ViewModel
        novelViewModel = new ViewModelProvider(requireActivity()).get(NovelViewModel.class);
        novelViewModel.getAllNovels().observe(getViewLifecycleOwner(), new Observer<List<Novel>>() {
            @Override
            public void onChanged(List<Novel> novels) {
                novelAdapter.setNovels(novels);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(Novel novel) {
        // Mostrar un mensaje dependiendo del género
        String genreMessage;
        switch (novel.getGenre().toLowerCase()) {
            case "ficción":
                genreMessage = "¡Ficción! Un género lleno de creatividad y aventuras.";
                break;
            case "terror":
                genreMessage = "¡Terror! Prepárate para historias escalofriantes.";
                break;
            case "romance":
                genreMessage = "¡Romance! Historias llenas de amor y emoción.";
                break;
            case "fantasía":
                genreMessage = "¡Fantasía! Mundos mágicos te esperan.";
                break;
            default:
                genreMessage = "Un género interesante: " + novel.getGenre();
        }

        Toast.makeText(requireContext(), genreMessage, Toast.LENGTH_SHORT).show();

        // Navegar al fragmento de detalles como antes
        Bundle bundle = new Bundle();
        bundle.putString("title", novel.getTitle());
        bundle.putString("author", novel.getAuthor());
        bundle.putString("genre", novel.getGenre());
        bundle.putInt("year", novel.getYear());

        NovelDetailFragment detailFragment = new NovelDetailFragment();
        detailFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

}

