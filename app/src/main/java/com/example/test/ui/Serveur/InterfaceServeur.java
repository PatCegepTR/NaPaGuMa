package com.example.test.ui.Serveur;


import com.example.test.ui.DonneesPerso.LesDonnees;
import com.example.test.ui.Profil.CompteConnexion;
import com.example.test.ui.Profil.Profil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceServeur {

    //@GET("/H2024/420616RI/GR01/p_anctil/getDonnees2.php")
    @GET("/getDonnees2.php")
    Call<List<LesDonnees>> getDonnees2(@Query("idUtilisateur") int idUtilisateur);

    //@GET("/H2024/420616RI/GR01/p_anctil/getDonnees7Jours.php")
    @GET("/getDonnees7Jours.php")
    Call<List<LesDonnees>> getDonneesSeptDerniersJours(@Query("idUtilisateur") int idUtilisateur);

    @GET("/getDonneesDuJour.php")
    Call<List<LesDonnees>> getDonneesDuJour(@Query("datePrise") String date,
                                            @Query("idUtilisateur") int idUtilisateur);

    //@GET("/H2024/420616RI/GR01/p_anctil/getProfil.php")
    @GET("/getProfil.php")
    Call<Profil> getProfil(@Query("courriel") String courriel);

    @GET("/Connexion.php")
    Call<CompteConnexion> connexion(@Query("courriel") String courriel,
                                    @Query("motDePasse") String motDePasse);

    @GET("/getAccesBD.php")
    Call<Boolean> getAccesBD();
}
