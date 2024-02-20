package com.example.test.ui.Profil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.InterfaceServeur;
import com.example.test.LesDonnees;
import com.example.test.R;
import com.example.test.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ProfilFragment extends Fragment implements InterfaceProfil {
    TextView tvInfoProfils;

    public ProfilFragment() {
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
        return inflater.inflate(R.layout.fragment_profil, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgProfil = view.findViewById(R.id.ivProfil);
        tvInfoProfils = view.findViewById(R.id.tvInfoPersoProfil);

        imgProfil.setImageResource(R.drawable.ic_launcher_foreground);

        getProfilById();


    }

    public void getProfilById(){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Profil> appel = serveur.getProfil();

        appel.enqueue(new Callback<Profil>() {
            @Override
            public void onResponse(retrofit2.Call<Profil> call, Response<Profil> response) {
                Profil profil = response.body();

                tvInfoProfils.setText(profil.getPrenom() + " " + profil.getNom() + "\n"
                        + profil.getCourriel() + "\n" + profil.getDateNaissance());
            }

            @Override
            public void onFailure(retrofit2.Call<Profil> call, Throwable t) {
                Toast.makeText(getContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void gestionClic(Profil profil) {

    }
}