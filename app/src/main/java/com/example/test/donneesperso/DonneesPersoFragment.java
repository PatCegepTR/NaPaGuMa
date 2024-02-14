package com.example.test.donneesperso;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.InterfaceServeur;
import com.example.test.R;
import com.example.test.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonneesPersoFragment extends Fragment implements AdapterListeDonnee.MonInterface {
    private List<Donnee> liste;

    RecyclerView rvDonneesPerso;
    Button btnDonnees;
    AdapterListeDonnee adapter;

    public DonneesPersoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donnees_perso, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDonnees = view.findViewById(R.id.btnDonnees);
        rvDonneesPerso = view.findViewById(R.id.rvDonnesPerso);

        rvDonneesPerso.setLayoutManager(new LinearLayoutManager(getContext()));

        liste = new ArrayList<Donnee>();
        remplirDonnees();
    }

    public void remplirDonnees()
    {
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);

        Call<List<Donnee>> call = serveur.getDonnees();

        AdapterListeDonnee.MonInterface monInterface = this;

        call.enqueue(new Callback<List<Donnee>>() {
            @Override
            public void onResponse(Call<List<Donnee>> call, Response<List<Donnee>> response) {
                liste = response.body();
                Toast.makeText(getContext(),"YIPPEEEEEEEEEEEE", Toast.LENGTH_SHORT).show();

                adapter = new AdapterListeDonnee(liste, monInterface);
                rvDonneesPerso.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Donnee>> call, Throwable t) {
                Toast.makeText(getContext(),"OUUULAAAA", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void gestionClic(int position, Donnee donnee) {

    }
}
