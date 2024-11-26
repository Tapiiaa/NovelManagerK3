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

        // Inicializar NovelAdapter
        novelAdapter = new NovelAdapter();
        novelAdapter.setOnItemClickListener(this);
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
        // Manejar clics en los elementos de la lista
        Bundle bundle = new Bundle();
        bundle.putString("title", novel.getTitle());
        bundle.putString("author", novel.getAuthor());
        bundle.putString("genre", novel.getGenre());
        bundle.putInt("year", novel.getYear());

        // Crear el fragmento de detalles y pasarle los datos
        NovelDetailFragment detailFragment = new NovelDetailFragment();
        detailFragment.setArguments(bundle);

        // Reemplazar el fragmento actual por el de detalles
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null) // Permite volver al fragmento anterior con el botón "Atrás"
                .commit();
    }

}
