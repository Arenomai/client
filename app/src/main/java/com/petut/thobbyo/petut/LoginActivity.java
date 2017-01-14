package com.petut.thobbyo.petut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.petut.thobbyo.petut.net.Connection;
import com.petut.thobbyo.petut.net.msgtypes.AuthTockenRequester;

import java.io.IOException;

import static android.R.attr.duration;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    Button bouton_login;
    EditText txt_server_addr, txt_server_port, txt_username,txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences(Application.PREF_DEFAULT, 0);

        txt_server_addr = (EditText) findViewById(R.id.editTextServerAddr);
        txt_server_addr.setText(sp.getString("serverAddr", "thgros.fr"));
        txt_server_port = (EditText) findViewById(R.id.editTextServerPort);
        txt_server_port.setText(String.valueOf(sp.getInt("serverPort", 4242)));
        txt_password = (EditText)  findViewById(R.id.txt_passowrd);
        txt_username = (EditText) findViewById(R.id.txt_username);

        bouton_login = (Button) findViewById(R.id.bouton_login);
        bouton_login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(txt_username.getText().toString().equals("") || txt_password.getText().toString().equals(""))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Veuillez entrer un pseudo et un mot de passe";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    final SharedPreferences sp = getSharedPreferences(Application.PREF_DEFAULT, 0);
                    final SharedPreferences.Editor spe = sp.edit();
                    spe.putString("serverAddr", txt_server_addr.getText().toString());
                    spe.putInt("serverPort", Integer.valueOf(txt_server_port.getText().toString()));
                    spe.apply();
                    AuthTockenRequester areq = new AuthTockenRequester(getApplicationContext()){

                    @Override
                    public void onPostExecute(String ret) {
                        int token = sp.getInt("token",-3);
                        if(token == -2)
                        {
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getApplicationContext(), "Mot de passe incorrect", duration);
                            toast.show();
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, PlanActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                        }
                        }
                    };

                    areq.execute(txt_username.getText().toString(), txt_password.getText().toString());
                }
            }
        });
    }
}
