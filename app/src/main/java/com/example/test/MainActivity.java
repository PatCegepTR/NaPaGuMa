package com.example.test;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.ui.DonneesPerso.AdapterListeDonnee;
import com.example.test.ui.DonneesPerso.DonneesPersoFragment;
import com.example.test.ui.DonneesPerso.LesDonnees;
import com.example.test.ui.PriseDeDonnee.PriseDeDonneesFragment;
import com.example.test.ui.Profil.fragment_login;
import com.example.test.ui.Serveur.InterfaceServeur;
import com.example.test.ui.Serveur.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    DonneesPersoFragment donneesPersoFragment = new DonneesPersoFragment();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.monmenu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navaController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);



        if (Objects.requireNonNull(navaController.getCurrentDestination()).getId() == R.id.fragment_login){
            navView.setVisibility(BottomNavigationView.GONE);

        }
        else{
            navView.setVisibility(BottomNavigationView.VISIBLE);
        }


        if (Objects.requireNonNull(navaController.getCurrentDestination()).getId() == R.id.navigation_profil){
            menu.clear();
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.monmenu, menu);


        }
        else if (Objects.requireNonNull(navaController.getCurrentDestination()).getId() == R.id.navigation_donnee_perso) {
            menu.clear();
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_donnees_perso, menu);


        }
        else{
            menu.clear();
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itDeconnexion){
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = pref.edit();

            editor.putBoolean("connecte", false);
            editor.commit();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_profil_to_login);
            Toast.makeText(this, R.string.deconnexion, Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.itListeDonnee){
            Intent intent = new Intent();
            intent.setAction("com.info.test.AffichageDonnee");
            intent.putExtra("modeAffichage","listeDonnee");
            sendBroadcast(intent);

        }
        else if (item.getItemId() == R.id.itGraphDonnee){
            Intent intent = new Intent();
            intent.setAction("com.info.test.AffichageDonnee");
            intent.putExtra("modeAffichage","graphDonnee");
            sendBroadcast(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PriseDeDonneesFragment fragment = new PriseDeDonneesFragment();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getAccesBD();


        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_infoSante, R.id.navigation_donnee_perso, R.id.navigation_prise_de_donnee, R.id.navigation_statistique, R.id.navigation_profil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                invalidateOptionsMenu();
                if(navDestination.getId() == R.id.fragment_login){
                    getSupportActionBar().hide();
                }
                else {
                    getSupportActionBar().show();
                }

            }
        });
    }

    private void getAccesBD(){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);

        Call<Boolean> call = serveur.getAccesBD();

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                boolean accesBD = response.body();

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("accesBD", accesBD);
                editor.commit();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("accesBD", false);
                editor.commit();
                Toast.makeText(MainActivity.this,R.string.erreur_conn, Toast.LENGTH_SHORT).show();
            }
        });
    }

}