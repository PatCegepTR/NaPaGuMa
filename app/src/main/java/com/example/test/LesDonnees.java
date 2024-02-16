package com.example.test;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LesDonnees {
    @SerializedName("id")
    int id;

    @SerializedName("rythmeCardiaque")
    int rythmeCardiaque;

    @SerializedName("saturationO2")
    int saturationO2;

    @SerializedName("date")
    String date;

    @SerializedName("idUtilisateur")
    int idUtilisateur;

    public LesDonnees(int id, int rythmeCardiaque, int saturationO2, String date, int idUtilisateur) {
        this.id = id;
        this.rythmeCardiaque = rythmeCardiaque;
        this.saturationO2 = saturationO2;
        this.date = date;
        this.idUtilisateur = idUtilisateur;
    }



   /* public LesDonnees(int id, int rythmeCardiaque, int saturationO2, Date date) {
        this.id = id;
        this.rythmeCardiaque = rythmeCardiaque;
        this.saturationO2 = saturationO2;
        this.date = date;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRythmeCardiaque() {
        return rythmeCardiaque;
    }

    public void setRythmeCardiaque(int rythmeCardiaque) {
        this.rythmeCardiaque = rythmeCardiaque;
    }

    public int getSaturationO2() {
        return saturationO2;
    }

    public void setSaturationO2(int saturationO2) {
        this.saturationO2 = saturationO2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
