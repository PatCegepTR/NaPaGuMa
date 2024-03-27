package com.example.test.ui.Serveur;


import androidx.annotation.Nullable;

import com.example.test.ui.DonneesPerso.LesDonnees;
import com.example.test.ui.Profil.CompteConnexion;
import com.example.test.ui.Profil.Profil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceServeur {

    @GET("/getDonnees2.php")
    Call<List<LesDonnees>> getDonnees2(@Query("idUtilisateur") int idUtilisateur);

    @GET("/getDonnees7Jours.php")
    Call<List<LesDonnees>> getDonneesSeptDerniersJours(@Query("idUtilisateur") int idUtilisateur);

    @GET("/getDonneesDuJour.php")
    Call<List<LesDonnees>> getDonneesDuJour(@Query("datePrise") String date,
                                            @Query("idUtilisateur") int idUtilisateur);

    @GET("/getProfil.php")
    Call<Profil> getProfil(@Query("courriel") String courriel);

    @GET("/Connexion.php")
    Call<CompteConnexion> connexion(@Query("courriel") String courriel,
                                    @Query("motDePasse") String motDePasse);

    @GET("/getAccesBD.php")
    Call<Boolean> getAccesBD();

    @GET("/modifProfil.php")
    Call<Boolean> modifierProfil(@Query("courriel") String courriel,
                                  @Query("ancienMotDePasse") String motDePasse,
                                  @Query("nouveauMotDePasse") String nouveauMotDePasse);

    @GET("/supprimerDonnee.php")
    Call<Boolean> supprimerDonnee(@Query("idDonnee") int id);

    @GET("/addDonnees.php")
    Call<Boolean> ajouterDonnee(@Query("rythmeCardiaque") double rythmeCardiaque,
                                @Query("saturationO2") double saturationO2,
                                @Query("idUtilisateur") int idUtilisateur);
}
