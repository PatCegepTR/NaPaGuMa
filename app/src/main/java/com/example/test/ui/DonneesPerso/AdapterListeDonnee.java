package com.example.test.ui.DonneesPerso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test.R;

import java.util.List;

public class AdapterListeDonnee extends RecyclerView.Adapter {
    // Variables.
    List<LesDonnees> listeDonnees;
    InterfaceDonneesPerso monInterfaceDonneesPerso;

    // Constructeur de l'adaptateur sur la liste de données personnelles.
    public AdapterListeDonnee(List<LesDonnees> liste, InterfaceDonneesPerso monInterfaceDonneesPerso )
    {
        listeDonnees = liste;
        this.monInterfaceDonneesPerso = monInterfaceDonneesPerso;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_donnees_perso,parent,false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MonViewHolder monViewHolder = (MonViewHolder) holder;
        monViewHolder.tvCardiaque.setText(listeDonnees.get(position).getRythmeCardiaqueString());
        monViewHolder.tvO2.setText(listeDonnees.get(position).getSaturationO2String());
        monViewHolder.tvDate.setText(listeDonnees.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return listeDonnees.size();
    }

    // ViewHolder pour les données personnelles par jour.
    public class MonViewHolder extends RecyclerView.ViewHolder
    {
        // Variables.
        TextView tvCardiaque, tvO2, tvDate;

        // Constructeur du ViewHolder.
        public MonViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCardiaque = itemView.findViewById(R.id.tvCardiaque);
            tvO2 = itemView.findViewById(R.id.tvO2);
            tvDate = itemView.findViewById(R.id.tvListeCardDate);

            // Gestion du clic qui zoom sur une journée spécifique.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monInterfaceDonneesPerso.gestionClicZoom(getLayoutPosition(), listeDonnees.get(getLayoutPosition()));
                }
            });
        }
    }
}
