package com.example.test.ui.Profil;

import com.google.gson.annotations.SerializedName;

public class CompteConnexion {
    @SerializedName("courriel")
    private String courriel;
    @SerializedName("motDePasse")
    private String motDePasse;
    @SerializedName("type")
    private String type;
    @SerializedName("idLangue")
    private int idLangue;

    public CompteConnexion(String courriel, String motDePasse, String type, int idLangue) {
        this.courriel = courriel;
        this.motDePasse = motDePasse;
        this.type = type;
        this.idLangue = idLangue;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdLangue() {
        return idLangue;
    }

    public void setIdLangue(int idLangue) {
        this.idLangue = idLangue;
    }

}
