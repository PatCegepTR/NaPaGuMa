package com.example.test.ui.DonneesPerso;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.ui.Serveur.InterfaceServeur;
import com.example.test.R;
import com.example.test.ui.Serveur.RetrofitInstance;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonneesPersoFragment extends Fragment implements InterfaceDonneesPerso {
    // Variables.
    ModeAffichageReceiver modeAffichageReceiver;

    RecyclerView rvDonneesPerso;
    AdapterListeDonnee adapterListeDonnee;
    AdapterZoomDonneesPerso adapterZoomDonneesPerso;

    private List<LesDonnees> listeDonneesPerso = new ArrayList<LesDonnees>();
    String[] datesDonnees = new String[7];

    TextView tvGraphRCTitre, tvGraphSOTitre, tvGraphRCTemps, tvGraphSOTemps;
    ImageView ivGraphique, ivListe;

    BarChart chartHistoriqueRythmeCardiaque;
    BarChart chartHistoriqueSaturationOxygene;

    ArrayList<BarEntry> historiqueDonneesRythmeCardiaque = new ArrayList<>();
    ArrayList<BarEntry> historiqueDonneesSaturationOxygene = new ArrayList<>();

    BarDataSet dataSetRythmeCardiaque;
    BarDataSet dataSetSaturationOxygene;

    BarData barDataRythmeCardiaque;
    BarData barDataSaturationOxygene;

    AlertDialog adZoomJourneeDonneesPerso;

    // Constructeur du fragment de notre section Données Personnelles. (Il doit être vide)
    public DonneesPersoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_donnees_perso, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getViews(view);
        remplirDonnees();
    }

    @Override
    public void onResume() {
        super.onResume();

        modeAffichageReceiver = new ModeAffichageReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.info.test.AffichageDonnee");

        getActivity().registerReceiver(modeAffichageReceiver, filter);
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(modeAffichageReceiver);
        super.onStop();
    }

    // Va chercher les éléments du Layout à l'aide de l'ID et les implémentes dans les variables.
    public void getViews(View view){
        chartHistoriqueRythmeCardiaque = view.findViewById(R.id.barChartRythmeCardiaque);
        chartHistoriqueSaturationOxygene = view.findViewById(R.id.barChartSaturationOxygene);

        tvGraphRCTitre = view.findViewById(R.id.tvGraphiqueRC);
        tvGraphSOTitre = view.findViewById(R.id.tvGraphiqueSO);
        tvGraphRCTemps = view.findViewById(R.id.tvTempsRC);
        tvGraphSOTemps = view.findViewById(R.id.tvTempsSO);

        ivGraphique = view.findViewById(R.id.ivGraphique);
        ivListe = view.findViewById(R.id.ivListe);

        rvDonneesPerso = view.findViewById(R.id.rvDonnesPerso);
        rvDonneesPerso.setHasFixedSize(true);
        rvDonneesPerso.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // Va chercher les données et les envoit dans une liste.
    public void remplirDonnees()
    {
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);

        Call<List<LesDonnees>> call = serveur.getDonneesSeptDerniersJours();
        call.enqueue(new Callback<List<LesDonnees>>() {
            @Override
            public void onResponse(Call<List<LesDonnees>> call, Response<List<LesDonnees>> response) {
                listeDonneesPerso = response.body();

                adapterListeDonnee = new AdapterListeDonnee(listeDonneesPerso, DonneesPersoFragment.this);
                rvDonneesPerso.setAdapter(adapterListeDonnee);

                createData(listeDonneesPerso);
                makeGraph();
            }

            @Override
            public void onFailure(Call<List<LesDonnees>> call, Throwable t) {
                Toast.makeText(getContext(),"Une erreur s'est produite", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Création du graphique et appelle la fonction de modification pour les deux graphiques.
    private void makeGraph(){
        createDataSets();
        createBarDatas();

        chartCustomizations(chartHistoriqueRythmeCardiaque);
        chartCustomizations(chartHistoriqueSaturationOxygene);
        setChartDatas();

        // On doit invalidate pour que les données soient présente.
        chartHistoriqueRythmeCardiaque.invalidate();
        chartHistoriqueSaturationOxygene.invalidate();
    }

    // Création des données de la liste dans les graphiques appropriés.
    private void createData(List<LesDonnees> listeDonnees){
        createDates();

        List<Integer> indexUtilise = new ArrayList<>();

        for (int i = 0; i < listeDonnees.size(); i++) {
            // On parcours les sept derniers jours et on regarde si les dates correspondent avec la donnée de la liste.
            for(int j = 0; j < 7; j++){
               String dateDonnee = listeDonnees.get(i).getDate();
               String date = datesDonnees[j];

               // On ajoute les données aux graphiques et on retire la date une fois la liste parcourus.
               if(date.equals(dateDonnee)){
                   historiqueDonneesRythmeCardiaque.add(new BarEntry(j, listeDonnees.get(i).getRythmeCardiaque()));
                   historiqueDonneesSaturationOxygene.add(new BarEntry(j, listeDonnees.get(i).getSaturationO2()));
                   indexUtilise.add(j);
               }
            }
        }
    }

    // Création de l'axe X avec les dates des sept derniers jours et on initie les données de la semaine à zéro.
    private void createDates(){
        int index = 0;

        // On part du septième jour jusqu'a aujourd'hui.
        for(int j = 6; j >= 0; j--){
            Calendar calendrier = Calendar.getInstance();
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            calendrier.add(Calendar.DAY_OF_YEAR, -j);

            datesDonnees[index] = formatDate.format(calendrier.getTime());

            historiqueDonneesRythmeCardiaque.add(new BarEntry(index, 0));
            historiqueDonneesSaturationOxygene.add(new BarEntry(index, 0));

            index++;
        }
    }

    // On créer les Datas Sets des graphiques et on les modifies.
    private void createDataSets(){
        dataSetRythmeCardiaque = new BarDataSet(historiqueDonneesRythmeCardiaque, "Rythme Cardiaque");
        dataSetSaturationOxygene = new BarDataSet(historiqueDonneesSaturationOxygene, "Saturation d'Oxygène");

        dataSetRythmeCardiaque.setColor(Color.rgb(169, 33, 33));
        dataSetSaturationOxygene.setColor(Color.rgb(33, 169, 169));

        dataSetFormatting(dataSetRythmeCardiaque);
        dataSetFormatting(dataSetSaturationOxygene);
    }

    // On formate les Data Sets pour que lorsqu'une valeur est égale à zéro, on n'écrit pas la valeur.
    private void dataSetFormatting(DataSet dataSet){
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                if (value == 0)
                    return "";
                else
                    return super.getFormattedValue(value);
            }
        });
    }

    // Création des Bars en fonction des données reçues.
    private void createBarDatas(){
        barDataRythmeCardiaque = new BarData(dataSetRythmeCardiaque);
        barDataSaturationOxygene = new BarData(dataSetSaturationOxygene);
    }

    // Modification des graphiques.
    private void chartCustomizations(BarChart chart){
        // Customize the appearance of the chart.
        chart.setDrawGridBackground(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getDescription().setEnabled(false);
        chart.getXAxis().setEnabled(true);
        chart.getAxisLeft().setEnabled(true);
        chart.getAxisRight().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.getAxisLeft().setAxisMinimum(0f);

        chart.getLegend().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datesDonnees));
    }

    // On insère les Bar de données dans leurs graphiques associés.
    private void setChartDatas(){
        chartHistoriqueRythmeCardiaque.setData(barDataRythmeCardiaque);
        chartHistoriqueSaturationOxygene.setData(barDataSaturationOxygene);
    }

    // Affichage du fragment en mode Liste.
    public void afficherRvDonnees(){
        chartHistoriqueRythmeCardiaque.setVisibility(View.GONE);
        chartHistoriqueSaturationOxygene.setVisibility(View.GONE);
        tvGraphRCTitre.setVisibility(View.GONE);
        tvGraphSOTitre.setVisibility(View.GONE);
        tvGraphRCTemps.setVisibility(View.GONE);
        tvGraphSOTemps.setVisibility(View.GONE);
        ivGraphique.setVisibility(View.GONE);
        ivListe.setVisibility(View.VISIBLE);
        rvDonneesPerso.setVisibility(View.VISIBLE);
    }

    // Affichage du fragment en mode Graphique.
    public void afficherGraphique(){
        rvDonneesPerso.setVisibility(View.GONE);
        chartHistoriqueRythmeCardiaque.setVisibility(View.VISIBLE);
        chartHistoriqueSaturationOxygene.setVisibility(View.VISIBLE);
        tvGraphRCTitre.setVisibility(View.VISIBLE);
        tvGraphSOTitre.setVisibility(View.VISIBLE);
        tvGraphRCTemps.setVisibility(View.VISIBLE);
        tvGraphSOTemps.setVisibility(View.VISIBLE);
        ivGraphique.setVisibility(View.VISIBLE);
        ivListe.setVisibility(View.GONE);
    }

    // Gestion du zoom sur une journée spécifique.
    @Override
    public void gestionClicZoom(int position, LesDonnees donnee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View zoomDonneesPersoView = getLayoutInflater().inflate(R.layout.layout_zoom_donnees_perso, null);
        builder.setView(zoomDonneesPersoView);

        RecyclerView rvZoomDonneesPerso = zoomDonneesPersoView.findViewById(R.id.rvZoomDonneesPerso);
        rvZoomDonneesPerso.setHasFixedSize(true);
        rvZoomDonneesPerso.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView tvDateJour = zoomDonneesPersoView.findViewById(R.id.tvZoomJourneeTitre);

        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);

        Call<List<LesDonnees>> call = serveur.getDonneesDuJour(donnee.getDate());

        call.enqueue(new Callback<List<LesDonnees>>() {
            @Override
            public void onResponse(Call<List<LesDonnees>> call, Response<List<LesDonnees>> response) {
                List<LesDonnees> listeDonneesDuJour = response.body();

                adapterZoomDonneesPerso = new AdapterZoomDonneesPerso(listeDonneesDuJour);
                rvZoomDonneesPerso.setAdapter(adapterZoomDonneesPerso);

                tvDateJour.setText(donnee.getDate());
            }

            @Override
            public void onFailure(Call<List<LesDonnees>> call, Throwable t) {
                Toast.makeText(getContext(),"Une erreur s'est produite", Toast.LENGTH_SHORT).show();
            }
        });

        adZoomJourneeDonneesPerso = builder.create();
        adZoomJourneeDonneesPerso.show();
    }

    // Gestion du mode d'affichage.
    public class ModeAffichageReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mode = intent.getStringExtra("modeAffichage");

            if (mode.equals("listeDonnee"))
                afficherRvDonnees();
            else if (mode.equals("graphDonnee"))
                afficherGraphique();
        }
    }
}
