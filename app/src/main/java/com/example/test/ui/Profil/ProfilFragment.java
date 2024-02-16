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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment implements InterfaceProfil {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    TextView tvInfoProfils;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profil, container, false);

        TextView infoProfil = view.findViewById(R.id.tvInfoPersoProfil);
        ImageView imgProfil = view.findViewById(R.id.ivProfil);

        //infoProfil.setText("Nom : " + "Dupont" + "\n" + "Prénom : " + "Jean" + "\n" + "Date de naissance : " + "01/01/2000" + "\n" + "Adresse mail : ");
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