package com.petut.thobbyo.petut.net.msgtypes;

import android.os.AsyncTask;

import com.petut.thobbyo.petut.net.Connection;
import com.petut.thobbyo.petut.net.InMessage;
import com.petut.thobbyo.petut.net.MessageType;
import com.petut.thobbyo.petut.net.OutMessage;

import java.io.IOException;


public class AuthTockenRequester extends AsyncTask<String, Void, Integer> {

    private Connection co;

    public AuthTockenRequester(Connection co) {
        this.co = co;
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            OutMessage omsg = new OutMessage(MessageType.Auth, Auth.Subtype.Request);
            omsg.writeString(params[0]);
            omsg.writeString(params[1]);
            co.write(omsg);
            InMessage inmsg = co.read();

            int token;
            if (inmsg.getSubtype() == Auth.Subtype.Denied) {
                token = -2;
            } else {
                token = inmsg.readI32();
            }
            co.close();
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

