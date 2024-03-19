package com.example.test.ui.Profil;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;


public class ProfilFragment extends Fragment {

    TextView tvPrenom, tvNom, tvDateNaissance, tvCourriel;
    SharedPreferences pref;

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

        String prenom = pref.getString("prenom", "");
        String nom = pref.getString("nom", "");
        String dateNaissance = pref.getString("dateNaissance", "");
        String courriel = pref.getString("courriel", "");

        tvPrenom.setText(prenom);
        tvNom.setText(nom);
        tvDateNaissance.setText(dateNaissance);
        tvCourriel.setText(courriel);
    }
}