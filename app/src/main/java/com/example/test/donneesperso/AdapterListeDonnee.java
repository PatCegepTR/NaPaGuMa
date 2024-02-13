package com.example.test.donneesperso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test.R;
import com.example.test.infoSante.AdapterListeArticle;

import java.util.List;

public class AdapterListeDonnee extends RecyclerView.Adapter {

    public interface MonInterface{
        public void gestionClic(int position, Donnee donnee);
    }

    List<Donnee> listeDonnees;
    MonInterface monInterface;

    public AdapterListeDonnee(List<Donnee> liste, MonInterface unInterface )
    {
        listeDonnees = liste;
        monInterface = unInterface;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_journee_donnees_perso,parent,false);
        return new AdapterListeDonnee.MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AdapterListeArticle.MonViewHolder monViewHolder = (AdapterListeArticle.MonViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return listeDonnees.size();
    }

    public class MonViewHolder extends RecyclerView.ViewHolder
    {

        ImageView ivImage;
        public MonViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
