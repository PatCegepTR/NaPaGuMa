package com.example.test.infoSante;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class InfoSanteActivity extends AppCompatActivity {

    List<Article> listeArticles = new ArrayList<>();

    RecyclerView recyclerView;
    AdapterListeArticle adapteurListeArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info_sante);

        recyclerView = findViewById(R.id.rvInfoSante);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listeArticles.add(new Article("Fréquence cardiaque?", "01/01/24", "Voici ce qu'est la fréquence cardiaque!", "https://th.bing.com/th/id/OIP.fL2EL2i1dVYR5Je4MvS_ZwHaHa?rs=1&pid=ImgDetMain"));
        listeArticles.add(new Article("Fréquence cardiaque?", "01/01/24", "Voici ce qu'est la fréquence cardiaque!", "https://th.bing.com/th/id/OIP.fL2EL2i1dVYR5Je4MvS_ZwHaHa?rs=1&pid=ImgDetMain"));
        listeArticles.add(new Article("Fréquence cardiaque?", "01/01/24", "Voici ce qu'est la fréquence cardiaque!", "https://th.bing.com/th/id/OIP.fL2EL2i1dVYR5Je4MvS_ZwHaHa?rs=1&pid=ImgDetMain"));
        listeArticles.add(new Article("Fréquence cardiaque?", "01/01/24", "Voici ce qu'est la fréquence cardiaque!", "https://th.bing.com/th/id/OIP.fL2EL2i1dVYR5Je4MvS_ZwHaHa?rs=1&pid=ImgDetMain"));
        listeArticles.add(new Article("Fréquence cardiaque?", "01/01/24", "Voici ce qu'est la fréquence cardiaque!", "https://th.bing.com/th/id/OIP.fL2EL2i1dVYR5Je4MvS_ZwHaHa?rs=1&pid=ImgDetMain"));

        adapteurListeArticle = new AdapterListeArticle(listeArticles);

        recyclerView.setAdapter(adapteurListeArticle);
    }
}
