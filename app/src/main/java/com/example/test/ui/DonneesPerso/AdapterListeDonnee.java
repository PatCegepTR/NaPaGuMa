package com.example.test.ui.DonneesPerso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test.InterfaceAdapter;
import com.example.test.LesDonnees;
import com.example.test.R;
import com.example.test.infoSante.AdapterListeArticle;

import java.util.List;

public class AdapterListeDonnee extends RecyclerView.Adapter {

    List<LesDonnees> listeDonnees;
    InterfaceAdapter monInterface;

    public AdapterListeDonnee(List<LesDonnees> liste, InterfaceAdapter unInterface )
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
