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
    private OnItemClickListener listener;

    public void setNovels(List<Novel> novels) {
        this.novelList = novels;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Novel novel);
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
        holder.bind(novel, listener);
    }

    @Override
    public int getItemCount() {
        return novelList != null ? novelList.size() : 0;
    }

    public static class NovelViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView authorTextView;
        private final TextView genreTextView;
        private final TextView yearTextView;

        public NovelViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            authorTextView = itemView.findViewById(R.id.text_view_author);
            genreTextView = itemView.findViewById(R.id.text_view_genre);
            yearTextView = itemView.findViewById(R.id.text_view_year);
        }

        public void bind(Novel novel, OnItemClickListener listener) {
            titleTextView.setText(novel.getTitle());
            authorTextView.setText(novel.getAuthor());
            genreTextView.setText(novel.getGenre());
            yearTextView.setText(String.valueOf(novel.getYear()));

            itemView.setOnClickListener(v -> listener.onItemClick(novel));
        }
    }
}


