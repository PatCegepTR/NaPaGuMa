package com.example.test.ui.PriseDeDonnee;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.databinding.FragmentPriseDeDonneesBinding;
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
                .serverHost("172.16.87.71")
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
                            priseDonnee.setImageResource(R.drawable.ic_sablier);
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

                    double rythmeDecimal = Double.parseDouble(rythme);
                    double oxygeneDecimal = Double.parseDouble(oxygene);


                    Handler mainHandler = new Handler(Looper.getMainLooper());

                    // Send a task to the MessageQueue of the main thread
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvRythmeCardiaque.setText(String.format("%.2f", rythmeDecimal));
                            tvOxygene.setText(String.format("%.2f", oxygeneDecimal));                            // Code will be executed on the main thread
                            priseDonnee.setImageResource(R.drawable.ic_commencer);
                            client.toAsync().disconnect();
                        }
                    });




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