package com.example.test;

import com.example.test.donneesperso.Donnee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceServeur {
    @GET("/H2024/420616RI/GR01/p_anctil/getDonnees.php")
    Call<List<Donnee>> getDonnees();
}
