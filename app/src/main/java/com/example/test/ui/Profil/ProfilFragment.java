package com.example.test.ui.Profil;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.ui.Serveur.InterfaceServeur;
import com.example.test.ui.Serveur.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfilFragment extends Fragment {
    // Variables.
    TextView tvPrenom, tvNom, tvDateNaissance, tvCourriel;
    Button btModifierProfile;
    SharedPreferences pref;

    AlertDialog adModifierProfile;

    // Constructeur du fragment de notre section Profil. (Il doit être vide)
    public ProfilFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        // On va chercher les éléments de la View.
        tvPrenom = view.findViewById(R.id.tvPrenom);
        tvNom = view.findViewById(R.id.tvNom);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissance);
        tvCourriel = view.findViewById(R.id.tvCourriel);
        btModifierProfile = view.findViewById(R.id.btModifierProfile);

        String prenom = pref.getString("prenom", "");
        String nom = pref.getString("nom", "");
        String dateNaissance = pref.getString("dateNaissance", "");
        String courriel = pref.getString("courriel", "");

        tvPrenom.setText(prenom);
        tvNom.setText(nom);
        tvDateNaissance.setText(dateNaissance);
        tvCourriel.setText(courriel);

        // Gestion du clique de modification de mot de passe.
        btModifierProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On crée une boite de dialogue.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View modifierProfileView = getLayoutInflater().inflate(R.layout.layout_modifier_profile, null);
                builder.setView(modifierProfileView);

                // On va chercher les éléments de la boite.
                EditText etMotDePasseActuel = modifierProfileView.findViewById(R.id.etMotDePasseActuel);
                EditText etModificationMDP = modifierProfileView.findViewById(R.id.etModificationMDP);
                Button btSauvegarder = modifierProfileView.findViewById(R.id.btSauvegarder);

                // Gestion du bouton de sauvegarde.
                btSauvegarder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mDPActuel = etMotDePasseActuel.getText().toString();
                        String nouveauMDP = etModificationMDP.getText().toString();
                        boolean valide = true;

                        // Validation des champs.
                        if(nouveauMDP.trim().isEmpty()){
                            valide = false;
                            etModificationMDP.setError(getText(R.string.mdp_nouveau_vide));
                        }

                        if(mDPActuel.trim().isEmpty()){
                            valide = false;
                            etMotDePasseActuel.setError(getText(R.string.mdp_actuel_vide));
                        }

                        if(valide)
                        {
                            modifierProfil(mDPActuel, nouveauMDP);
                            adModifierProfile.dismiss();
                        }
                    }
                });

                // On construit la boite de dialogue.
                adModifierProfile = builder.create();
                adModifierProfile.show();
            }
        });
    }

    // Appel serveur qui va mettre à jour le mot de passe dans la base de données.
    private void modifierProfil(String motDePasse, String nouveauMotDePasse){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Boolean> call = serveur.modifierProfil(pref.getString("courriel", ""), motDePasse, nouveauMotDePasse);
        try {
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    boolean modifier = response.body();

                    if (!modifier)
                        Toast.makeText(getContext(), R.string.erreur, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(),R.string.erreur_conn, Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(),R.string.erreur, Toast.LENGTH_LONG).show();
        }
    }
}