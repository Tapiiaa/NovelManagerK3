package com.example.novelmanager.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        return novelList.size();
    }

    public static class NovelViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView authorTextView;
        private final TextView genreTextView;
        private final TextView yearTextView;
        private final ImageView imageView;

        public NovelViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            authorTextView = itemView.findViewById(R.id.text_view_author);
            genreTextView = itemView.findViewById(R.id.text_view_genre);
            yearTextView = itemView.findViewById(R.id.text_view_year);
            imageView = itemView.findViewById(R.id.image_view_cover);
        }

        public void bind(Novel novel, OnItemClickListener listener) {
            titleTextView.setText(novel.getTitle());
            authorTextView.setText(novel.getAuthor());
            genreTextView.setText(novel.getGenre());
            yearTextView.setText(String.valueOf(novel.getYear()));

            // Optimización de imágenes
            if (novel.getImagePath() != null && !novel.getImagePath().isEmpty()) {
                Bitmap bitmap = decodeSampledBitmapFromFile(novel.getImagePath(), 100, 150);
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.default_cover); // Imagen por defecto
            }

            itemView.setOnClickListener(v -> listener.onItemClick(novel));
        }

        // Método para escalar imágenes
        private Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(filePath, options);
        }

        // Calcular factor de escala
        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            int height = options.outHeight;
            int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                int halfHeight = height / 2;
                int halfWidth = width / 2;

                while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }
            return inSampleSize;
        }
    }
}

