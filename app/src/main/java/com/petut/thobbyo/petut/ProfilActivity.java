package com.petut.thobbyo.petut;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.petut.thobbyo.petut.net.Connection;
import com.petut.thobbyo.petut.net.InMessage;
import com.petut.thobbyo.petut.net.MessageType;
import com.petut.thobbyo.petut.net.OutMessage;
import com.petut.thobbyo.petut.net.msgtypes.UserAccount;

import java.io.IOException;

public class ProfilActivity extends AppCompatActivity {
    public ProfilActivity(Context ctx) {
        context = ctx;
    }

    private final Connection co = new Connection();
    private final Context context;

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
        textID.setText(sp.getInt("token",-1));
        try {
            co.connect(sp.getString("serverAddr","thgros.fr"),sp.getInt("serverPort",4242));
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutMessage omsg = new OutMessage(MessageType.UserAccount, UserAccount.Subtype.InfoRequest);
        omsg.writeI32(sp.getInt("token",-1));
        co.write(omsg);
        InMessage inmsg = co.read();
        String nick = inmsg.readString();
        String bio = inmsg.readString();
        editTextPseudo.setText(nick);
        editTextBio.setText(bio);

        button_profil = (Button) findViewById(R.id.buttonProfil);
        button_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }


        });
    }
}