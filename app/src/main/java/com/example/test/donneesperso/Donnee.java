package com.example.test.donneesperso;

public class Donnee {
    double rythmeCardiaque;
    double saturationOxygene;
    String date;

    public Donnee(double rythmeCardiaque, double saturationOxygene, String date) {
        this.rythmeCardiaque = rythmeCardiaque;
        this.saturationOxygene = saturationOxygene;
        this.date = date;
    }

    public double getRythmeCardiaque() {
        return rythmeCardiaque;
    }

    public void setRythmeCardiaque(double rythmeCardiaque) {
        this.rythmeCardiaque = rythmeCardiaque;
    }

    public double getSaturationOxygene() {
        return saturationOxygene;
    }

    public void setSaturationOxygene(double saturationOxygene) {
        this.saturationOxygene = saturationOxygene;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
