package com.example.novelmanager.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private final LruCache<String, Bitmap> imageCache;

    public NovelAdapter() {
        // Inicializamos LruCache con el 1/8 de la memoria disponible
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
    }

    public void setNovels(List<Novel> novels) {
        this.novels = novels;
        notifyDataSetChanged();
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
        Novel currentNovel = novels.get(position);

        holder.textViewTitle.setText(currentNovel.getTitle());
        holder.textViewAuthor.setText(currentNovel.getAuthor());
        holder.textViewGenre.setText(currentNovel.getGenre());
        holder.textViewYear.setText(String.valueOf(currentNovel.getYear()));

        // Cargar imagen eficientemente
        String imagePath = currentNovel.getImagePath(); // Asegúrate de que Novel tenga este atributo
        Bitmap bitmap = imageCache.get(imagePath);

        if (bitmap == null) {
            // Cargar y escalar la imagen solo si no está en caché
            bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 100, 150, false); // Ajusta dimensiones según tu diseño
                imageCache.put(imagePath, bitmap);
            }
        }
        holder.imageViewCover.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return novels.size();
    }

    class NovelViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewAuthor;
        private final TextView textViewGenre;
        private final TextView textViewYear;
        private final ImageView imageViewCover;

        public NovelViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAuthor = itemView.findViewById(R.id.text_view_author);
            textViewGenre = itemView.findViewById(R.id.text_view_genre);
            textViewYear = itemView.findViewById(R.id.text_view_year);
            imageViewCover = itemView.findViewById(R.id.image_view_cover);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(novels.get(position));
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
