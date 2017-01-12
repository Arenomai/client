package com.petut.thobbyo.petut.net.msgtypes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.petut.thobbyo.petut.Application;
import com.petut.thobbyo.petut.net.Connection;
import com.petut.thobbyo.petut.net.InMessage;
import com.petut.thobbyo.petut.net.MessageType;
import com.petut.thobbyo.petut.net.OutMessage;

import java.io.IOException;

/**
 * Created by Krevar on 12/01/2017.
 */

public class UserAccountInfoUpdater extends AsyncTask<String, String, String> {
    public UserAccountInfoUpdater(Context ctx) {
        context = ctx;
    }
    private final Connection co = new Connection();

    @Override
    protected String doInBackground(String... params)
    {
        final SharedPreferences sp = context.getSharedPreferences(Application.PREF_DEFAULT, 0);
        final SharedPreferences.Editor spe = sp.edit();
        try {
            co.connect(sp.getString("serverAddr","thgros.fr"),sp.getInt("serverPort",4242));
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutMessage omsg = new OutMessage(MessageType.UserAccount, UserAccount.Subtype.AccountModify);
        omsg.writeString(params[0]);
        omsg.writeString(params[1]);
        omsg.writeI32(sp.getInt("token",-1));
        co.write(omsg);
        try {
            co.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



    private Context context;
}