package com.example.test.ui.DonneesPerso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.InterfaceAdapter;
import com.example.test.InterfaceServeur;
import com.example.test.ui.DonneesPerso.AdapterListeDonnee;
import com.example.test.LesDonnees;
import com.example.test.R;
import com.example.test.RetrofitInstance;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonneesPersoFragment extends Fragment implements InterfaceAdapter {


    ModeAffichageReceiver modeAffichageReceiver;
    private List<LesDonnees> liste;

    RecyclerView rvDonneesPerso;
    AdapterListeDonnee adapter;

    String[] datesDonnees = new String[7];

    /* ----------------------------------
        VARIABLES GRAPHIQUES
       ----------------------------------  */
    BarChart chartHistoriqueRythmeCardiaque;
    BarChart chartHistoriqueSaturationOxygene;

    ArrayList<BarEntry> historiqueDonneesRythmeCardiaque = new ArrayList<>();
    ArrayList<BarEntry> historiqueDonneesSaturationOxygene = new ArrayList<>();

    BarDataSet dataSetRythmeCardiaque;
    BarDataSet dataSetSaturationOxygene;

    BarData barDataRythmeCardiaque;
    BarData barDataSaturationOxygene;

    TextView tvGraphRCTitre, tvGraphSOTitre, tvGraphRCTemps, tvGraphSOTemps;
    ImageView ivGraphique, ivListe;



    public DonneesPersoFragment() {
        // Constructeur vide requis.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donnees_perso, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // RECHERCHE DU GRAPHIQUE AVEC L'ID ET CRÉATION DU GRAPHIQUE.
        chartHistoriqueRythmeCardiaque = view.findViewById(R.id.barChartRythmeCardiaque);
        chartHistoriqueSaturationOxygene = view.findViewById(R.id.barChartSaturationOxygene);

        tvGraphRCTitre = view.findViewById(R.id.tvGraphiqueRC);
        tvGraphSOTitre = view.findViewById(R.id.tvGraphiqueSO);
        tvGraphRCTemps = view.findViewById(R.id.tvTempsRC);
        tvGraphSOTemps = view.findViewById(R.id.tvTempsSO);

        ivGraphique = view.findViewById(R.id.ivGraphique);
        ivListe = view.findViewById(R.id.ivListe);

        rvDonneesPerso = view.findViewById(R.id.rvDonnesPerso);

        rvDonneesPerso.setLayoutManager(new LinearLayoutManager(getContext()));

        liste = new ArrayList<LesDonnees>();
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

    public void remplirDonnees()
    {
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);

        Call<List<LesDonnees>> call = serveur.getDonneesSeptDerniersJours();

        InterfaceAdapter monInterface = this;

        call.enqueue(new Callback<List<LesDonnees>>() {
            @Override
            public void onResponse(Call<List<LesDonnees>> call, Response<List<LesDonnees>> response) {
                liste = response.body();

                adapter = new AdapterListeDonnee(liste, monInterface);
                rvDonneesPerso.setAdapter(adapter);

                createData(liste);
                makeGraph();

            }

            @Override
            public void onFailure(Call<List<LesDonnees>> call, Throwable t) {
                Toast.makeText(getContext(),"Une erreur s'est produite", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void makeGraph(){
        createDataSets();
        createBarDatas();

        chartCustomizations(chartHistoriqueRythmeCardiaque);
        chartCustomizations(chartHistoriqueSaturationOxygene);
        setChartDatas();

        chartHistoriqueRythmeCardiaque.invalidate();
        chartHistoriqueSaturationOxygene.invalidate();
    }

    private void createData(List<LesDonnees> listeDonnees){
        createDates();
        List<Integer> indexUtilise = new ArrayList<>();
        for (int i = 0; i < listeDonnees.size(); i++) {
           for(int j = 0; j < 7; j++){
               String dateDonnee = (listeDonnees.get(i).getDateYMD()).substring(0,10);
               String date = datesDonnees[j];
               if(date.equals(dateDonnee)){
                   historiqueDonneesRythmeCardiaque.add(new BarEntry(j, listeDonnees.get(i).getRythmeCardiaque()));
                   historiqueDonneesSaturationOxygene.add(new BarEntry(j, listeDonnees.get(i).getSaturationO2()));
                   indexUtilise.add(j);
               }
           }
        }
    }

    private void createDates(){
        int index = 0;
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

    private void createDataSets(){
        dataSetRythmeCardiaque = new BarDataSet(historiqueDonneesRythmeCardiaque, "Rythme Cardiaque");
        dataSetSaturationOxygene = new BarDataSet(historiqueDonneesSaturationOxygene, "Saturation d'Oxygène");

        // Customize the data sets.
        dataSetRythmeCardiaque.setColor(Color.rgb(169, 33, 33));
        dataSetSaturationOxygene.setColor(Color.rgb(33, 169, 169));

        // Set a custom value formatter to hide the value for the third entry
        dataSetFormatting(dataSetRythmeCardiaque);
        dataSetFormatting(dataSetSaturationOxygene);
    }

    private void dataSetFormatting(DataSet dataSet){
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Check if the value is for the third entry
                if (value == 0) {
                    // Return an empty string to hide the value
                    return "";
                } else {
                    // For other entries, return the default formatted value
                    return super.getFormattedValue(value);
                }
            }
        });
    }

    private void createBarDatas(){
        barDataRythmeCardiaque = new BarData(dataSetRythmeCardiaque);
        barDataSaturationOxygene = new BarData(dataSetSaturationOxygene);
    }

    private void chartCustomizations(BarChart chart){
        // Customize the appearance of the chart.
        chart.setDrawGridBackground(false); // Disable grid background
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getDescription().setEnabled(false); // Disable description text
        chart.getXAxis().setEnabled(true); // Enable x-axis
        chart.getAxisLeft().setEnabled(true); // Enable left y-axis
        chart.getAxisRight().setEnabled(false); // Disable right y-axis
        chart.setTouchEnabled(false);
        chart.getAxisLeft().setAxisMinimum(0f);

        chart.getLegend().setEnabled(false);

        // Set fixed X-axis labels
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datesDonnees));
    }

    private void setChartDatas(){
        chartHistoriqueRythmeCardiaque.setData(barDataRythmeCardiaque);
        chartHistoriqueSaturationOxygene.setData(barDataSaturationOxygene);
    }



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

    @Override
    public void gestionClic(int position, LesDonnees donnee) {

    }



    public class ModeAffichageReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mode = intent.getStringExtra("modeAffichage");

            if (mode.equals("listeDonnee")){
                afficherRvDonnees();
            }
            else if (mode.equals("graphDonnee")){
                afficherGraphique();
            }
        }
    }




}
