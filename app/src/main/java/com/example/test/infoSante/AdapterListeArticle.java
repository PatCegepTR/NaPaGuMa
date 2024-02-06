package com.example.test.infoSante;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;

public class AdapterListeArticle extends RecyclerView.Adapter {

    List<Article> listeArticles;


    public AdapterListeArticle(List<Article> listeArticles){
        this.listeArticles = listeArticles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_info_sante,parent,false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MonViewHolder monViewHolder = (MonViewHolder) holder;

        Article article = listeArticles.get(position);

        monViewHolder.tvTitre.setText(listeArticles.get(position).getTitre());
        monViewHolder.tvDate.setText(listeArticles.get(position).getDate());
        monViewHolder.ivImage.setImageResource(R.drawable.ic_profil);
    }

    @Override
    public int getItemCount() {
        return listeArticles.size();
    }


    public class MonViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitre, tvDate;
        ImageView ivImage;
        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitre = itemView.findViewById(R.id.tvArticleTitreCarte);
            tvDate = itemView.findViewById(R.id.tvArticleDateCarte);
            ivImage = itemView.findViewById(R.id.ivArticleImageCarte);

        }
    }
}
