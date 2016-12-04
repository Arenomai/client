package com.petut.thobbyo.petut;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPActivity extends AppCompatActivity {
    public class Chaussette extends AsyncTask<String, String, String> {
        TextView tcp_text = (TextView) findViewById(R.id.tcp_text);

        @Override
        protected String doInBackground(String... arg) {
            try {
                Socket chaussette = new Socket("thgros.fr", 4242);

                PrintWriter out = new PrintWriter(chaussette.getOutputStream(), true);
                out.println(arg[0]);

                BufferedReader in = new BufferedReader(new InputStreamReader(chaussette.getInputStream()));
                String response = in.readLine();

                chaussette.close();

                return response;

            } catch (IOException e) {
                e.printStackTrace();
                return "ERROR";
            }
        }

        @Override
        protected void onPostExecute(String reponse) {
            tcp_text.setText(tcp_text.getText().toString() + reponse + "\n");
        }
    }

    EditText tcp_req;
    TextView tcp_text;
    Button tcp_envoi;
    ImageButton plan;
    ImageButton tcp;
    ImageButton profil;
    ImageButton defense;
    ImageButton cartes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tcp);

        tcp_req = (EditText) findViewById(R.id.tcp_req);
        tcp_text = (TextView) findViewById(R.id.tcp_text);
        tcp_envoi = (Button) findViewById(R.id.tcp_envoi);

        tcp = (ImageButton) findViewById(R.id.tcp);
        plan = (ImageButton) findViewById(R.id.plan);
        profil = (ImageButton) findViewById(R.id.profil);
        defense = (ImageButton) findViewById(R.id.defense);
        cartes = (ImageButton) findViewById(R.id.cartes);

        tcp_envoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Chaussette().execute(tcp_req.getText().toString());
            }
        });

        tcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TCPActivity.this, PlanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TCPActivity.this, ProfilActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TCPActivity.this, ArmesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        cartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TCPActivity.this, CartesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
