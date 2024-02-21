package com.example.test.ui.DonneesPerso;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonneesPersoFragment extends Fragment implements InterfaceAdapter {
    private List<LesDonnees> liste;

    RecyclerView rvDonneesPerso;
    AdapterListeDonnee adapter;

    String[] datesDonnees = new String[7];

    /* ----------------------------------
        VARIABLES GRAPHIQUES
       ----------------------------------  */
    BarChart chartHistoriqueDonnees;

    ArrayList<BarEntry> historiqueDonneesRythmeCardiaque = new ArrayList<>();

    BarDataSet _dataSetRythmeCardiaque;
    BarData _barDataRythmeCardiaque;

    public DonneesPersoFragment() {
        // Required empty public constructor
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
        chartHistoriqueDonnees = view.findViewById(R.id.barChart);

        rvDonneesPerso = view.findViewById(R.id.rvDonnesPerso);

        rvDonneesPerso.setLayoutManager(new LinearLayoutManager(getContext()));

        liste = new ArrayList<LesDonnees>();
        remplirDonnees();
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

        chartCustomizations();
        setChartDatas();

        chartHistoriqueDonnees.invalidate();
    }

    private void createData(List<LesDonnees> listeDonnees){
        for (int i = 0; i<7; i++) {
            historiqueDonneesRythmeCardiaque.add(new BarEntry(i, listeDonnees.get(i).getRythmeCardiaque()));
            datesDonnees[i] = listeDonnees.get(i).getDateYMD();
        }



    }

    private void createDataSets(){
        _dataSetRythmeCardiaque = new BarDataSet(historiqueDonneesRythmeCardiaque, "Rythme Cardiaque");

        // Customize the data sets.
        _dataSetRythmeCardiaque.setColor(Color.BLUE);

        // Set a custom value formatter to hide the value for the third entry
        _dataSetRythmeCardiaque.setValueFormatter(new ValueFormatter() {
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
        _barDataRythmeCardiaque = new BarData(_dataSetRythmeCardiaque);
    }

    private void chartCustomizations(){
        // Customize the appearance of the chart.
        chartHistoriqueDonnees.setDrawGridBackground(false); // Disable grid background
        chartHistoriqueDonnees.getXAxis().setDrawGridLines(false);
        chartHistoriqueDonnees.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartHistoriqueDonnees.getAxisLeft().setDrawGridLines(false);
        chartHistoriqueDonnees.getDescription().setEnabled(false); // Disable description text
        chartHistoriqueDonnees.getXAxis().setEnabled(true); // Enable x-axis
        chartHistoriqueDonnees.getAxisLeft().setEnabled(true); // Enable left y-axis
        chartHistoriqueDonnees.getAxisRight().setEnabled(false); // Disable right y-axis
        chartHistoriqueDonnees.setTouchEnabled(false);
        chartHistoriqueDonnees.getAxisLeft().setAxisMinimum(0f);

        // Modify legend.
        Legend legend = chartHistoriqueDonnees.getLegend();
        legend.setTextColor(Color.BLUE); // Set legend text color
        legend.setTextSize(12f); // Set legend text size
        legend.setForm(Legend.LegendForm.CIRCLE); // Set legend form to circle

        // Set fixed X-axis labels
        XAxis xAxis = chartHistoriqueDonnees.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datesDonnees));
    }

    private void setChartDatas(){
        chartHistoriqueDonnees.setData(_barDataRythmeCardiaque);
    }



    @Override
    public void gestionClic(int position, LesDonnees donnee) {
    }
}
