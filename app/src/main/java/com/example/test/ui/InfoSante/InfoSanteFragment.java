package com.example.test.ui.InfoSante;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.LesDonnees;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoSanteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoSanteFragment extends Fragment implements InterfaceInfoSante{

    RecyclerView recyclerViewInfoSante;
    AdapterInfoSante adapterInfoSante;
    List<InfoSante> listeArticlesInfoSante = new ArrayList<InfoSante>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoSanteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoSanteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoSanteFragment newInstance(String param1, String param2) {
        InfoSanteFragment fragment = new InfoSanteFragment();
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
        final View view = inflater.inflate(R.layout.fragment_info_sante, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();

        recyclerViewInfoSante = view.findViewById(R.id.rvInfoSante);
        recyclerViewInfoSante.setHasFixedSize(true);
        recyclerViewInfoSante.setLayoutManager(new LinearLayoutManager(context));

        listeArticlesInfoSante.add(new InfoSante(getString(R.string.titre_card_article1), getString(R.string.date_card_article1), "https://www.docteurclic.com/galerie-photos/image_3191_400.jpg", "www.google.ca", "Je parle du poul."));
        listeArticlesInfoSante.add(new InfoSante(getString(R.string.titre_card_article2), getString(R.string.date_card_article1), "https://static.tuasaude.com/media/article/hr/yh/saturacao-de-oxigenio_54743_l.webp", "www.google.ca", "Je parle du poul."));
        listeArticlesInfoSante.add(new InfoSante(getString(R.string.titre_card_article3), getString(R.string.date_card_article1), "https://upload.wikimedia.org/wikipedia/en/thumb/2/28/Information.svg/1024px-Information.svg.png", "www.google.ca", "Je parle du poul."));

        adapterInfoSante = new AdapterInfoSante(listeArticlesInfoSante, this);

        recyclerViewInfoSante.setAdapter(adapterInfoSante);

    }

    public void GestionClicArticle(int position, InfoSante article){

    }
}