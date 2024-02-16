package com.example.test.ui.Profil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.InterfaceServeur;
import com.example.test.R;
import com.example.test.RetrofitInstance;

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
        final View view = inflater.inflate(R.layout.fragment_profil, container, false);


        ImageView imgProfil = view.findViewById(R.id.ivProfil);

        getProfilById();

        imgProfil.setImageResource(R.drawable.ic_launcher_foreground);

        return view;
    }

    public void getProfilById(){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        retrofit2.Call<Profil> appel = serveur.getProfil();

        appel.enqueue(new Callback<Profil>() {
            @Override
            public void onResponse(retrofit2.Call<Profil> call, Response<Profil> response) {
                Profil profil = response.body();

                tvInfoProfils.setText(profil.getNom() + "\n" + profil.getPrenom() + "\n" + profil.getCourriel() + "\n" + profil.getDateNaissance());
            }

            @Override
            public void onFailure(retrofit2.Call<Profil> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void afficherProfil(Profil profil) {
        tvInfoProfils.setText(profil.getNom() + "\n" + profil.getPrenom() + "\n" + profil.getCourriel() + "\n" + profil.getDateNaissance());
    }
}