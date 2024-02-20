package com.example.test;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


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
            menu.findItem(R.id.itModifier).setVisible(true);
            menu.findItem(R.id.itDeconnexion).setVisible(true);
        } else {
            menu.findItem(R.id.itModifier).setVisible(false);
            menu.findItem(R.id.itDeconnexion).setVisible(false);
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
    }



}