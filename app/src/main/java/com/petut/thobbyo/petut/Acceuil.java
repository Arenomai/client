package com.petut.thobbyo.petut;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Acceuil extends AppCompatActivity {
    EditText tcp_req;
    TextView tcp_text;
    Button tcp_envoi;
    Chaussette chau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        final EditText tcp_req = (EditText) findViewById(R.id.tcp_req);
        final TextView tcp_text = (TextView) findViewById(R.id.tcp_text);
        Button tcp_envoi = (Button) findViewById(R.id.tcp_envoi);
        final Chaussette chau = new Chaussette();

        tcp_envoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reponse = chau.Send(tcp_req.getText().toString());
                tcp_text.setText(tcp_text.getText().toString() + "\n" + reponse);
            }
        });
    }

    public void changeView(View view) {
        Intent intent = new Intent(Acceuil.this, MapsMenu.class);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Acceuil Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
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
