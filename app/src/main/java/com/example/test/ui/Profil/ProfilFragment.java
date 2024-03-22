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

import com.example.test.R;


public class ProfilFragment extends Fragment {

    TextView tvPrenom, tvNom, tvDateNaissance, tvCourriel;
    Button btModifierProfile;
    SharedPreferences pref;

    AlertDialog adModifierProfile;

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

        btModifierProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View modifierProfileView = getLayoutInflater().inflate(R.layout.layout_modifier_profile, null);
                builder.setView(modifierProfileView);

                EditText etModificationMDP = modifierProfileView.findViewById(R.id.etModificationMDP);
                String nouveauMDP = etModificationMDP.getText().toString();

                boolean valide = true;

                if(etModificationMDP.getText().toString().trim().isEmpty()){
                    valide = false;
                    etModificationMDP.setError("Entrez un mot de passe valide.");
                }

                if(valide)
                {

                }

                adModifierProfile = builder.create();
                adModifierProfile.show();
            }
        });
    }
}