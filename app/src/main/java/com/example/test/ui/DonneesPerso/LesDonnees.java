package com.example.test.ui.DonneesPerso;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class LesDonnees {
    // Variables.
    @SerializedName("id")
    int id;
    @SerializedName("rythmeCardiaque")
    double rythmeCardiaque;
    @SerializedName("saturationO2")
    double saturationO2;
    @SerializedName("datePrise")
    String date;
    @SerializedName("heurePrise")
    String heure;
    @SerializedName("idUtilisateur")
    int idUtilisateur;

    // Constructeur qui contient les données prisent à une certaine date.
    public LesDonnees(int id, double rythmeCardiaque, double saturationO2, String date, int idUtilisateur) {
        this.id = id;
        this.rythmeCardiaque = rythmeCardiaque;
        this.saturationO2 = saturationO2;
        this.date = date;
        this.idUtilisateur = idUtilisateur;
    }

    // Constructeur qui contient les données prisent par heure selon un journée spécifique.
    public LesDonnees(int id, double rythmeCardiaque, double saturationO2, String date, int idUtilisateur, String heure) {
        this.id = id;
        this.rythmeCardiaque = rythmeCardiaque;
        this.saturationO2 = saturationO2;
        this.date = date;
        this.idUtilisateur = idUtilisateur;
        this.heure = heure;
    }

    // Getters.
    public int getId() {
        return id;
    }

    public double getRythmeCardiaque() {
        return rythmeCardiaque;
    }

    public int getRythmeCardiaqueInt() {
        return (int)rythmeCardiaque;
    }

    public double getSaturationO2() {
        return saturationO2;
    }

    public int getSaturationO2Int() {
        return (int)saturationO2;
    }

    public String getRythmeCardiaqueString(){
        return String.valueOf(rythmeCardiaque);
    }

    public String getSaturationO2String(){
        return String.valueOf(saturationO2);
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

}

