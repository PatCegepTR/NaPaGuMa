package com.example.test.donneesperso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.InterfaceServeur;
import com.example.test.R;
import com.example.test.RetrofitInstance;
import com.example.test.infoSante.AdapterListeArticle;
import com.example.test.infoSante.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonneesPersoActivity extends AppCompatActivity implements AdapterListeDonnee.MonInterface{

    RecyclerView rvDonneesPerso;
    List<Donnee> liste;
    Button btnDonnees;
    AdapterListeDonnee adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_donnees_perso);

        context = this;

        rvDonneesPerso = findViewById(R.id.rvDonnesPerso);
        adapter = new AdapterListeDonnee(liste, this);
        rvDonneesPerso.setAdapter(adapter);

        btnDonnees = findViewById(R.id.btnDonnees);



    }

    public void btnDonnees(View view)
    {
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);

        Call<List<Donnee>> call = serveur.getDonnees();

        call.enqueue(new Callback<List<Donnee>>() {
            @Override
            public void onResponse(Call<List<Donnee>> call, Response<List<Donnee>> response) {
                liste = response.body();
                Toast.makeText(context,"YIPPEEEEEEEEEEEE", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<Donnee>> call, Throwable t) {
                Toast.makeText(context,"OUUULAAAA", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void gestionClic(int position, Donnee donnee) {

    }

}
