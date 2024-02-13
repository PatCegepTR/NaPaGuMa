package com.example.test;

import com.example.test.donneesperso.Donnee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceServeur {
    @GET("/getDonnees.php")
    Call<List<Donnee>> getDonnees();
}
