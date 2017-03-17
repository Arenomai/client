package com.petut.thobbyo.arenomai;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.petut.thobbyo.arenomai.net.Connection;
import com.petut.thobbyo.arenomai.net.MessageType;
import com.petut.thobbyo.arenomai.net.OutMessage;

import java.io.IOException;

public class TCPActivity extends AppCompatActivity {
    public class Chaussette extends AsyncTask<String, String, String> {
        private Context ctx;
        TextView tcp_text = (TextView) findViewById(R.id.tcp_text);

        public Chaussette(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                final Connection co = new Connection();

                publishProgress("Connecting...");
                final SharedPreferences sp = ctx.getSharedPreferences(Application.PREF_DEFAULT, 0);
                co.connect(sp.getString("serverAddr", "localhost"), sp.getInt("serverPort", 4242));
                publishProgress("Connected!");

                publishProgress("Sending auth message");
                OutMessage omsg = new OutMessage(MessageType.Auth);
                omsg.writeI32(123456);
                co.write(omsg);

                publishProgress("Sending inventory request");
                omsg = new OutMessage(MessageType.Inventory);
                omsg.writeI32(123);
                co.write(omsg);

                publishProgress("Disconnecting...");
                co.close();
                publishProgress("Disconnected");

                return "";

            } catch (IOException e) {
                e.printStackTrace();
                return "ERROR";
            }
        }

        protected void addLine(String str) {
            tcp_text.append(str + "\n");
            tcp_text.post(new Runnable() {
                public void run() {
                    final int scrollAmount = tcp_text.getLayout()
                            .getLineTop(tcp_text.getLineCount())
                            - tcp_text.getHeight();
                    tcp_text.scrollTo(0, scrollAmount > 0 ? scrollAmount : 0);
                }
            });
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            addLine(progress[0]);
        }

        @Override
        protected void onPostExecute(String reponse) {
            addLine(reponse);
        }
    }

    EditText tcp_req;
    TextView tcp_text;
    Button tcp_envoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tcp);

        tcp_req = (EditText) findViewById(R.id.tcp_req);
        tcp_text = (TextView) findViewById(R.id.tcp_text);
        tcp_text.setMovementMethod(new ScrollingMovementMethod());
        tcp_envoi = (Button) findViewById(R.id.tcp_envoi);
        tcp_envoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Chaussette(getApplicationContext()).execute();
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
