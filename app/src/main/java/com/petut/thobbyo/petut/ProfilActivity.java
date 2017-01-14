package com.petut.thobbyo.petut;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.petut.thobbyo.petut.net.msgtypes.UserAccountInfoRequester;
import com.petut.thobbyo.petut.net.msgtypes.UserAccountInfoUpdater;


public class ProfilActivity extends AppCompatActivity {

    private Button button_profil;
    private TextView textID;
    private EditText editTextPseudo, editTextBio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        textID = (TextView) findViewById(R.id.textViewID);
        editTextPseudo = (EditText) findViewById(R.id.editTextPseudo);
        editTextBio = (EditText) findViewById(R.id.editTextBio);
        final SharedPreferences sp = getSharedPreferences(Application.PREF_DEFAULT, 0);
        final SharedPreferences.Editor spe = sp.edit();
        UserAccountInfoRequester urq = new UserAccountInfoRequester(getApplicationContext()){
            public void onPostExecute(String ret) {
                textID.setText(String.valueOf(sp.getInt("token",-1)));
                editTextPseudo.setText(sp.getString("nick",""));
                editTextBio.setText(sp.getString("bio",""));

                button_profil = (Button) findViewById(R.id.buttonProfil);
                button_profil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nick = editTextPseudo.getText().toString();
                        String bio = editTextBio.getText().toString();
                        new UserAccountInfoUpdater(getApplicationContext()).execute(nick,bio);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), "Mise à jour effectuée", duration);
                        toast.show();
                    }


                });
                }
        };
        urq.execute();
    }
}