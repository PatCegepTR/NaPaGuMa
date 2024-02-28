package com.example.test.ui.DonneesPerso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;
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

        Call<List<LesDonnees>> call = serveur.getDonnees2();

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
    }

    private void createData(List<LesDonnees> listeDonnees){
        for (int i = 0; i<7; i++) {
            historiqueDonneesRythmeCardiaque.add(new BarEntry(i, listeDonnees.get(i).getRythmeCardiaque()));
            historiqueDonneesSaturationOxygene.add(new BarEntry(i, listeDonnees.get(i).getSaturationO2()));
            datesDonnees[i] = listeDonnees.get(i).getDateYMD();
        }



    }

    private void createDataSets(){
        dataSetRythmeCardiaque = new BarDataSet(historiqueDonneesRythmeCardiaque, "Rythme Cardiaque");
        dataSetSaturationOxygene = new BarDataSet(historiqueDonneesSaturationOxygene, "Saturation d'Oxygène");

        // Customize the data sets.
        dataSetRythmeCardiaque.setColor(Color.RED);
        dataSetSaturationOxygene.setColor(Color.BLUE);

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

        // Modify legend.
        Legend legend = chart.getLegend();
        legend.setTextColor(Color.BLUE); // Set legend text color
        legend.setTextSize(12f); // Set legend text size
        legend.setForm(Legend.LegendForm.CIRCLE); // Set legend form to circle

        // Set fixed X-axis labels
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datesDonnees));
    }

    private void setChartDatas(){
        chartHistoriqueRythmeCardiaque.setData(barDataRythmeCardiaque);
        chartHistoriqueSaturationOxygene.setData(barDataSaturationOxygene);
    }



    public void afficherRvDonnees(){
        chartHistoriqueDonnees.setVisibility(View.GONE);
        rvDonneesPerso.setVisibility(View.VISIBLE);
    }

    public void afficherGraphique(){
        rvDonneesPerso.setVisibility(View.GONE);
        chartHistoriqueDonnees.setVisibility(View.VISIBLE);
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
            else
            Toast.makeText(context, "Mode d'affichage", Toast.LENGTH_SHORT).show();
        }
    }




}
