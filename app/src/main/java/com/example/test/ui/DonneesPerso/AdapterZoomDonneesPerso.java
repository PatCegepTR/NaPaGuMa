package com.example.test.ui.DonneesPerso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.ui.Profil.Profil;
import com.example.test.ui.Profil.ProfilFragment;
import com.example.test.ui.Serveur.InterfaceServeur;
import com.example.test.ui.Serveur.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Boolean> call = serveur.supprimerDonnee(listeDonneesJour.get(position).getId());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
               boolean supprimer = response.body();

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
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
