package com.petut.thobbyo.arenomai.net.msgtypes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.petut.thobbyo.arenomai.Application;
import com.petut.thobbyo.arenomai.net.Connection;
import com.petut.thobbyo.arenomai.net.InMessage;
import com.petut.thobbyo.arenomai.net.MessageType;
import com.petut.thobbyo.arenomai.net.OutMessage;

/**
 * Created by Krevar on 11/01/2017.
 */

public class UserAccountInfoRequester extends AsyncTask<String, String, String> {
    public UserAccountInfoRequester(Context ctx, Connection co) {
        context = ctx;
        this.co = co;
    }
    private Connection co;

    @Override
    protected String doInBackground(String... params)
    {
        final SharedPreferences sp = context.getSharedPreferences(Application.PREF_DEFAULT, 0);
        final SharedPreferences.Editor spe = sp.edit();
        OutMessage omsg = new OutMessage(MessageType.UserAccount, UserAccount.Subtype.InfoRequest);
        omsg.writeI32(sp.getInt("token",-1));
        co.write(omsg);
        InMessage inmsg = co.read();
        spe.putString("nick",inmsg.readString());
        spe.putString("bio",inmsg.readString());
        spe.apply();
        return "";

    }
    @Override
    protected void onPostExecute(String reponse) {
    }

    private Context context;
}
