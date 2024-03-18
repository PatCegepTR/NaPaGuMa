package com.example.test.ui.Profil;

import com.google.gson.annotations.SerializedName;

public class CompteConnexion {
    @SerializedName("utilisateur")
    private String utilisateur;
    @SerializedName("motDePasse")
    private String motDePasse;

    int connection = 0;

    public CompteConnexion(String utilisateur, String motDePasse, int connection) {
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;
        this.connection = connection;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getConnexion() {
        return connection;
    }

    public void setConnexion(int connection) {
        this.connection = connection;
    }
}
