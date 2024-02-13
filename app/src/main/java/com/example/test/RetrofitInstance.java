package com.example.test;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static final String Base_URL = "file:///P:/FTP/Cours/H2024/420616RI/GR01/p_anctil";
    private static Retrofit retrofit;

    public static Retrofit getInstance()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
