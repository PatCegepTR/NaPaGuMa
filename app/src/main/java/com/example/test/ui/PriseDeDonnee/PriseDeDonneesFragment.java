package com.example.test.ui.PriseDeDonnee;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.databinding.ActivityMainBinding;
import com.example.test.databinding.FragmentPriseDeDonneesBinding;
import com.example.test.ui.Serveur.InterfaceServeur;
import com.example.test.ui.Serveur.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hivemq.client.internal.mqtt.MqttAsyncClient;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PriseDeDonneesFragment extends Fragment {
    // Variables.
    FloatingActionButton priseDonnee;
    Mqtt5Client client;
    TextView tvOxygene;
    TextView tvRythmeCardiaque;

    // Constructeur du fragment de notre section Prise de Données. (Il doit être vide)
    public PriseDeDonneesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("172.16.87.71")
                .serverPort(1883)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prise_de_donnees, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // On va chercher les éléments de la View.
        priseDonnee = view.findViewById(R.id.fbCommencerPrise);
        tvRythmeCardiaque = view.findViewById(R.id.tvRythmeCardiaque);
        tvOxygene = view.findViewById(R.id.tvOxygene);

        // Gestion du clique de la prise de données.
        priseDonnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.toAsync().connect()
                        .whenComplete((connAck, throwable) -> {
                        if (throwable != null) {

                        }
                        else {
                            priseDonnee.setImageResource(R.drawable.ic_sablier);
                            souscrire();
                        }
                    });
            }
        });
    }

    // On souscrit au topic MQTT.
    public void souscrire(){
        client.toAsync().subscribeWith()
                .topicFilter("CapteurCardiaque")
                .callback(publish -> {
                    // Process the received message
                    String test = new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8) ;
                    JsonElement element = new JsonParser().parse(test);
                    JsonObject jsonObject = element.getAsJsonObject();


                    String rythme = jsonObject.get("rythmeCardiaque").toString();
                    String oxygene = jsonObject.get("saturationO2").toString();

                    double rythmeDecimal = Double.parseDouble(rythme);
                    double oxygeneDecimal = Double.parseDouble(oxygene);


                    Handler mainHandler = new Handler(Looper.getMainLooper());

                    // Send a task to the MessageQueue of the main thread
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
                                int idUtilisateur = pref.getInt("id", 0);
                                ajouterDonnee(rythmeDecimal, oxygeneDecimal, idUtilisateur);
                                tvRythmeCardiaque.setText(String.format("%.2f", rythmeDecimal));
                                tvOxygene.setText(String.format("%.2f", oxygeneDecimal));                            // Code will be executed on the main thread
                                priseDonnee.setImageResource(R.drawable.ic_commencer);
                                client.toAsync().disconnect();
                            }
                            catch (Exception e){
                                client.toAsync().disconnect();
                            }

                        }
                    });


                })
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        Toast.makeText(getContext(), R.string.erreur, Toast.LENGTH_SHORT).show();
                    } else {

                    }
                });
    }

    // Appel serveur sur l'ajout des données.
    private void ajouterDonnee(double rythmeCardiaque, double saturationO2, int idUtilisateur){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Boolean> call = serveur.ajouterDonnee(rythmeCardiaque, saturationO2, idUtilisateur);
        try {
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    boolean ajouter = response.body();

                    if (!ajouter)
                        Toast.makeText(getContext(),R.string.erreur, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(), R.string.erreur_conn, Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(),R.string.erreur, Toast.LENGTH_LONG).show();
        }
    }

}