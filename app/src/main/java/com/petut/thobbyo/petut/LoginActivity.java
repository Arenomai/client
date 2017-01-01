package com.petut.thobbyo.petut;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    Button bouton_login;
    EditText txt_server_addr, txt_server_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences(Application.PREF_DEFAULT, 0);

        txt_server_addr = (EditText) findViewById(R.id.editTextServerAddr);
        txt_server_addr.setText(sp.getString("serverAddr", "thgros.fr"));
        txt_server_port = (EditText) findViewById(R.id.editTextServerPort);
        txt_server_port.setText(String.valueOf(sp.getInt("serverPort", 4242)));

        bouton_login = (Button) findViewById(R.id.bouton_login);
        bouton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences sp = getSharedPreferences(Application.PREF_DEFAULT, 0);
                final SharedPreferences.Editor spe = sp.edit();
                spe.putString("serverAddr", txt_server_addr.getText().toString());
                spe.putInt("serverPort", Integer.valueOf(txt_server_port.getText().toString()));
                spe.apply();

                Intent intent = new Intent(LoginActivity.this, PlanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }
}
