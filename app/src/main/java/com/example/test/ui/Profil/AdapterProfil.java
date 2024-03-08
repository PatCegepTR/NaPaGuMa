package com.example.test.ui.Profil;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.InterfaceAdapter;
import com.example.test.InterfaceServeur;
import com.example.test.MainActivity;
import com.example.test.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdapterProfil {
    InterfaceProfil monInterfaceProfil;

    Profil monProfil;



    public AdapterProfil(Profil profil ,InterfaceProfil monInterfaceProfil) {
        this.monInterfaceProfil = monInterfaceProfil;
        this.monProfil = profil;
    }


}
