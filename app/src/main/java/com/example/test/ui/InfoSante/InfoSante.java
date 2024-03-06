package com.example.test.ui.InfoSante;


public class InfoSante {
    // Variables.
    String articleInfoSanteTitre;
    String articleInfoSanteDate;
    String articleInfoSanteLienImage;
    String articleInfoSanteLien;
    String articleInfoSanteDescription;

    // Constructeur d'un article Info Santé.
    public InfoSante(String articleInfoSanteTitre, String articleInfoSanteDate, String articleInfoSanteLienImage, String articleInfoSanteLien, String articleInfoSanteDescription) {
        this.articleInfoSanteTitre = articleInfoSanteTitre;
        this.articleInfoSanteDate = articleInfoSanteDate;
        this.articleInfoSanteLienImage = articleInfoSanteLienImage;
        this.articleInfoSanteLien = articleInfoSanteLien;
        this.articleInfoSanteDescription = articleInfoSanteDescription;
    }

    // Getters.
    public String getArticleInfoSanteTitre() {
        return articleInfoSanteTitre;
    }
    public String getArticleInfoSanteDate() {
        return articleInfoSanteDate;
    }
    public String getArticleInfoSanteLienImage() {
        return articleInfoSanteLienImage;
    }
    public String getArticleInfoSanteLien() {
        return articleInfoSanteLien;
    }
    public String getArticleInfoSanteDescription() {
        return articleInfoSanteDescription;
    }
}
