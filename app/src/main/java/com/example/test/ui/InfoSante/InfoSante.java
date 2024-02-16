package com.example.test.ui.InfoSante;

import com.google.gson.annotations.SerializedName;

public class InfoSante {

    @SerializedName("id")
    private int id;

    @SerializedName("InfoSanteTitre")
    private String InfoSanteTitre;

    @SerializedName("InfoSanteDate")
    private String InfoSanteDate;


    public InfoSante(int id, String InfoSanteTitre, String InfoSanteDate) {
        this.id = id;
        this.InfoSanteTitre = InfoSanteTitre;
        this.InfoSanteDate = InfoSanteDate;
    }

    public int getId() {
        return id;
    }

    public String getInfoSanteTitre() {
        return InfoSanteTitre;
    }

    public String getInfoSanteDate() {
        return InfoSanteDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInfoSanteTitre(String InfoSanteTitre) {
        this.InfoSanteTitre = InfoSanteTitre;
    }

    public void setInfoSanteDate(String InfoSanteDate) {
        this.InfoSanteDate = InfoSanteDate;
    }

}
