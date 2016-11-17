package com.petut.thobbyo.petut;

import android.app.ActionBar;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Acceuil extends AppCompatActivity {
    public class Chaussette extends AsyncTask<String, String, String> {
        TextView tcp_text = (TextView) findViewById(R.id.tcp_text);

        @Override
        protected String doInBackground(String... arg) {
            try {
                Socket chaussette = new Socket("thgros.fr", 4242);
                InputStream in = chaussette.getInputStream();
                OutputStream out = chaussette.getOutputStream();
                out.write(arg[0].getBytes());

                int nbytes;
                String reponse = "";
                byte buf[] = new byte[512];

                /*while ((nbytes = in.read(buf)) != -1) {
                    reponse += new String(buf).substring(0, nbytes);
                }*/

                chaussette.close();

                return arg[0];

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
    Button plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        tcp_req = (EditText) findViewById(R.id.tcp_req);
        tcp_text = (TextView) findViewById(R.id.tcp_text);
        tcp_envoi = (Button) findViewById(R.id.tcp_envoi);
        plan = (Button) findViewById(R.id.plan);

        tcp_envoi.setOnClickListener(new View.OnClickListener() {
            @Override
            //tcp_req.getText().toString()
            public void onClick(View view) {
                new Chaussette().execute("select * from test");
            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Acceuil.this, MapsMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
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
