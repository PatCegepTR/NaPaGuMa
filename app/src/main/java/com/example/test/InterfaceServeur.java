package com.example.test;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceServeur {
    @GET("/H2024/420616RI/GR01/p_anctil/getDonnees.php")
    Call<String> getBonjour();


    @GET("/H2024/420616RI/GR01/p_anctil/getDonnees2.php")
    Call<List<LesDonnees>> getDonnees2();
}
