package com.example.novelmanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelmanager.R;
import com.example.novelmanager.model.Novel;

import java.util.ArrayList;
import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelViewHolder> {

    private List<Novel> novels = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NovelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.novel_item, parent, false);
        return new NovelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelViewHolder holder, int position) {
        Novel currentNovel = novels.get(position);
        holder.textViewTitle.setText(currentNovel.getTitle());
        holder.textViewAuthor.setText(currentNovel.getAuthor());
    }

    @Override
    public int getItemCount() {
        return novels.size();
    }

    public void setNovels(List<Novel> novels) {
        this.novels = novels;
        notifyDataSetChanged();
    }

    public Novel getNovelAt(int position) {
        return novels.get(position);
    }

    class NovelViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAuthor;

        public NovelViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAuthor = itemView.findViewById(R.id.text_view_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(novels.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Novel novel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
