package com.example.test.ui.InfoSante;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class InfoSanteFragment extends Fragment implements InterfaceInfoSante{

    // Variables.
    RecyclerView recyclerViewInfoSante;
    AdapterInfoSante adapterInfoSante;
    List<InfoSante> listeArticlesInfoSante = new ArrayList<InfoSante>();

    AlertDialog adArticleZoom;

    // Constructeur du fragment de notre section Info Santé.
    public InfoSanteFragment() {
        // Constructeur vide requis.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_sante, container, false);
    }

    // Insertion des articles à l'adaptateur et de l'adaptateur au RecyclerView.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        recyclerViewInfoSante = view.findViewById(R.id.rvInfoSante);
        recyclerViewInfoSante.setHasFixedSize(true);
        recyclerViewInfoSante.setLayoutManager(new LinearLayoutManager(context));

        listeArticlesInfoSante.add(new InfoSante(getString(R.string.titre_card_article1), getString(R.string.date_card_article1), "https://www.docteurclic.com/galerie-photos/image_3191_400.jpg", getString(R.string.lien_card_article1), getString(R.string.description_card_article1)));
        listeArticlesInfoSante.add(new InfoSante(getString(R.string.titre_card_article2), getString(R.string.date_card_article1), "https://static.tuasaude.com/media/article/hr/yh/saturacao-de-oxigenio_54743_l.webp", getString(R.string.lien_card_article2), getString(R.string.description_card_article2)));
        listeArticlesInfoSante.add(new InfoSante(getString(R.string.titre_card_article3), getString(R.string.date_card_article1), "https://www.mobcall.com/images/clicktocall.png", getString(R.string.lien_card_article3), getString(R.string.description_card_article3)));

        adapterInfoSante = new AdapterInfoSante(listeArticlesInfoSante, this);

        recyclerViewInfoSante.setAdapter(adapterInfoSante);

    }

    // Création d'une boite de dialogue qui va faire un zoom sur un article.
    public void GestionClicArticle(int position, InfoSante article){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View articleZoomView = getLayoutInflater().inflate(R.layout.layout_article_info_sante, null);
        builder.setView(articleZoomView);

        TextView tvArticleZoomTitre = articleZoomView.findViewById(R.id.tvZoomArticleTitre);
        TextView tvArticleZoomDescription = articleZoomView.findViewById(R.id.tvZoomArticleDescription);
        TextView tvArticleZoomLien = articleZoomView.findViewById(R.id.tvZoomArticleLien);
        ImageView ivArticleZoomImageLien = articleZoomView.findViewById(R.id.ivZoomArticleImage);

        tvArticleZoomTitre.setText(article.getArticleInfoSanteTitre());
        tvArticleZoomDescription.setText(article.getArticleInfoSanteDescription());
        tvArticleZoomLien.setText(article.getArticleInfoSanteLien());
        tvArticleZoomLien.setAutoLinkMask(Linkify.WEB_URLS);

        Picasso.get().load(article.getArticleInfoSanteLienImage()).into(ivArticleZoomImageLien);

        adArticleZoom = builder.create();
        adArticleZoom.show();
    }
}