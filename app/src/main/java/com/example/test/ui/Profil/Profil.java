package com.example.test.ui.Profil;

import com.google.gson.annotations.SerializedName;

public class Profil {
    // Variables.
    @SerializedName("id")
    int id;

    @SerializedName("nom")
    String nom;

    @SerializedName("prenom")
    String prenom;

    @SerializedName("dateNaissance")
    String dateNaissance;

    @SerializedName("courriel")
    String courriel;

    // Constructeur du profil.
    public Profil(int id, String nom, String prenom, String dateNaissance, String courriel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.courriel = courriel;
    }

    // Getters et Setters.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }



}
