package com.example.test.ui.Profil;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
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
    // Variables.
    Button btConnexion;
    EditText etUtilisateur;
    EditText etPasswd;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    // Constructeur du fragment de notre section Connexion. (Il doit être vide)
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

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        boolean connecte = pref.getBoolean("connecte", false);

        // On vérifie selon les préférences locales, si la personne est déjà connectée.
        if(connecte)
            navAccueil();

        // On va chercher les éléments du fragments.
        btConnexion = view.findViewById(R.id.btConnexion);
        etUtilisateur = view.findViewById(R.id.etNomUtilisateur);
        etPasswd = view.findViewById(R.id.etMDP);

        // Vérification de la connexion.
        btConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utilisateur = etUtilisateur.getText().toString();
                String passwd = etPasswd.getText().toString();

                // Vérification des champps.
                if(utilisateur.isEmpty() || passwd.isEmpty()){
                    Toast.makeText(fragment_login.this.getContext(), R.string.validation_form, Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean accesBD = pref.getBoolean("accesBD", false);
                    if(accesBD)
                        getConnexion();
                    else
                        Toast.makeText(fragment_login.this.getContext(), R.string.erreur_conn, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Appel au serveur selon les champs entrés.
    public void getConnexion(){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<CompteConnexion> call = serveur.connexion(etUtilisateur.getText().toString(), etPasswd.getText().toString());

        call.enqueue(new Callback<CompteConnexion>() {
            @Override
            public void onResponse(Call<CompteConnexion> call, Response<CompteConnexion> response) {

                CompteConnexion compteConnexion = response.body();

                if(compteConnexion != null){
                    setupPreferences(true);
                    getProfile();
                    navAccueil();
                }
                else {
                    Toast.makeText(fragment_login.this.getContext(), R.string.erreur_conn, Toast.LENGTH_SHORT).show();
                    setupPreferences(false);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CompteConnexion> call, Throwable t) {
                Toast.makeText(fragment_login.this.getContext(), R.string.mauvaise_info, Toast.LENGTH_SHORT).show();
                setupPreferences(false);
            }
        });
    }

    // On entre en préférence que la personne est connecté.
    public void setupPreferences(boolean connecte){
        editor = pref.edit();
        editor.putBoolean("connecte", true);
        editor.commit();
    }

    // Appel au serveur pour retrouver les données du profil et on les enregistre en préférences.
    private void getProfile(){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Profil> call = serveur.getProfil(etUtilisateur.getText().toString());

        call.enqueue(new Callback<Profil>() {
            @Override
            public void onResponse(Call<Profil> call, Response<Profil> response) {
                editor = pref.edit();
                editor.putInt("id", response.body().getId());
                editor.putString("prenom", response.body().getPrenom());
                editor.putString("nom", response.body().getNom());
                editor.putString("dateNaissance", response.body().getDateNaissance());
                editor.putString("courriel", response.body().getCourriel());
                editor.commit();
            }

            @Override
            public void onFailure(Call<Profil> call, Throwable t) {
                Toast.makeText(fragment_login.this.getContext(), R.string.erreur_conn, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Navigation vers l'accueil.
    public void navAccueil(){
        NavController navController = NavHostFragment.findNavController(fragment_login.this);
        navController.navigate(R.id.action_login_to_accueil);
    }
}