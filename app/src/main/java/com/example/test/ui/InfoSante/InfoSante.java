package com.example.test.ui.InfoSante;


public class InfoSante {
    String articleInfoSanteTitre;
    String articleInfoSanteDate;
    String articleInfoSanteLienImage;
    String articleLien;
    String articleDescription;

    public InfoSante(String articleInfoSanteTitre, String articleInfoSanteDate, String articleInfoSanteLienImage, String articleLien, String articleDescription) {
        this.articleInfoSanteTitre = articleInfoSanteTitre;
        this.articleInfoSanteDate = articleInfoSanteDate;
        this.articleInfoSanteLienImage = articleInfoSanteLienImage;
        this.articleLien = articleLien;
        this.articleDescription = articleDescription;
    }

    public String getArticleInfoSanteTitre() {
        return articleInfoSanteTitre;
    }

    public void setArticleInfoSanteTitre(String articleInfoSanteTitre) {
        this.articleInfoSanteTitre = articleInfoSanteTitre;
    }

    public String getArticleInfoSanteDate() {
        return articleInfoSanteDate;
    }

    public void setArticleInfoSanteDate(String articleInfoSanteDate) {
        this.articleInfoSanteDate = articleInfoSanteDate;
    }

    public String getArticleInfoSanteLienImage() {
        return articleInfoSanteLienImage;
    }

    public void setArticleInfoSanteLienImage(String articleInfoSanteLienImage) {
        this.articleInfoSanteLienImage = articleInfoSanteLienImage;
    }

    public String getArticleLien() {
        return articleLien;
    }

    public void setArticleLien(String articleLien) {
        this.articleLien = articleLien;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }
}
