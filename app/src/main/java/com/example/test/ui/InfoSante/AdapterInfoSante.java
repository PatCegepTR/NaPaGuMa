package com.example.test.ui.InfoSante;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.InterfaceAdapter;
import com.example.test.LesDonnees;
import com.example.test.R;
import com.example.test.ui.DonneesPerso.AdapterListeDonnee;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterInfoSante extends RecyclerView.Adapter{

    List<InfoSante> listeArticlesInfoSante;
    InterfaceInfoSante monInterfaceInfoSante;

    public AdapterInfoSante(List<InfoSante> listeArticlesInfoSante, InterfaceInfoSante monInterfaceInfoSante) {
        this.listeArticlesInfoSante = listeArticlesInfoSante;
        this.monInterfaceInfoSante = monInterfaceInfoSante;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_info_sante,parent,false);
        return new ViewHolderInfoSante(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderInfoSante viewHolderInfoSante = (ViewHolderInfoSante) holder;
        viewHolderInfoSante.tvArticleTitre.setText(listeArticlesInfoSante.get(position).getArticleInfoSanteTitre());
        viewHolderInfoSante.tvArticleDate.setText(listeArticlesInfoSante.get(position).getArticleInfoSanteDate());

        Picasso.get().load(listeArticlesInfoSante.get(position).getArticleInfoSanteLienImage()).into(viewHolderInfoSante.ivArticleImage);
    }

    @Override
    public int getItemCount() {
        return listeArticlesInfoSante.size();
    }

    public class ViewHolderInfoSante extends RecyclerView.ViewHolder
    {
        TextView tvArticleTitre, tvArticleDate;
        ImageView ivArticleImage;

        public ViewHolderInfoSante(@NonNull View itemView) {
            super(itemView);

            tvArticleTitre = itemView.findViewById(R.id.tvTitreInfoSante);
            tvArticleDate = itemView.findViewById(R.id.tvDateInfoSante);
            ivArticleImage = itemView.findViewById(R.id.ivInfoSante);
        }
    }
}
