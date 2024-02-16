package com.example.test.ui.DonneesPerso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test.InterfaceAdapter;
import com.example.test.LesDonnees;
import com.example.test.R;

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
        View view = inflater.inflate(R.layout.layout_donnees_perso,parent,false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MonViewHolder monViewHolder = (MonViewHolder) holder;
        monViewHolder.tvCardiaque.setText(listeDonnees.get(position).getRythmeCardiaqueString());
        monViewHolder.tvO2.setText(listeDonnees.get(position).getSaturationO2String());
    }

    @Override
    public int getItemCount() {
        return listeDonnees.size();
    }

    public class MonViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvCardiaque, tvO2;
        public MonViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCardiaque = itemView.findViewById(R.id.tvO2);
            tvO2 = itemView.findViewById(R.id.tvCardiaque);
        }
    }
}
