package com.example.test.ui.Profil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.ui.Serveur.InterfaceServeur;
import com.example.test.ui.Serveur.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_login extends Fragment {

    Button buttonConnexion;
    EditText etUtilisateur;
    EditText etPasswd;


    public fragment_login() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonConnexion = view.findViewById(R.id.btConnexion);
        etUtilisateur = view.findViewById(R.id.etNomUtilisateur);
        etPasswd = view.findViewById(R.id.etMDP);

        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utilisateur = etUtilisateur.getText().toString();
                String passwd = etPasswd.getText().toString();

                if(utilisateur.isEmpty() || passwd.isEmpty()){
                    Toast.makeText(fragment_login.this.getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
                else {
                    getConnexion();
                }
            }
        });
    }



    public void getConnexion(){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<CompteConnexion> appel = serveur.connexion(etUtilisateur.getText().toString(), etPasswd.getText().toString());

        appel.enqueue(new Callback<CompteConnexion>() {
            @Override
            public void onResponse(Call<CompteConnexion> call, Response<CompteConnexion> response) {

                CompteConnexion compteConnexion = response.body();

                if(compteConnexion != null){
                    NavController navController = NavHostFragment.findNavController(fragment_login.this);
                    navController.navigate(R.id.action_login_to_accueil);
                }
                else {
                    Toast.makeText(fragment_login.this.getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CompteConnexion> call, Throwable t) {

            }
        });


    }

}