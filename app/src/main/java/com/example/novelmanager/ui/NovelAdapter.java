package com.example.novelmanager.ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelmanager.R;
import com.example.novelmanager.model.Novel;

import java.util.List;



public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelViewHolder> {

    private List<Novel> novelList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public NovelAdapter(List<Novel> novelList, OnItemClickListener listener) {
        this.novelList = novelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NovelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.novel_item, parent, false);
        return new NovelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelViewHolder holder, int position) {
        Novel novel = novelList.get(position);
        holder.textViewTitle.setText(novel.getTitle());
        holder.textViewAuthor.setText(novel.getAuthor());
        holder.textViewGenre.setText(novel.getGenre());
        holder.textViewYear.setText(String.valueOf(novel.getYear()));

        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
        holder.favoriteButton.setOnClickListener(v -> markAsFavorite(novel));
        holder.deleteButton.setOnClickListener(v -> deleteNovel(position));
    }

    @Override
    public int getItemCount() {
        return novelList.size();
    }

    public Novel getNovelAtPosition(int position) {
        return novelList.get(position);
    }

    private void markAsFavorite(Novel novel) {
        novel.setFavorite(true); // Marca como favorita en el modelo
        //Actualizar bbdd
        int position = novelList.indexOf(novel);

    }

    private void deleteNovel(int position) {
        novelList.remove(position);
        notifyItemRemoved(position);
    }

    public void setNovels(List<Novel> novels) {
        this.novelList = novels;
    }

    public class NovelViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewAuthor, textViewGenre, textViewYear;
        ImageButton favoriteButton, deleteButton;

        public NovelViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textTitle);
            textViewAuthor = itemView.findViewById(R.id.textAuthor);
            textViewGenre = itemView.findViewById(R.id.textGenre);
            textViewYear = itemView.findViewById(R.id.textYear);
            favoriteButton = itemView.findViewById(R.id.buttonFavorite);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
