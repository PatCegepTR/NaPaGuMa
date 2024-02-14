package com.example.test.ui.DonneesPerso;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Donnee {

    @SerializedName("id")
    int id;

    @SerializedName("rythmeCardiaque")
    int rythmeCardiaque;

    @SerializedName("saturationO2")
    int saturationO2;

    @SerializedName("date")
    Date date;

    public Donnee(int id, int rythmeCardiaque, int saturationO2, Date date) {
        this.id = id;
        this.rythmeCardiaque = rythmeCardiaque;
        this.saturationO2 = saturationO2;
        this.date = date;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
