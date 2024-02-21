package com.example.test;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.ui.DonneesPerso.DonneesPersoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityMainBinding;

import java.util.Objects;

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

        if (Objects.requireNonNull(navaController.getCurrentDestination()).getId() == R.id.navigation_profil){
            menu.clear();
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.monmenu, menu);

        }
        else if (Objects.requireNonNull(navaController.getCurrentDestination()).getId() == R.id.navigation_donnee_perso){
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
        if(item.getItemId() == R.id.itModifier){
            Toast.makeText(this, "Modification", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.itDeconnexion){
            Toast.makeText(this, "Deconnexion", Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.itGraphDonnee){
            donneesPersoFragment.afficherRvDonnees(2);
            donneesPersoFragment.afficherGraphique(1);
            Toast.makeText(this, "Graphique", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.itListeDonnee){
            donneesPersoFragment.afficherGraphique(2);
            donneesPersoFragment.afficherRvDonnees(1);
            Toast.makeText(this, "Liste", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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
            }
        });
    }



}