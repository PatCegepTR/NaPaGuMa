package com.example.test.ui.PriseDeDonnee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.databinding.FragmentPriseDeDonneesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.UUID;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class PriseDeDonneesFragment extends Fragment {


    FloatingActionButton priseDonnee;
    Mqtt5Client client;
    TextView tvOxygene;
    TextView tvRythmeCardiaque;

    public PriseDeDonneesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("172.16.208.52")
                .serverPort(1883)
                .build();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prise_de_donnees, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        priseDonnee = view.findViewById(R.id.fbCommencerPrise);
        tvRythmeCardiaque = view.findViewById(R.id.tvRythmeCardiaque);
        tvOxygene = view.findViewById(R.id.tvOxygene);

        priseDonnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.toAsync().connect()
                        .whenComplete((connAck, throwable) -> {
                        if (throwable != null) {
                            Toast.makeText(getContext(), "C'est ici", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            souscrire();
                        }
                    });
            }
        });
    }


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

                    //tvOxygene.setText(oxygene);
                    //tvRythmeCardiaque.setText(rythme);

                    Toast.makeText(getContext(),rythme , Toast.LENGTH_SHORT).show();
                })
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        Toast.makeText(getContext(), "Il y a erreur!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Souscription réussie", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}