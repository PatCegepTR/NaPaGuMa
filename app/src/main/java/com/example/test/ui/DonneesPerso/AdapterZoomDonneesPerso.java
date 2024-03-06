package com.example.test.ui.DonneesPerso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;

public class AdapterZoomDonneesPerso extends RecyclerView.Adapter {
    // Variables.
    List<LesDonnees> listeDonneesJour;

    // Constructeur de l'adaptateur des données selon une journée spécifique.
    public AdapterZoomDonneesPerso(List<LesDonnees> listeDonneesJour)
    {
        this.listeDonneesJour = listeDonneesJour;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_zoom_carte_donnees_perso,parent,false);
        return new AdapterZoomDonneesPerso.MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MonViewHolder monViewHolder = (MonViewHolder) holder;
        monViewHolder.tvZoomRC.setText(listeDonneesJour.get(position).getRythmeCardiaqueString());
        monViewHolder.tvZoomSO.setText(listeDonneesJour.get(position).getSaturationO2String());
        monViewHolder.tvZoomHeure.setText(listeDonneesJour.get(position).getHeure());
    }

    @Override
    public int getItemCount() {
        return listeDonneesJour.size();
    }

    // Supprime une donnée spécifique d'un jour.
    public void supprimerDonnee(int position){
        listeDonneesJour.remove(listeDonneesJour.get(position));
        notifyItemRemoved(position);
    }

    // ViewHolder des données prisent à des heures spécifiques.
    public class MonViewHolder extends RecyclerView.ViewHolder
    {
        // Variables.
        TextView tvZoomRC, tvZoomSO, tvZoomHeure;

        // Constructeur du ViewHolder.
        public MonViewHolder(@NonNull View itemView) {
            super(itemView);

            tvZoomRC = itemView.findViewById(R.id.tvZoomJourneeRC);
            tvZoomSO = itemView.findViewById(R.id.tvZoomJourneeSO);
            tvZoomHeure = itemView.findViewById(R.id.tvZoomJourneeHeure);

            // Gestion du long clic, qui supprime une donnée.
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    supprimerDonnee(getLayoutPosition());
                    return false;
                }
            });
        }
    }
}
