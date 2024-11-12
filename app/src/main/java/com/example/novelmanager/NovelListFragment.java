package com.example.novelmanager;

import android.os.Bundle;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        // Inicializa novelAdapter con una lista vacía y this como listener
        novelAdapter = new NovelAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(novelAdapter);

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
    public void onItemClick(int position) {
        // Maneja el clic en el elemento de la lista aquí
    }
}
