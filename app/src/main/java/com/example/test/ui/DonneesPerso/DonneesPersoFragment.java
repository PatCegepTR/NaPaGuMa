package com.example.test.ui.DonneesPerso;

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

import com.example.test.InterfaceAdapter;
import com.example.test.InterfaceServeur;
import com.example.test.ui.DonneesPerso.AdapterListeDonnee;
import com.example.test.LesDonnees;
import com.example.test.R;
import com.example.test.RetrofitInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonneesPersoFragment extends Fragment implements InterfaceAdapter {
    private List<LesDonnees> liste;

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

        liste = new ArrayList<LesDonnees>();
        remplirDonnees();
        //getBonjour();
    }

    public void getBonjour()
    {
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);

        Call<String> call = serveur.getBonjour();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String text = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(),"Bonjours bug", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void remplirDonnees()
    {
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);

        Call<List<LesDonnees>> call = serveur.getDonnees2();

        InterfaceAdapter monInterface = this;

        call.enqueue(new Callback<List<LesDonnees>>() {
            @Override
            public void onResponse(Call<List<LesDonnees>> call, Response<List<LesDonnees>> response) {

                Toast.makeText(getContext(),"Ça marche", Toast.LENGTH_SHORT).show();

                //liste = response.body();

                adapter = new AdapterListeDonnee(liste, monInterface);
                rvDonneesPerso.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<LesDonnees>> call, Throwable t) {
                Toast.makeText(getContext(),"OUUULAAAA", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void gestionClic(int position, LesDonnees donnee) {
        Toast.makeText(getContext(),"YIPPEEEEEEEEEEEE", Toast.LENGTH_SHORT).show();
    }
}
