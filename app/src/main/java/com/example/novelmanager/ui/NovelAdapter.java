package com.example.novelmanager.ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelmanager.R;
import com.example.novelmanager.model.Novel;

import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelViewHolder> {

    private List<Novel> novelList;

    public NovelAdapter(List<Novel> novelList) {
        this.novelList = novelList;
    }

    @NonNull
    @Override
    public NovelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.novel_item, parent, false);
        return new NovelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelViewHolder holder, int position) {
        Novel novel = novelList.get(position);
        holder.titleTextView.setText(novel.getTitle());
        holder.authorTextView.setText(novel.getAuthor());
        holder.genreTextView.setText(novel.getGenre());
        holder.yearTextView.setText(String.valueOf(novel.getYear()));
    }

    @Override
    public int getItemCount() {
        return novelList.size();
    }

    public static class NovelViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        TextView genreTextView;
        TextView yearTextView;

        public NovelViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textTitle);
            authorTextView = itemView.findViewById(R.id.textAuthor);
            genreTextView = itemView.findViewById(R.id.textGenre);
            yearTextView = itemView.findViewById(R.id.textYear);
        }
    }
}
