package com.petut.thobbyo.petut.net.msgtypes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.petut.thobbyo.petut.Application;
import com.petut.thobbyo.petut.net.Connection;
import com.petut.thobbyo.petut.net.InMessage;
import com.petut.thobbyo.petut.net.MessageType;
import com.petut.thobbyo.petut.net.OutMessage;

import java.io.IOException;

import static com.petut.thobbyo.petut.Application.PREF_DEFAULT;

/**
 * Created by Krevar on 09/01/2017.
 */

public class AuthTockenRequester extends AsyncTask<String, String, String> {

    private static Context context;

    public AuthTockenRequester(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        final Connection co = new Connection();
        final Context ctx;
        final SharedPreferences sp = context.getSharedPreferences(PREF_DEFAULT, 0);
        final SharedPreferences.Editor spe = sp.edit();

        try {
            co.connect(sp.getString("serverAddr","thgros.fr"),sp.getInt("serverPort",4242));
            OutMessage omsg = new OutMessage(MessageType.Auth, Auth.Subtype.Request);
            Auth.Request ar = new Auth.Request();
            ar.setUsername(params[0]);
            ar.setPassword(params[1]);
            ar.writeToMsg(omsg);
            co.write(omsg);

            InMessage inmsg = co.read();
            spe.putInt("token",inmsg.readI32());
            co.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return "";
    }
}
